/***********************************************************************************************************************
 *
 * Copyright (C) 2010-2013 by the Stratosphere project (http://stratosphere.eu)
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 **********************************************************************************************************************/

package eu.stratosphere.nephele.rpc;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.DatagramPacket;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.KryoException;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.minlog.Log;

import eu.stratosphere.nephele.util.StringUtils;

/**
 * This class implements a lightweight, UDP-based RPC service.
 * <p>
 * This class is thread-safe.
 * 
 * @author warneke
 */
public final class RPCService {

	/**
	 * The default number of threads handling RPC requests.
	 */
	private static final int DEFAULT_NUM_RPC_HANDLERS = 1;

	/**
	 * Interval in which the background clean-up routine runs in milliseconds.
	 */
	static final int CLEANUP_INTERVAL = 10000;

	/**
	 * The maximum period of time an RPC call is allowed to take in milliseconds.
	 */
	private static final int RPC_TIMEOUT = 60000;

	/**
	 * The executor service managing the RPC handler threads.
	 */
	private final ExecutorService rpcHandlers;

	/**
	 * The UDP port this service is bound to.
	 */
	private final int rpcPort;

	/**
	 * Network thread to wait for incoming data and dispatch it among the available RPC handler threads.
	 */
	private final NetworkThread networkThread;

	/**
	 * Stores whether the RPC service was requested to shut down.
	 */
	private final AtomicBoolean shutdownRequested = new AtomicBoolean(false);

	/**
	 * The statistics module collects statistics on the operation of the RPC service.
	 */
	private final RPCStatistics statistics = new RPCStatistics();

	/**
	 * Periodic timer to handle clean-up tasks in the background.
	 */
	private final Timer cleanupTimer = new Timer();

	private final ConcurrentHashMap<String, RPCProtocol> callbackHandlers =
		new ConcurrentHashMap<String, RPCProtocol>();

	private final ConcurrentHashMap<Integer, RPCRequestMonitor> pendingRequests =
		new ConcurrentHashMap<Integer, RPCRequestMonitor>();

	private final ConcurrentHashMap<Integer, RPCRequest> requestsBeingProcessed =
		new ConcurrentHashMap<Integer, RPCRequest>();

	private final ConcurrentHashMap<Integer, CachedResponse> cachedResponses =
		new ConcurrentHashMap<Integer, CachedResponse>();

	private final List<Class<?>> kryoTypesToRegister;

	private final ThreadLocal<Kryo> kryo = new ThreadLocal<Kryo>() {

		/**
		 * {@inheritDoc}
		 */
		@Override
		protected Kryo initialValue() {

			final Kryo kryo = new Kryo();
			if (RPCService.this.kryoTypesToRegister != null) {
				kryo.setAutoReset(false);
				kryo.setRegistrationRequired(true);
				kryo.setReferences(true);
				kryo.addDefaultSerializer(StackTraceElement.class, new StackTraceElementSerializer());
				kryo.addDefaultSerializer(Collection.class, new CollectionSerializer());

				for (final Class<?> kryoType : RPCService.this.kryoTypesToRegister)
					kryo.register(kryoType);
			}

			return kryo;
		}
	};

	private static final class CachedResponse {

		private final long creationTime;

		private final DatagramPacket[] packets;

		private CachedResponse(final long creationTime, final DatagramPacket[] packets) {
			this.creationTime = creationTime;
			this.packets = packets;
		}
	}

	private static final class RPCRequestMonitor {

		private RPCResponse rpcResponse = null;
	}

	private final class RPCInvocationHandler implements InvocationHandler {

		private final InetSocketAddress remoteSocketAddress;

		private final String interfaceName;

		private RPCInvocationHandler(final InetSocketAddress remoteSocketAddress, final String interfaceName) {
			this.remoteSocketAddress = remoteSocketAddress;
			this.interfaceName = interfaceName;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {

			final int messageID = (int) (Integer.MIN_VALUE + Math.random() * Integer.MAX_VALUE * 2.0);
			final RPCRequest rpcRequest = new RPCRequest(messageID, this.interfaceName, method, args);

			return RPCService.this.sendRPCRequest(this.remoteSocketAddress, rpcRequest);
		}
	}

