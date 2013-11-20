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
package eu.stratosphere.sopremo.execution;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.KryoCopyable;
import com.esotericsoftware.kryo.KryoException;
import com.esotericsoftware.kryo.KryoSerializable;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import eu.stratosphere.nephele.execution.librarycache.LibraryCacheManager;
import eu.stratosphere.nephele.io.IOReadableWritable;
import eu.stratosphere.nephele.jobgraph.JobID;
import eu.stratosphere.sopremo.SopremoEnvironment;
import eu.stratosphere.sopremo.operator.SopremoPlan;
import eu.stratosphere.sopremo.pact.SopremoUtil;

/**
 * Represents a request to a {@link MeteorExecutor} that encapsulates the query and optional settings.
 * 
 * @author Arvid Heise
 */
public class ExecutionRequest implements KryoSerializable, KryoCopyable<ExecutionRequest>, IOReadableWritable {
	private SopremoPlan query;

	private ExecutionMode mode = ExecutionMode.RUN;

	/**
	 * Initializes ExecutionRequest with the given query.
	 * 
	 * @param query
	 *        the query to execute
	 */
	public ExecutionRequest(SopremoPlan query) {
		this.query = query;
	}

	/**
	 * Needed for deserialization.
	 */
	public ExecutionRequest() {
	}

	public ExecutionMode getMode() {
		return this.mode;
	}

	/**
	 * Returns the query.
	 * 
	 * @return the query
	 */
	public SopremoPlan getQuery() {
		return this.query;
	}

	public void setMode(ExecutionMode mode) {
		if (mode == null)
			throw new NullPointerException("mode must not be null");

		this.mode = mode;
	}

	public enum ExecutionMode {
		RUN, RUN_WITH_STATISTICS;
	}

	/*
	 * (non-Javadoc)
	 * @see com.esotericsoftware.kryo.KryoSerializable#write(com.esotericsoftware.kryo.Kryo,
	 * com.esotericsoftware.kryo.io.Output)
	 */
	@Override
	public void write(Kryo kryo, Output output) {
		kryo.writeObject(output, this.mode);
		kryo.writeObject(output, new ArrayList<String>(this.query.getRequiredPackages()));
		kryo.writeObject(output, this.query);
	}

	/*
	 * (non-Javadoc)
	 * @see com.esotericsoftware.kryo.KryoSerializable#read(com.esotericsoftware.kryo.Kryo,
	 * com.esotericsoftware.kryo.io.Input)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void read(Kryo kryo, Input input) {
		this.mode = kryo.readObject(input, ExecutionMode.class);
		ArrayList<String> requiredPackages = kryo.readObject(input, ArrayList.class);

		final JobID dummId = JobID.generate();
		ClassLoader oldClassLoader = kryo.getClassLoader();
		try {
			LibraryCacheManager.register(dummId,
				requiredPackages.toArray(new String[requiredPackages.size()]));
			kryo.setClassLoader(LibraryCacheManager.getClassLoader(dummId));
			this.query = kryo.readObject(input, SopremoPlan.class);
		} catch (Exception e) {
			SopremoUtil.LOG.error(e.getMessage());
			throw new KryoException(e);
		} finally {
			kryo.setClassLoader(oldClassLoader);
			try {
				LibraryCacheManager.unregister(dummId);
			} catch (Throwable e) {
				SopremoUtil.LOG.error(e.getMessage());
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.esotericsoftware.kryo.KryoCopyable#copy(com.esotericsoftware.kryo.Kryo)
	 */
	@Override
	public ExecutionRequest copy(Kryo kryo) {
		final ExecutionRequest er = new ExecutionRequest(this.query);
		er.setMode(this.mode);
		return er;
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.nephele.io.IOReadableWritable#read(java.io.DataInput)
	 */
	@Override
	public void read(DataInput in) throws IOException {
		this.mode = ExecutionMode.values()[in.readInt()];

		ArrayList<String> requiredPackages = new ArrayList<String>();
		for (int count = in.readInt(); count > 0; count--)
			requiredPackages.add(in.readUTF());
		this.query = null;
		byte[] planBuffer = new byte[in.readInt()];
		in.readFully(planBuffer);

		final JobID dummId = new JobID();
		try {
			LibraryCacheManager.register(dummId,
				requiredPackages.toArray(new String[requiredPackages.size()]));
			SopremoEnvironment.getInstance().setClassLoader(LibraryCacheManager.getClassLoader(dummId));
			this.query = SopremoUtil.deserialize(planBuffer, SopremoPlan.class);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				LibraryCacheManager.unregister(dummId);
			} catch (IOException e) {
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.nephele.io.IOReadableWritable#write(java.io.DataOutput)
	 */
	@Override
	public void write(DataOutput out) throws IOException {
		out.writeInt(this.mode.ordinal());

		final List<String> requiredPackages = this.query.getRequiredPackages();
		out.writeInt(requiredPackages.size());
		for (String packageName : requiredPackages)
			out.writeUTF(packageName);

		final byte[] planBuffer = SopremoUtil.serializable(this.query);
		out.writeInt(planBuffer.length);
		out.write(planBuffer);
	}

}
