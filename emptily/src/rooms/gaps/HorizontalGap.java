package rooms.gaps;

import javafx.geometry.Side;
import rooms.RoomLayout;

/** {@link #side()} is {@link Side#TOP TOP} or {@link Side#BOTTOM BOTTOM}.*/
public interface HorizontalGap extends DoorGap /* permits HorizontalGapImpl */ {

	static HorizontalGap of(Side side, double leftDist, double rightDist) {
		return new HorizontalGapImpl(side, leftDist, rightDist);
	}
	
	double leftDist();
	
	double rightDist();
	
	/** Returns {@link #leftDist()}.*/
	@Override
	default double dist1() {
		return leftDist();
	}
	
	/** Returns {@link #rightDist()}.*/
	@Override
	default double dist2() {
		return rightDist();
	}
	
	/** The {@link Side} of the {@link RoomLayout room} this {@link HorizontalGap} is on, either {@link Side#TOP TOP} or
	 * {@link Side#BOTTOM BOTTOM}.*/
	@Override
	Side side();
	
	/** Returns the width this {@link HorizontalGap} would be if it were in the given {@link RoomLayout}. */
	default double width(RoomLayout layout) {
		return layout.interiorWidth() - leftDist() - rightDist();
	}
	
	@Override
	default double sizeIn(RoomLayout room) {
		return room.interiorWidth() - leftDist() - rightDist();
	}
	
}
