/***********************************************************************************************************************
 *
 * Copyright (C) 2010 by the Stratosphere project (http://stratosphere.eu)
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
package eu.stratosphere.sopremo.testing;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import eu.stratosphere.sopremo.EqualCloneTest;
import eu.stratosphere.sopremo.io.Sink;
import eu.stratosphere.sopremo.operator.Operator;
import eu.stratosphere.sopremo.operator.SopremoPlan;

/**
 * 
 */
@Ignore
public abstract class SopremoOperatorTestBase<T extends Operator<T>> extends EqualCloneTest<T>{

	@Test
	public void testPlanSerialization() {
		final Kryo k = new Kryo();

		for (T original : getInstances()) {
			final SopremoPlan plan = new SopremoPlan();
			plan.setSinks(new Sink("file:///dummy").withInputs(original));
			
			final ByteArrayOutputStream baos = new ByteArrayOutputStream();
			final Output output = new Output(baos);
			k.writeClassAndObject(output, plan);
			output.close();
			final ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
			final SopremoPlan deserialized = (SopremoPlan) k.readClassAndObject(new Input(bais));

			Assert.assertEquals(plan, deserialized);
		}
	}

	protected List<T> getInstances() {
		List<T> instances = new ArrayList<T>();
		instances.add(this.first);
		instances.add(this.second);
		instances.addAll(this.more);
		return instances;
	}
	
	@Test
	public void testPlanClone() throws IllegalAccessException {
		for (T original : getInstances()) {
			final SopremoPlan plan = new SopremoPlan();
			plan.setSinks(new Sink("file:///dummy").withInputs(original));
			final Object clone = plan.clone();
			this.testPropertyClone(SopremoPlan.class, plan, clone);
		}
	}
}
