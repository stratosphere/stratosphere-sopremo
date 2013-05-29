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
package eu.stratosphere.sopremo.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import eu.stratosphere.sopremo.type.TextNode;

/**
 * @author Arvid Heise
 */
public final class PatternCache implements ISopremoCache {
	private final transient Map<CharSequence, Pattern> cache = new HashMap<CharSequence, Pattern>();

	public Pattern getPatternOf(TextNode node) {
		Pattern pattern = this.cache.get(node);
		if (pattern == null)
			this.cache.put(node, pattern = Pattern.compile(node.getTextValue().toString()));
		return pattern;
	}

	@Override
	public PatternCache clone() {
		return new PatternCache();
	}
}
