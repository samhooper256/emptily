package rooms;

import java.util.Set;

public interface FloorPlan {
	
	static FloorPlan of(RoomInfo startingRoom, Set<RoomInfo> rooms, Set<HallwayInfo> hallways) {
		return new FloorPlanImpl(startingRoom, rooms, hallways);
	}

	RoomInfo startingRoom();
	
	Set<RoomInfo> rooms();
	
	Set<HallwayInfo> hallways();
	
}
