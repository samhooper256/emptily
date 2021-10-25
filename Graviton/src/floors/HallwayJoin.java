package floors;

import hallways.HallwayInfo;
import rooms.RoomInfo;

public record HallwayJoin(RoomInfo a, HallwayInfo info, RoomInfo b) {

	RoomInfo other(RoomInfo ri) {
		if(ri.equals(a))
			return b;
		if(ri.equals(b))
			return a;
		throw new IllegalArgumentException("The given RoomInfo is not an end of the hallway");
	}
	
}
