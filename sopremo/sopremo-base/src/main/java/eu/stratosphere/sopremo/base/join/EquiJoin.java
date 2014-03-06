package eu.stratosphere.sopremo.base.join;

import eu.stratosphere.api.common.functions.Function;
import eu.stratosphere.sopremo.operator.Internal;

@Internal
public class EquiJoin extends TwoSourceJoinBase<EquiJoin> {
	private Mode mode = Mode.NONE;

	public Mode getMode() {
		return this.mode;
	}

	/**
	 * Sets the mode of the outer join.
	 * 
	 * @param mode
	 *        the mode to set
	 */
	public void setMode(final Mode mode) {
		if (mode == null)
			throw new NullPointerException("mode must not be null");

		this.mode = mode;
	}

	/**
	 * Sets the mode of the outer join.
	 * 
	 * @param retainLeft
	 *        whether left side should be retained
	 * @param retainRight
	 *        whether right side should be retained
	 * @return this
	 */
	public EquiJoin withMode(final boolean retainLeft, final boolean retainRight) {
		final int modeIndex = (retainLeft ? 1 : 0) + 2 * (retainRight ? 1 : 0);
		this.setMode(Mode.values()[modeIndex]);
		return this;
	}

	/**
	 * Sets the mode of the outer join.
	 * 
	 * @param mode
	 *        the mode to set
	 * @return this
	 */
	public EquiJoin withMode(final Mode mode) {
		this.setMode(mode);
		return this;
	}

	@Override
	protected Class<? extends Function> getFunctionClass() {
		switch (this.mode) {
		case BOTH:
			return FullOuterJoin.Implementation.class;
		case RIGHT:
			return RightOuterJoin.Implementation.class;
		case LEFT:
			return LeftOuterJoin.Implementation.class;
		case NONE:
			return InnerJoin.Implementation.class;
		default:
			throw new IllegalStateException();
		}
	}

	public enum Mode {
		NONE, LEFT, RIGHT, BOTH;
	}
}