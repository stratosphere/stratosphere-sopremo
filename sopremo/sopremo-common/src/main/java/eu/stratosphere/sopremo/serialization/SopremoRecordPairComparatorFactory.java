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

import eu.stratosphere.api.common.typeutils.TypeComparator;
import eu.stratosphere.api.common.typeutils.TypePairComparator;
import eu.stratosphere.api.common.typeutils.TypePairComparatorFactory;

public class SopremoRecordPairComparatorFactory implements TypePairComparatorFactory<SopremoRecord, SopremoRecord> {
	private static final SopremoRecordPairComparatorFactory INSTANCE = new SopremoRecordPairComparatorFactory();

	/**
	 * Gets an instance of the comparator factory. The instance is shared, since the factory is a
	 * stateless class.
	 * 
	 * @return An instance of the comparator factory.
	 */
	public static final SopremoRecordPairComparatorFactory get() {
		return INSTANCE;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * eu.stratosphere.pact.common.generic.types.TypePairComparatorFactory#createComparator12(eu.stratosphere.pact.common
	 * .generic.types.TypeComparator, eu.stratosphere.pact.common.generic.types.TypeComparator)
	 */
	@Override
	public TypePairComparator<SopremoRecord, SopremoRecord> createComparator12(
			final TypeComparator<SopremoRecord> comparator1, final TypeComparator<SopremoRecord> comparator2) {
		if (!(comparator1 instanceof SopremoRecordComparator && comparator2 instanceof SopremoRecordComparator))
			throw new IllegalArgumentException("Cannot instantiate pair comparator from the given comparators.");
		final SopremoRecordComparator prc1 = (SopremoRecordComparator) comparator1;
		final SopremoRecordComparator prc2 = (SopremoRecordComparator) comparator2;

		return new SopremoRecordPairComparator(prc1.getKeyExpressionIndices(), prc1.getKeyExpressions(),
			prc2.getKeyExpressionIndices(), prc2.getKeyExpressions());
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * eu.stratosphere.pact.common.generic.types.TypePairComparatorFactory#createComparator21(eu.stratosphere.pact.common
	 * .generic.types.TypeComparator, eu.stratosphere.pact.common.generic.types.TypeComparator)
	 */
	@Override
	public TypePairComparator<SopremoRecord, SopremoRecord> createComparator21(
			final TypeComparator<SopremoRecord> comparator1, final TypeComparator<SopremoRecord> comparator2)
	{
		if (!(comparator1 instanceof SopremoRecordComparator && comparator2 instanceof SopremoRecordComparator))
			throw new IllegalArgumentException("Cannot instantiate pair comparator from the given comparators.");
		final SopremoRecordComparator prc1 = (SopremoRecordComparator) comparator1;
		final SopremoRecordComparator prc2 = (SopremoRecordComparator) comparator2;

		return new SopremoRecordPairComparator(prc2.getKeyExpressionIndices(), prc2.getKeyExpressions(),
			prc1.getKeyExpressionIndices(), prc1.getKeyExpressions());
	}
}
