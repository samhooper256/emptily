package rooms;

import java.util.*;

record FloorPlanImpl(RoomInfo startingRoom, Set<RoomInfo> rooms, List<HallwayInfo> hallways) implements FloorPlan {

}
