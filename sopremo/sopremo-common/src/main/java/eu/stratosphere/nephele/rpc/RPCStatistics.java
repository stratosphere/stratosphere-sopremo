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

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

import com.esotericsoftware.minlog.Log;

/**
 * The RPCStatistics class collects statistics on the operation of the RPC service, e.g. packet loss, request retries,
 * etc.
 * <p>
 * This class is thread safe.
 * 
 * @author warneke
 */
public class RPCStatistics {

	/**
	 * Auxiliary class to hold statistics on the round-trip times (RTTs) of RPC class.
	 * <p>
	 * This class is thread-safe.
	 * 
	 * @author warneke
	 */
	private static final class RTTStatistics {

		/**
		 * The number of reported requests in the current collection interval.
		 */
		private final AtomicInteger requestCounter = new AtomicInteger(0);

		/**
		 * The sum of all RTTs in the current collection interval in milliseconds.
		 */
		private final AtomicInteger sumOfRTTs = new AtomicInteger(0);

		/**
		 * The name of the method with the minimum RTT in the current collection interval.
		 */
		private volatile String minMethodName = null;

		/**
		 * The minimum RTT in the current collection interval in milliseconds.
		 */
		private final AtomicInteger minRTT = new AtomicInteger(Integer.MAX_VALUE);

		/**
		 * The name of the method with the maximum RTT in the current collection interval.
		 */
		private volatile String maxMethodName = null;

		/**
		 * The maximum RTT in the current collection interval in milliseconds.
		 */
		private final AtomicInteger maxRTT = new AtomicInteger(Integer.MIN_VALUE);

		/**
		 * Reports the RTT of an RPC call.
		 * 
		 * @param methodName
		 *        the name of the remote method that has been called
		 * @param rtt
		 *        the required RTT in milliseconds
		 */
		private final void reportRTT(final String methodName, final int rtt) {

			this.requestCounter.incrementAndGet();
			this.sumOfRTTs.addAndGet(rtt);
			testAndSetLowestRTT(methodName, rtt);
			testAndSetHighestRTT(methodName, rtt);
		}

		/**
		 * Sets the lowest RTT in a thread-safe way.
		 * 
		 * @param methodName
		 *        the method name
		 * @param rtt
		 *        the RTT in milliseconds
		 */
		private void testAndSetLowestRTT(final String methodName, final int rtt) {

			while (true) {

				final int val = this.minRTT.get();
				if (rtt < val) {
					if (!this.minRTT.compareAndSet(val, rtt)) {
						continue;
					}

					this.minMethodName = methodName;
				}

				break;
			}
		}

		/**
		 * Sets the highest RTT in a thread-safe way.
		 * 
		 * @param methodName
		 *        the method name
		 * @param rtt
		 *        the RTT in milliseconds
		 */
		private void testAndSetHighestRTT(final String methodName, final int rtt) {

			while (true) {

				final int val = this.maxRTT.get();
				if (rtt > val) {
					if (!this.maxRTT.compareAndSet(val, rtt)) {
						continue;
					}

					this.maxMethodName = methodName;
				}

				break;
			}
		}

		/**
		 * Processes and logs the collected RTT data.
		 */
		private void processCollectedData() {

			final int numberOfRequests = this.requestCounter.getAndSet(0);
			if (numberOfRequests == 0) {
				return;
			}

			final float avg = (float) this.sumOfRTTs.getAndSet(0) / (float) numberOfRequests;
			final int min = this.minRTT.getAndSet(Integer.MAX_VALUE);
			final String minMethodName = this.minMethodName;
			this.minMethodName = null;
			final int max = this.maxRTT.getAndSet(Integer.MIN_VALUE);
			final String maxMethodName = this.maxMethodName;
			this.maxMethodName = null;

			if (Log.DEBUG) {

				final StringBuilder sb = new StringBuilder("RTT stats: ");
				sb.append(avg);
				sb.append(" ms avg,\t: ");
				sb.append(min);
				sb.append(" ms min (");
				sb.append(minMethodName);
				sb.append("),\t");
				sb.append(max);
				sb.append(" ms max (");
				sb.append(maxMethodName);
				sb.append(')');

				Log.debug(sb.toString());
			}
		}
	}

	/**
	 * Auxiliary class to hold the transmission retry statistics for a particular number of packets.
	 * <p>
	 * This class is thread-safe.
	 * 
	 * @author warneke
	 */
	private static final class RetryStatistics {

		/**
		 * The number of packets this object has been created for.
		 */
		private final int numberOfPackets;

		/**
		 * The minimum number of retries reported in the current collection interval.
		 */
		private final AtomicInteger minRetries = new AtomicInteger(Integer.MAX_VALUE);

		/**
		 * The method name for which the minimum number of retries has been reported.
		 */
		private volatile String minMethodName = null;

