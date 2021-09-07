package rooms;

import java.util.*;

import utils.RNG;

class RoomLayoutHelper {
	
	private static List<RoomLayout> layouts;
	
	static {
		layouts = List.of(
				new RoomLayoutImpl(400, 400,
						new RectangleLayout(200, 200, 100, 100),
						new RectangleLayout(90, 90, 100, 100))
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
