package floors;

import java.util.*;

import hallways.HallwayInfo;
import rooms.RoomInfo;

record FloorPlanImpl(RoomInfo startingRoom, Set<RoomInfo> rooms, List<HallwayInfo> hallways) implements FloorPlan {

}