	private final class CleanupTask extends TimerTask {

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void run() {

			// Process the collected data
			RPCService.this.statistics.processCollectedData();

			final long now = System.currentTimeMillis();
			final Iterator<Map.Entry<Integer, CachedResponse>> it =
				RPCService.this.cachedResponses.entrySet().iterator();
			while (it.hasNext()) {

				final Map.Entry<Integer, CachedResponse> entry = it.next();
				final CachedResponse cachedResponse = entry.getValue();
				if (cachedResponse.creationTime + CLEANUP_INTERVAL < now)
					it.remove();
			}

			RPCService.this.networkThread.cleanUpStaleState();
		}
	}

	public RPCService(final int rpcPort, final int numRPCHandlers, final List<Class<?>> typesToRegister)
			throws IOException {

		this.rpcHandlers = Executors.newFixedThreadPool(numRPCHandlers);

		if (typesToRegister == null)
			this.kryoTypesToRegister = null;
		else {
			ArrayList<Class<?>> kryoTypesToRegister = new ArrayList<Class<?>>();
			addBasicRPCTypes(kryoTypesToRegister);
			kryoTypesToRegister.addAll(typesToRegister);
			this.kryoTypesToRegister = Collections.unmodifiableList(kryoTypesToRegister);
		}

		this.rpcPort = rpcPort;
		this.networkThread = new NetworkThread(this, rpcPort);
		this.networkThread.start();

		this.cleanupTimer.schedule(new CleanupTask(), CLEANUP_INTERVAL, CLEANUP_INTERVAL);
	}

	private static void addBasicRPCTypes(final List<Class<?>> typesToRegister) {

		typesToRegister.add(ArrayList.class);
		typesToRegister.add(AssertionError.class);
		typesToRegister.add(boolean[].class);
		typesToRegister.add(Class.class);
		typesToRegister.add(Class[].class);
		typesToRegister.add(IllegalArgumentException.class);
		typesToRegister.add(InterruptedException.class);
		typesToRegister.add(IOException.class);
		typesToRegister.add(KryoException.class);
		typesToRegister.add(List.class);
		typesToRegister.add(Object[].class);
		typesToRegister.add(RPCEnvelope.class);
		typesToRegister.add(RPCRequest.class);
		typesToRegister.add(RPCReturnValue.class);
		typesToRegister.add(RPCCleanup.class);
		typesToRegister.add(RPCThrowable.class);
		typesToRegister.add(StackTraceElement.class);
		typesToRegister.add(StackTraceElement[].class);
		typesToRegister.add(String[].class);
		typesToRegister.add(StringBuffer.class);

		// Register non-public RPC types
		registerNonPublicRPCTypes(typesToRegister, "java.util.Collections$UnmodifiableRandomAccessList");

	}

	/**
	 * Adds a non-public type to the list of RPC types to be registered.
	 * 
	 * @param typesToRegister
	 *        the list of RPC types to be registered
	 * @param className
	 *        the name of the non-public type
	 * @return <code>true</code> if the registered was successful, <code>false</code> otherwise
	 */
	private static boolean registerNonPublicRPCTypes(final List<Class<?>> typesToRegister, final String className) {

		try {
			final Class<?> uralClass = Class.forName(className);
			typesToRegister.add(uralClass);
			return true;
		} catch (Exception e) {
		}

		return false;
	}

	public RPCService() throws IOException {
		this(DEFAULT_NUM_RPC_HANDLERS, null);
	}

	public RPCService(final List<Class<?>> typesToRegister) throws IOException {
		this(DEFAULT_NUM_RPC_HANDLERS, typesToRegister);
	}

	public RPCService(final int numRPCHandlers, final List<Class<?>> typesToRegister) throws IOException {

		this.rpcHandlers = Executors.newFixedThreadPool(numRPCHandlers);

		if (typesToRegister == null)
			this.kryoTypesToRegister = null;
		else {
			ArrayList<Class<?>> kryoTypesToRegister = new ArrayList<Class<?>>();
			addBasicRPCTypes(kryoTypesToRegister);
			kryoTypesToRegister.addAll(typesToRegister);
			this.kryoTypesToRegister = Collections.unmodifiableList(kryoTypesToRegister);
		}

		this.rpcPort = -1;
		this.networkThread = new NetworkThread(this, -1);
		this.networkThread.start();

		this.cleanupTimer.schedule(new CleanupTask(), CLEANUP_INTERVAL, CLEANUP_INTERVAL);
	}

