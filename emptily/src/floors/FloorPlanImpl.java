package floors;

import java.util.*;

import hallways.HallwayInfo;
import rooms.RoomInfo;

final class FloorPlanImpl implements FloorPlan {

	private final RoomInfo startingRoom;
	private final Set<RoomInfo> rooms;
	private final List<HallwayInfo> hallways;
	private final Map<RoomInfo, Set<HallwayJoin>> joinMap;
	
	FloorPlanImpl(RoomInfo startingRoom, Set<RoomInfo> rooms, List<HallwayInfo> hallways,
			Map<RoomInfo, Set<HallwayJoin>> joinMap) {
		this.startingRoom = startingRoom;
		this.rooms = rooms;
		this.hallways = hallways;
		this.joinMap = joinMap;
	}

	@Override
	public RoomInfo startingRoom() {
		return startingRoom;
	}

	@Override
	public Set<RoomInfo> rooms() {
		return rooms;
	}

	@Override
	public Collection<HallwayInfo> hallways() {
		return hallways;
	}

	@Override
	public Set<RoomInfo> adjacentRooms(RoomInfo ri) {
		Set<RoomInfo> adj = new HashSet<>();
		for(HallwayJoin j : joinMap.get(ri))
			adj.add(j.other(ri));
		return adj;
	}

	@Override
	public Set<HallwayJoin> joins(RoomInfo ri) {
		return joinMap.get(ri);
	}
	
}