		/**
		 * The maximum number of retries reported in the current collection interval.
		 */
		private final AtomicInteger maxRetries = new AtomicInteger(Integer.MIN_VALUE);

		/**
		 * The method name for which the maximum number of retries has been reported.
		 */
		private volatile String maxMethodName = null;

		/**
		 * The number of reported requests in the current collection interval.
		 */
		private final AtomicInteger requestCounter = new AtomicInteger(0);

		/**
		 * The sum of required retries in the current collection interval.
		 */
		private final AtomicInteger sumOfRetries = new AtomicInteger(0);

		/**
		 * Creates a new retry statistics object.
		 * 
		 * @param numberOfPackets
		 *        the number of packets this object is created for
		 */
		private RetryStatistics(final int numberOfPackets) {
			this.numberOfPackets = numberOfPackets;
		}

		/**
		 * Reports the number of required retries for an RPC call.
		 * 
		 * @param methodName
		 *        the name of the remote method that has been called
		 * @param requiredRetries
		 *        the number of required retries
		 */
		private final void reportNumberOfRequiredRetries(final String methodName, final int requiredRetries) {

			this.requestCounter.incrementAndGet();
			this.sumOfRetries.addAndGet(requiredRetries);
			testAndSetMin(methodName, requiredRetries);
			testAndSetMax(methodName, requiredRetries);
		}

		/**
		 * Sets the minimum number of required retries in a thread-safe way.
		 * 
		 * @param methodName
		 *        the method name
		 * @param requiredRetries
		 *        the number of required retries
		 */
		private void testAndSetMin(final String methodName, final int requiredRetries) {

			while (true) {

				final int val = this.minRetries.get();
				if (requiredRetries < val) {
					if (!this.minRetries.compareAndSet(val, requiredRetries)) {
						continue;
					}

					this.minMethodName = methodName;
				}

				break;
			}
		}

		/**
		 * Sets the maximum number of required retries in a thread-safe way.
		 * 
		 * @param methodName
		 *        the method name
		 * @param requiredRetries
		 *        the number of required retries
		 */
		private void testAndSetMax(final String methodName, final int requiredRetries) {

			while (true) {

				final int val = this.maxRetries.get();
				if (requiredRetries > val) {
					if (!this.maxRetries.compareAndSet(val, requiredRetries)) {
						continue;
					}

					this.maxMethodName = methodName;
				}

				break;
			}
		}

		/**
		 * Processes and logs the collected statistics data.
		 */
		private void processCollectedData() {

			if (Log.DEBUG) {

				final int numberOfRequests = this.requestCounter.get();
				if (numberOfRequests == 0) {
					return;
				}

				final float avg = (float) this.sumOfRetries.get() / (float) numberOfRequests;

				final StringBuilder sb = new StringBuilder();
				sb.append(this.numberOfPackets);
				sb.append("\t: ");
				sb.append(avg);
				sb.append(" (min ");
				sb.append(this.minMethodName);
				sb.append(' ');
				sb.append(this.minRetries.get());
				sb.append(", max ");
				sb.append(this.maxMethodName);
				sb.append(' ');
				sb.append(this.maxRetries.get());
				sb.append(')');

				Log.debug(sb.toString());
			}
		}
	}

	/**
	 * Map storing the collected retry statistics data by the number of packets the request consisted of.
	 */
	private final ConcurrentMap<Integer, RetryStatistics> statisticsData = new ConcurrentHashMap<Integer, RetryStatistics>();

	/**
	 * The RTT statistics.
	 */
	private final RTTStatistics rttStatistics = new RTTStatistics();

	/**
	 * Reports the number of required retries for a successful transmission call to the statistics module.
	 * 
	 * @param methodName
	 *        the name of the remote method that has been invoked as part of the transmission
	 * @param numberOfPackets
	 *        the number of packets the transmission consisted of
	 * @param requiredRetries
	 *        the number of required retries before the transmission was fully acknowledged
	 */
	void reportSuccessfulTransmission(final String methodName, final int numberOfPackets, final int requiredRetries) {

		final Integer key = Integer.valueOf(numberOfPackets);
		RetryStatistics data = this.statisticsData.get(key);
		if (data == null) {
			data = new RetryStatistics(numberOfPackets);
			final RetryStatistics oldValue = this.statisticsData.putIfAbsent(key, data);
			if (oldValue != null) {
				data = oldValue;
			}
		}

		data.reportNumberOfRequiredRetries(methodName, requiredRetries);
	}

	void reportRTT(final String methodName, final int rtt) {
		this.rttStatistics.reportRTT(methodName, rtt);
	}

	/**
	 * Triggers the periodic processing the of the collected data.
	 */
	void processCollectedData() {

		final Iterator<RetryStatistics> it = this.statisticsData.values().iterator();
		while (it.hasNext()) {
			final RetryStatistics data = it.next();
			it.remove();
			data.processCollectedData();
		}

		this.rttStatistics.processCollectedData();
	}
}
