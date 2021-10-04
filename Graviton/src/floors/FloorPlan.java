package floors;

import java.util.*;

import hallways.HallwayInfo;
import rooms.RoomInfo;

public interface FloorPlan {
	
	static FloorPlan of(RoomInfo startingRoom, Set<RoomInfo> rooms, List<HallwayInfo> hallways) {
		return new FloorPlanImpl(startingRoom, rooms, hallways);
	}

	RoomInfo startingRoom();
	
	Set<RoomInfo> rooms();
	
	Collection<HallwayInfo> hallways();
	
}