	public void setProtocolCallbackHandler(final Class<? extends RPCProtocol> protocol,
			final RPCProtocol callbackHandler) {

		// Check signature of interface before adding it
		checkRPCProtocol(protocol);

		if (this.callbackHandlers.putIfAbsent(protocol.getName(), callbackHandler) != null)
			Log.error("There is already a protocol call back handler set for protocol " + protocol.getName());

	}

	/**
	 * Checks the signature of the methods contained in the given protocol.
	 * 
	 * @param protocol
	 *        the protocol to be checked
	 */
	private static final void checkRPCProtocol(final Class<? extends RPCProtocol> protocol) {

		if (!protocol.isInterface())
			throw new IllegalArgumentException("Provided protocol " + protocol + " is not an interface");

		try {
			final Method[] methods = protocol.getMethods();
			for (int i = 0; i < methods.length; ++i) {

				final Method method = methods[i];
				final Class<?>[] exceptionTypes = method.getExceptionTypes();
				boolean ioExceptionFound = false;
				boolean interruptedExceptionFound = false;
				for (int j = 0; j < exceptionTypes.length; ++j)
					if (IOException.class.equals(exceptionTypes[j]))
						ioExceptionFound = true;
					else if (InterruptedException.class.equals(exceptionTypes[j]))
						interruptedExceptionFound = true;

				if (!ioExceptionFound)
					throw new IllegalArgumentException("Method " + method.getName()
						+ " of protocol " + protocol.getName() + " must be declared to throw an IOException");
				if (!interruptedExceptionFound)
					throw new IllegalArgumentException("Method " + method.getName()
						+ " of protocol " + protocol.getName() + " must be declared to throw an InterruptedException");
			}
		} catch (SecurityException se) {
			if (Log.DEBUG)
				Log.debug(StringUtils.stringifyException(se));
		}
	}

	@SuppressWarnings("unchecked")
	public <T extends RPCProtocol> T getProxy(final InetSocketAddress remoteAddress, Class<T> protocol) {

		final Class<?>[] interfaces = new Class<?>[1];
		interfaces[0] = protocol;
		return (T) java.lang.reflect.Proxy.newProxyInstance(RPCService.class.getClassLoader(), interfaces,
			new RPCInvocationHandler(remoteAddress, protocol.getName()));
	}

	/**
	 * Sends an RPC request to the given {@link InetSocketAddress}.
	 * 
	 * @param remoteSocketAddress
	 *        the remote address to send the request to
	 * @param request
	 *        the RPC request to send
	 * @return the return value of the RPC call, possibly <code>null</code>
	 * @throws Throwable
	 *         any exception that is thrown by the remote receiver of the RPC call
	 */
	Object sendRPCRequest(final InetSocketAddress remoteSocketAddress, final RPCRequest request) throws Throwable {

		if (this.shutdownRequested.get())
			throw new IOException("Shutdown of RPC service has already been requested");

		final long start = System.currentTimeMillis();
		DatagramPacket[] packets = this.messageToPackets(remoteSocketAddress, request);
		final Integer messageID = Integer.valueOf(request.getMessageID());

		final RPCRequestMonitor requestMonitor = new RPCRequestMonitor();

		this.pendingRequests.put(messageID, requestMonitor);

		RPCResponse rpcResponse = null;
		int numberOfRetries;
		try {

			numberOfRetries = this.networkThread.send(packets);

			// Wait for the response
			synchronized (requestMonitor) {

				while (true) {

					if (requestMonitor.rpcResponse != null) {
						rpcResponse = requestMonitor.rpcResponse;
						break;
					}

					final long sleepTime = RPC_TIMEOUT - (System.currentTimeMillis() - start);
					if (sleepTime > 0L)
						requestMonitor.wait(sleepTime);
					else
						break;
				}
			}

		} finally {
			// Request is no longer pending
			this.pendingRequests.remove(messageID);
		}

		if (rpcResponse == null)
			throw new IOException("Unable to complete RPC of method " + request.getMethodName() + " on "
				+ remoteSocketAddress);

		// Report the successful call to the statistics module
		final String methodName = request.getMethodName();
		this.statistics.reportSuccessfulTransmission(methodName, packets.length, numberOfRetries);
		this.statistics.reportRTT(methodName, (int) (System.currentTimeMillis() - start));

		// TODO: Send clean up message

		if (rpcResponse instanceof RPCReturnValue)
			return ((RPCReturnValue) rpcResponse).getRetVal();
		throw ((RPCThrowable) rpcResponse).getThrowable();
	}

