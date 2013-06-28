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

package eu.stratosphere.sopremo.serialization;

import java.util.Arrays;

import eu.stratosphere.nephele.configuration.Configuration;
import eu.stratosphere.pact.generic.types.TypeComparator;
import eu.stratosphere.pact.generic.types.TypeComparatorFactory;
import eu.stratosphere.sopremo.expressions.EvaluationExpression;
import eu.stratosphere.sopremo.pact.SopremoUtil;

public class SopremoRecordComparatorFactory implements TypeComparatorFactory<SopremoRecord> {
	public final static String DIRECTION = "sopremo.direction", KEYS = "sopremo.keys";

	private SopremoRecordLayout layout;

	private int[] keyExpressions;

	private boolean[] ascending;

	public SopremoRecordComparatorFactory(SopremoRecordLayout layout, int[] keyExpressions,
			boolean[] ascending) {
		this.layout = layout;
		this.keyExpressions = keyExpressions;
		this.ascending = ascending;
	}

	/**
	 * Initializes SopremoRecordComparatorFactory.
	 */
	public SopremoRecordComparatorFactory() {
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(this.ascending);
		result = prime * result + Arrays.hashCode(this.keyExpressions);
		result = prime * result + this.layout.hashCode();
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SopremoRecordComparatorFactory other = (SopremoRecordComparatorFactory) obj;
		return Arrays.equals(this.ascending, other.ascending) && Arrays.equals(this.keyExpressions, other.keyExpressions) &&
			this.layout.equals(other.layout);
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.pact.generic.types.TypeComparatorFactory#writeParametersToConfig(eu.stratosphere.nephele.
	 * configuration.Configuration)
	 */
	@Override
	public void writeParametersToConfig(Configuration config) {
		SopremoUtil.setObject(config, SopremoRecordLayout.LAYOUT_KEY, this.layout);
		SopremoUtil.setObject(config, KEYS, this.keyExpressions);
		SopremoUtil.setObject(config, DIRECTION, this.ascending);
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.pact.generic.types.TypeComparatorFactory#readParametersFromConfig(eu.stratosphere.nephele.
	 * configuration.Configuration, java.lang.ClassLoader)
	 */
	@Override
	public void readParametersFromConfig(Configuration config, ClassLoader cl) throws ClassNotFoundException {
		this.ascending = SopremoUtil.getObject(config, DIRECTION, null);
		this.keyExpressions = SopremoUtil.getObject(config, KEYS, null);
		this.layout = SopremoUtil.getObject(config, SopremoRecordLayout.LAYOUT_KEY, null);
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.pact.generic.types.TypeComparatorFactory#createComparator()
	 */
	@Override
	public TypeComparator<SopremoRecord> createComparator() {
		return new SopremoRecordComparator(this.layout, this.keyExpressions, this.ascending);
	}

}
