package rooms;

import javafx.geometry.Side;

public sealed interface DoorGap permits HorizontalGap, VerticalGap {
	
	static HorizontalGap top(double leftDist, double rightDist) {
		return HorizontalGap.of(Side.TOP, leftDist, rightDist);
	}
	
	static HorizontalGap bottom(double leftDist, double rightDist) {
		return HorizontalGap.of(Side.BOTTOM, leftDist, rightDist);
	}
	
	static VerticalGap left(double topDist, double bottomDist) {
		return VerticalGap.of(Side.LEFT, topDist, bottomDist);
	}
	
	static VerticalGap right(double topDist, double bottomDist) {
		return VerticalGap.of(Side.RIGHT, topDist, bottomDist);
	}
	
	static DoorGap of(Side side, double dist1, double dist2) {
		if(side.isHorizontal())
			return HorizontalGap.of(side, dist1, dist2);
		else
			return VerticalGap.of(side, dist1, dist2);
	}
	
	/** The {@link Side} of the {@link RoomLayout room} this {@link DoorGap} is on.*/
	Side side();

	/**
	 * <ul>
	 * <li>If this gap's {@link #side() side} is {@link Side#TOP TOP} or {@link Side#BOTTOM BOTTOM}, returns the
	 * distance from the interior side of the left wall to the left side of this {@link DoorGap}.</li>
	 * <li>If this gap's {@link #side() side} is {@link Side#LEFT LEFT} or {@link Side#RIGHT RIGHT}, returns the distance
	 * from  the interior side of the top wall to the top side of this {@link DoorGap}.</li>
	 * </ul>
	 */
	double dist1();
	
	/**
	 * <ul>
	 * <li>If this gap's {@link #side() side} is {@link Side#TOP TOP} or {@link Side#BOTTOM BOTTOM}, returns the
	 * distance from  the interior side of the right wall to the right side of this {@link DoorGap}.</li>
	 * <li>If this gap's {@link #side() side} is {@link Side#LEFT LEFT} or {@link Side#RIGHT RIGHT}, returns the
	 * distance from  the interior side of the bottom wall to bottom side of this {@link DoorGap}.</li>
	 * </ul>
	 */
	double dist2();
	
	default boolean isHorizontal() {
		return this instanceof HorizontalGap;
	}
	
	default HorizontalGap asHorizontal() {
		if(isVertical())
			throw new IllegalStateException("This is not a HorizontalGap");
		return (HorizontalGap) this;
	}
	
	default boolean isVertical() {
		return this instanceof VerticalGap;
	}
	
	default VerticalGap asVertical() {
		if(isHorizontal())
			throw new IllegalStateException("This is not a VerticalGap");
		return (VerticalGap) this;
	}
	
}
