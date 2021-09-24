package rooms;

import java.util.*;

import utils.RNG;

class RoomLayoutHelper {
	
	private static List<RoomLayout> layouts;
	
	static {
		layouts = List.of(
				RoomLayout.builder()
				.setSize(400, 400)
				.setRects(
						new RectangleLayoutImpl(100, 100, 200, 100),
						new RectangleLayoutImpl(80, 240, 30, 10),
						new RectangleLayoutImpl(200, 200, 100, 100)
				)
				.setGaps(DoorGap.left(140, 140), DoorGap.right(140, 140),
						DoorGap.top(140, 140), DoorGap.bottom(140, 140))
				.build(),
				RoomLayout.builder()
				.setSize(400, 400)
				.setRects(
						new RectangleLayoutImpl(60, 60, 90, 90),
						new RectangleLayoutImpl(250, 250, 90, 90)
				)
				.setGaps(DoorGap.left(140, 140), DoorGap.right(140, 140),
						DoorGap.top(140, 140), DoorGap.bottom(140, 140))
				.build()
		);
	}
	
	public static List<RoomLayout> all() {
		return layouts;
	}
	
	public static RoomLayout random() {
		return layouts.get(RNG.intExclusive(0, layouts.size()));
	}
	
	private RoomLayoutHelper() {
		
	}
	
}