	public void shutDown() {

		if (!this.shutdownRequested.compareAndSet(false, true))
			return;

		// Request shutdown of network thread
		try {
			this.networkThread.shutdown();
		} catch (InterruptedException ie) {
			Log.debug("Caught exception while waiting for network thread to shut down: ", ie);
		}

		this.rpcHandlers.shutdown();

		try {
			this.rpcHandlers.awaitTermination(5000L, TimeUnit.MILLISECONDS);
		} catch (InterruptedException ie) {
			Log.debug("Caught exception while waiting for RPC handlers to finish: ", ie);
		}

		this.cleanupTimer.cancel();

		// Finally, process the last collected data
		this.statistics.processCollectedData();
	}

	void processIncomingRPCMessage(final InetSocketAddress remoteSocketAddress, final Input input) {

		final Runnable runnable = new Runnable() {

			/**
			 * {@inheritDoc}
			 */
			@Override
			public void run() {

				final Kryo k = RPCService.this.kryo.get();
				k.reset();
				final RPCEnvelope envelope = k.readObject(input, RPCEnvelope.class);
				final RPCMessage msg = envelope.getRPCMessage();

				if (msg instanceof RPCRequest)
					RPCService.this.processIncomingRPCRequest(remoteSocketAddress, (RPCRequest) msg);
				else if (msg instanceof RPCResponse)
					RPCService.this.processIncomingRPCResponse((RPCResponse) msg);
				else
					RPCService.this.processIncomingRPCCleanup(remoteSocketAddress, (RPCCleanup) msg);
			}
		};

		this.rpcHandlers.execute(runnable);
	}

	private void processIncomingRPCRequest(final InetSocketAddress remoteSocketAddress, final RPCRequest rpcRequest) {

		final Integer messageID = Integer.valueOf(rpcRequest.getMessageID());

		if (this.requestsBeingProcessed.putIfAbsent(messageID, rpcRequest) != null) {
			Log.debug("Request " + rpcRequest.getMessageID() + " is already being processed at the moment");
			return;
		}

		final CachedResponse cachedResponse = this.cachedResponses.get(messageID);
		if (cachedResponse != null) {
			try {
				final int numberOfRetries = this.networkThread.send(cachedResponse.packets);
				this.statistics.reportSuccessfulTransmission(rpcRequest.getMethodName() + " (Response)",
					cachedResponse.packets.length, numberOfRetries);
			} catch (Exception e) {
				Log.error("Caught exception while trying to send RPC response: ", e);
			} finally {
				this.requestsBeingProcessed.remove(messageID);
			}
			return;
		}

		final RPCProtocol callbackHandler = this.callbackHandlers.get(rpcRequest.getInterfaceName());
		if (callbackHandler == null) {
			Log.error("Cannot find callback handler for protocol " + rpcRequest.getInterfaceName());
			this.requestsBeingProcessed.remove(messageID);
			return;
		}

		try {
			final Method method = callbackHandler.getClass().getMethod(rpcRequest.getMethodName(),
				rpcRequest.getParameterTypes());

			RPCResponse rpcResponse = null;
			try {
				final Object retVal = method.invoke(callbackHandler, rpcRequest.getArgs());
				rpcResponse = new RPCReturnValue(rpcRequest.getMessageID(), retVal);
			} catch (InvocationTargetException ite) {

				Throwable targetException = ite.getTargetException();

				// Make sure the stack trace is correctly filled
				targetException.getStackTrace();

				if (!this.isThrowableRegistered(targetException.getClass()))
					targetException = wrapInIOException(rpcRequest, targetException);

				rpcResponse = new RPCThrowable(rpcRequest.getMessageID(), targetException);
			}
			final DatagramPacket[] packets = this.messageToPackets(remoteSocketAddress, rpcResponse);
			this.cachedResponses.put(messageID, new CachedResponse(System.currentTimeMillis(), packets));

			final int numberOfRetries = this.networkThread.send(packets);
			this.statistics.reportSuccessfulTransmission(rpcRequest.getMethodName() + " (Response)", packets.length,
				numberOfRetries);

		} catch (Exception e) {
			Log.error("Caught processing RPC request: ", e);
		} finally {
			this.requestsBeingProcessed.remove(messageID);
		}
	}

