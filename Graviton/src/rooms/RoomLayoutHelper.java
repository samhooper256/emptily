package rooms;

import java.util.*;

import utils.RNG;

class RoomLayoutHelper {
	
	private static List<RoomLayout> layouts;
	
	static {
		layouts = List.of(
				new RoomLayoutImpl(400, 400,
						new RectangleLayoutImpl(100, 100, 200, 100),
						new RectangleLayoutImpl(80, 240, 30, 10),
						new RectangleLayoutImpl(200, 200, 100, 100)
				)
		);
	}
	
	public static Collection<RoomLayout> all() {
		return layouts;
	}
	
	public static RoomLayout random() {
		return layouts.get(RNG.intExclusive(0, layouts.size()));
	}
	
	private RoomLayoutHelper() {
		
	}
	
}
