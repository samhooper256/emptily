package rooms;

import javafx.geometry.Side;

public interface DoorGap {
	
	static DoorGap of(Side side, double dist1, double dist2) {
		return new DoorGapImpl(side, dist1, dist2);
	}
	
	/** The {@link Side} of the {@link RoomLayout room} this {@link DoorGap} is on.*/
	Side side();

	/**
	 * <ul>
	 * <li>If this gap's {@link #side() side} is {@link Side#TOP TOP} or {@link Side#BOTTOM BOTTOM}, returns the
	 * distance from the left wall to the left side of this {@link DoorGap}.</li>
	 * <li>If this gap's {@link #side() side} is {@link Side#LEFT LEFT} or {@link Side#RIGHT RIGHT}, returns the distance
	 * from the top wall to the top side of this {@link DoorGap}.</li>
	 * </ul>
	 */
	double dist1();
	
	/**
	 * <ul>
	 * <li>If this gap's {@link #side() side} is {@link Side#TOP TOP} or {@link Side#BOTTOM BOTTOM}, returns the
	 * distance from the right wall to the right side of this {@link DoorGap}.</li>
	 * <li>If this gap's {@link #side() side} is {@link Side#LEFT LEFT} or {@link Side#RIGHT RIGHT}, returns the
	 * distance from the bottom wall to bottom side of this {@link DoorGap}.</li>
	 * </ul>
	 */
	double dist2();
	
}
