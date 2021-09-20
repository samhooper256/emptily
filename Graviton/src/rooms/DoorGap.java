package rooms;

import javafx.geometry.Side;

public interface DoorGap {
	
	static DoorGap of(Side side, double dist1, double dist2) {
		return new DoorGapImpl(side, dist1, dist2);
	}
	
	Side side();
	
	double dist1();
	
	double dist2();
	
}
