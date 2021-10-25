package floors;

import java.util.*;
import java.util.stream.Collectors;

import hallways.HallwayInfo;
import rooms.RoomInfo;

public interface FloorPlan {

	RoomInfo startingRoom();
	
	Set<RoomInfo> rooms();
	
	Collection<HallwayInfo> hallways();
	
	/** Assumes the given {@link RoomInfo} is in this {@link FloorPlan}. */
	Set<RoomInfo> adjacentRooms(RoomInfo ri);
	
	Set<HallwayJoin> joins(RoomInfo ri);
	
	default Set<HallwayInfo> exits(RoomInfo ri) {
		return joins(ri).stream().map(HallwayJoin::info).collect(Collectors.toCollection(HashSet::new));
	}
	
	/** Includes the starting room. */
	default int roomCount() {
		return rooms().size();
	}
	
}
