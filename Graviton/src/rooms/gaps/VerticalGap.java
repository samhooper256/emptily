package rooms.gaps;

import javafx.geometry.Side;
import rooms.RoomLayout;

/** {@link #side()} is {@link Side#LEFT LEFT} or {@link Side#RIGHT RIGHT}.*/
public sealed interface VerticalGap extends DoorGap permits VerticalGapImpl {

	static VerticalGap of(Side side, double topDist, double bottomDist) {
		return new VerticalGapImpl(side, topDist, bottomDist);
	}
	
	double topDist();
	
	double bottomDist();
	
	/** Returns {@link #topDist()}.*/
	@Override
	default double dist1() {
		return topDist();
	}
	
	/** Returns {@link #bottomDist()}.*/
	@Override
	default double dist2() {
		return bottomDist();
	}
	
	/** The {@link Side} of the {@link RoomLayout room} this {@link VerticalGap} is on, either {@link Side#LEFT LEFT} or
	 * {@link Side#RIGHT RIGHT}.*/
	@Override
	Side side();
	
	@Override
	default double sizeIn(RoomLayout room) {
		return room.interiorHeight() - topDist() - bottomDist();
	}
	
}
