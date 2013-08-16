package eu.stratosphere.sopremo.packages;

import java.util.Arrays;

public class DefaultNameChooser implements NameChooser {
	private final int[] preferredOrder;

	public DefaultNameChooser() {
		this(3, 0, 1, 2);
	}

	public DefaultNameChooser(final int... preferredOrder) {
		this.preferredOrder = preferredOrder;
	}

	@Override
	public String choose(final String[] nouns, final String[] verbs, final String[] adjectives, final String[] prepositions) {
		final String[][] names = { nouns, verbs, adjectives, prepositions };
		for (final int pos : this.preferredOrder) {
			final String value = this.firstOrNull(names[pos]);
			if (value != null)
				return value;
		}
		return null;
	}

	private String firstOrNull(final String[] names) {
		return names == null || names.length == 0 ? null : names[0];
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(preferredOrder);
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
		DefaultNameChooser other = (DefaultNameChooser) obj;
		if (!Arrays.equals(preferredOrder, other.preferredOrder))
			return false;
		return true;
	}
}