package rooms;

import java.util.Set;

record FloorPlanImpl(RoomInfo startingRoom, Set<RoomInfo> rooms, Set<HallwayInfo> hallways) implements FloorPlan {

}
