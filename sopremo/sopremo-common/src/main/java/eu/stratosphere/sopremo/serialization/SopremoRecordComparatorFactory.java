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

import eu.stratosphere.api.common.typeutils.TypeComparator;
import eu.stratosphere.api.common.typeutils.TypeComparatorFactory;
import eu.stratosphere.configuration.Configuration;
import eu.stratosphere.sopremo.pact.SopremoUtil;

public class SopremoRecordComparatorFactory implements TypeComparatorFactory<SopremoRecord> {
	public final static String DIRECTION = "sopremo.direction", KEYS = "sopremo.keys";

	private SopremoRecordLayout layout;

	private int[] keyExpressions;

	private boolean[] ascending;

	/**
	 * Initializes SopremoRecordComparatorFactory.
	 */
	public SopremoRecordComparatorFactory() {
	}

	public SopremoRecordComparatorFactory(final SopremoRecordLayout layout, final int[] keyExpressions,
			final boolean[] ascending) {
		this.layout = layout;
		this.keyExpressions = keyExpressions;
		this.ascending = ascending;
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.api.typeutils.TypeComparatorFactory#createComparator()
	 */
	@Override
	public TypeComparator<SopremoRecord> createComparator() {
		return new SopremoRecordComparator(this.layout, this.keyExpressions, this.ascending);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (this.getClass() != obj.getClass())
			return false;
		final SopremoRecordComparatorFactory other = (SopremoRecordComparatorFactory) obj;
		return Arrays.equals(this.ascending, other.ascending) &&
			Arrays.equals(this.keyExpressions, other.keyExpressions) &&
			this.layout.equals(other.layout);
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

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.api.typeutils.TypeComparatorFactory#readParametersFromConfig(eu.stratosphere.nephele.
	 * configuration.Configuration, java.lang.ClassLoader)
	 */
	@Override
	public void readParametersFromConfig(final Configuration config, final ClassLoader cl)
			throws ClassNotFoundException {
		this.ascending = SopremoUtil.getObject(config, DIRECTION, null);
		this.keyExpressions = SopremoUtil.getObject(config, KEYS, null);
		this.layout = SopremoUtil.getObject(config, SopremoRecordLayout.LAYOUT_KEY, null);
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.api.typeutils.TypeComparatorFactory#writeParametersToConfig(eu.stratosphere.nephele.
	 * configuration.Configuration)
	 */
	@Override
	public void writeParametersToConfig(final Configuration config) {
		SopremoUtil.setObject(config, SopremoRecordLayout.LAYOUT_KEY, this.layout);
		SopremoUtil.setObject(config, KEYS, this.keyExpressions);
		SopremoUtil.setObject(config, DIRECTION, this.ascending);
	}

}
