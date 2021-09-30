package rooms;

import java.util.*;

import utils.RNG;

class RoomLayoutHelper {
	
	private static final List<RoomLayout> layouts;
	private static final double INTERIOR_CUT = RoomLayout.DEFAULT_BORDER_THICKNESS * 2;
	
	static {
//		layouts = List.of(
//				RoomLayout.builder()
//				.setSize(400, 400)
//				.setRects(
//						new RectangleLayoutImpl(100, 100, 200, 100),
//						new RectangleLayoutImpl(80, 240, 30, 10),
//						new RectangleLayoutImpl(200, 200, 100, 100)
//				)
//				.setGaps(leftGap(100, 400), rightGap(100, 400),
//						topGap(100, 400), bottomGap(100, 400))
//				.build(),
//				RoomLayout.builder()
//				.setSize(400, 400)
//				.setRects(
//						new RectangleLayoutImpl(60, 60, 90, 90),
//						new RectangleLayoutImpl(250, 250, 90, 90)
//				)
//				.setGaps(leftGap(100, 400), rightGap(100, 400),
//						topGap(100, 400), bottomGap(100, 400))
//				.build()
//		);
		layouts = List.of(
				RoomLayout.builder()
				.setSize(400, 400)
				.setRects(
						new RectangleLayoutImpl(100, 100, 200, 100),
						new RectangleLayoutImpl(80, 240, 30, 10),
						new RectangleLayoutImpl(200, 200, 100, 100)
				)
				.setGaps(leftGap(100, 400), rightGap(100, 400), bottomGap(100, 400))
				.build(),
				RoomLayout.builder()
				.setSize(800, 400)
				.setRects(
						new RectangleLayoutImpl(60, 60, 90, 90),
						new RectangleLayoutImpl(250, 250, 90, 90)
				)
				.setGaps(leftGap(100, 400), rightGap(100, 400), topGap(100, 800), bottomGap(100, 800))
				.build()
		);
	}
	
	private static DoorGap topGap(double width, double exteriorLength) {
		double side = (exteriorLength - INTERIOR_CUT - width) / 2;
		return DoorGap.top(side, side);
	}
	
	private static DoorGap bottomGap(double width, double exteriorLength) {
		double side = (exteriorLength - INTERIOR_CUT - width) / 2;
		return DoorGap.bottom(side, side);
	}
	
	private static DoorGap leftGap(double width, double exteriorHeight) {
		double side = (exteriorHeight - INTERIOR_CUT - width) / 2;
		return DoorGap.left(side, side);
	}
	
	private static DoorGap rightGap(double width, double exteriorHeight) {
		double side = (exteriorHeight - INTERIOR_CUT - width) / 2;
		return DoorGap.right(side, side);
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