	/**
	 * Checks if the given class is registered with the RPC service.
	 * 
	 * @param throwableType
	 *        the class to check
	 * @return <code>true</code> if the given class is registered with the RPC service, <code>false</code> otherwise
	 */
	private boolean isThrowableRegistered(final Class<? extends Throwable> throwableType) {

		final Kryo kryo = this.kryo.get();
		try {
			kryo.getRegistration(throwableType);
		} catch (IllegalArgumentException e) {
			return false;
		}

		return true;
	}

	/**
	 * Transforms the given {@link Throwable} into a string and wraps it into an {@link IOException}.
	 * 
	 * @param request
	 *        the RPC request which caused the {@link Throwable} to be wrapped
	 * @param throwable
	 *        the {@link Throwable} to be wrapped
	 * @return the {@link} IOException created from the {@link Throwable}
	 */
	private static IOException wrapInIOException(final RPCRequest request, final Throwable throwable) {

		final StringBuilder sb = new StringBuilder("The remote procedure call of method ");
		sb.append(request.getInterfaceName());
		sb.append('.');
		sb.append(request.getMethodName());
		sb.append(" caused an unregistered exception: ");
		sb.append(StringUtils.stringifyException(throwable));

		return new IOException(sb.toString());
	}

	private DatagramPacket[] messageToPackets(final InetSocketAddress remoteSocketAddress, final RPCMessage rpcMessage) {

		final MultiPacketOutputStream mpos = new MultiPacketOutputStream(RPCMessage.MAXIMUM_MSG_SIZE
			+ RPCMessage.METADATA_SIZE);
		final Kryo kryo = this.kryo.get();
		kryo.reset();

		final Output output = new Output(mpos);

		kryo.writeObject(output, new RPCEnvelope(rpcMessage));
		output.close();
		mpos.close();

		return mpos.createPackets(remoteSocketAddress);
	}

	/**
	 * Processes an incoming RPC response.
	 * 
	 * @param rpcResponse
	 *        the RPC response to be processed
	 */
	void processIncomingRPCResponse(final RPCResponse rpcResponse) {

		final Integer messageID = Integer.valueOf(rpcResponse.getMessageID());

		final RPCRequestMonitor requestMonitor = this.pendingRequests.get(messageID);

		// The caller has already timed out or received an earlier response
		if (requestMonitor == null)
			return;

		synchronized (requestMonitor) {
			requestMonitor.rpcResponse = rpcResponse;
			requestMonitor.notify();
		}
	}

	void processIncomingRPCCleanup(final InetSocketAddress remoteSocketAddress, final RPCCleanup rpcCleanup) {

		this.cachedResponses.remove(Integer.valueOf(rpcCleanup.getMessageID()));
	}

	public int getRPCPort() {

		return this.rpcPort;
	}

	/**
	 * Converts the given integer to a unsigned short.
	 * 
	 * @param val
	 *        the integer to convert
	 * @return the unsigned short
	 */
	static short encodeInteger(final int val) {

		if (val < -1 || val > 65534)
			throw new IllegalArgumentException("Value must be in the range -1 and 65534 but is " + val);

		return (short) (val - Short.MIN_VALUE + 1);
	}

	/**
	 * Converts the unsigned short into an integer
	 * 
	 * @param val
	 *        the unsigned short
	 * @return the converted integer
	 */
	static int decodeInteger(final short val) {

		return val - Short.MIN_VALUE - 1;
	}
}
