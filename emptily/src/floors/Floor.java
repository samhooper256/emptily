package floors;

import java.util.*;

import rooms.RectangleLayout;
import rooms.RoomLayout;
import rooms.gaps.DoorGap;
import utils.RNG;

public enum Floor {

	FIRST(5, Arrays.asList(
			RoomLayout.builder()
			.setSize(400, 400)
			.setGaps(leftGap(100, 400), rightGap(100, 400), topGap(100, 400), bottomGap(100, 400))
			.addBasicEnemyCentered(200, 200)
			.setPlayerSpawn(200, 200)
			.build(),
			RoomLayout.builder()
			.setSize(800, 400)
			.setRects(RectangleLayout.of(180, 200, 400, 40))
			.setGaps(topGap(100, 800), bottomGap(100, 800))
			.addBasicEnemy(90, 190)
			.addBasicEnemy(690, 190)
			.setPlayerSpawn(400, 150)
			.build(),
			RoomLayout.builder()
			.setSize(400, 800)
			.setRects(RectangleLayout.of(100, 100, 200, 200), RectangleLayout.of(100, 500, 200, 200))
			.setGaps(leftGap(100, 800), rightGap(100, 800))
			.addBasicEnemyCentered(200, 50)
			.addBasicEnemyCentered(200, 750)
			.setPlayerSpawn(200, 400)
			.build()
	)),
	SECOND(7, Arrays.asList(
			RoomLayout.builder()
			.setSize(400, 400)
			.setGaps(leftGap(100, 400), rightGap(100, 400), /*topGap(100, 400),*/ bottomGap(100, 400))
			.addBasicEnemyCentered(25, 25)
			.addBasicEnemyCentered(375, 375)
			.setPlayerSpawn(200, 200)
			.build(),
			RoomLayout.builder()
			.setSize(600, 600)
			.setRects(RectangleLayout.centered(150, 150, 150, 150), RectangleLayout.centered(450, 150, 150, 150),
					RectangleLayout.centered(150, 450, 150, 150), RectangleLayout.centered(450, 450, 150, 150))
			.setGaps(leftGap(100, 600), rightGap(100, 600))
			.addBasicEnemyCentered(300, 25)
			.addBasicEnemyCentered(300, 75)
			.addBasicEnemyCentered(300, 575)
			.addBasicEnemyCentered(300, 525)
			.setPlayerSpawn(300, 300)
			.build(),
			RoomLayout.builder()
			.setSize(400, 600)
			.setRects(RectangleLayout.centered(112.5, 125, 75, 250), RectangleLayout.centered(287.5, 125, 75, 250),
					RectangleLayout.centered(112.5, 475, 75, 250), RectangleLayout.centered(287.5, 475, 75, 250))
			.addBasicEnemiesCentered(
					25, 25,
					25, 75,
					375, 25,
					375, 75,
					25, 575,
					25, 525,
					375, 575,
					375, 525
			)
			.setPlayerSpawn(200, 300)
			.setGaps(topGap(100, 400), bottomGap(100, 400))
			.build()
	)),
	THIRD(10, Arrays.asList(
			RoomLayout.builder()
			.setSize(400, 400)
			.setGaps(leftGap(100, 400), rightGap(100, 400), topGap(100, 400), bottomGap(100, 400))
			.addBasicEnemy(20, 20)
			.addBasicEnemy(20, 360)
			.addBasicEnemy(360, 20)
			.addBasicEnemy(360, 360)
			.setPlayerSpawn(200, 200)
			.build(),
			RoomLayout.builder()
			.setSize(600, 400)
			.setGaps(DoorGap.top(90, 390), DoorGap.bottom(390, 90))
			.setRects(RectangleLayout.of(250, 10, 100, 200))
			.addBasicEnemy(20, 360)
			.addBasicEnemy(560, 20)
			.addBasicEnemy(560, 360)
			.addBasicEnemy(290, 360)
			.setPlayerSpawn(300, 250)
			.build(),
			RoomLayout.builder()
			.setSize(800, 800)
			.setGaps(topGap(100, 800), bottomGap(100, 800), leftGap(100, 800), rightGap(100, 800))
			.setRects(RectangleLayout.of(300, 300, 200, 200))
			.addBasicEnemy(20, 20)
			.addBasicEnemy(20, 60)
			.addBasicEnemy(60, 20)
			.addBasicEnemy(60, 60)
			.addBasicEnemy(100, 100)
			.addBasicEnemy(720, 20)
			.addBasicEnemy(720, 60)
			.addBasicEnemy(760, 20)
			.addBasicEnemy(760, 60)
			.addBasicEnemy(680, 100)
			.addBasicEnemy(720, 720)
			.addBasicEnemy(720, 760)
			.addBasicEnemy(760, 720)
			.addBasicEnemy(760, 760)
			.addBasicEnemy(680, 680)
			.addBasicEnemy(20, 720)
			.addBasicEnemy(60, 720)
			.addBasicEnemy(20, 760)
			.addBasicEnemy(60, 760)
			.addBasicEnemy(100, 680)
			.setPlayerSpawn(400, 200)
			.build()
	)),
	TEST(5, Arrays.asList(
			RoomLayout.builder()
			.setSize(400, 400)
			.setGaps(leftGap(100, 400), rightGap(100, 400), topGap(100, 400), bottomGap(100, 400))
			.addCannonCentered(200, 200)
			.setPlayerSpawn(200, 200)
			.build(),
			RoomLayout.builder()
			.setSize(800, 400)
			.setRects(RectangleLayout.of(50, 50, 40, 300), RectangleLayout.of(710, 50, 40, 300))
			.setGaps(topGap(100, 800), bottomGap(100, 800))
			.addCannonCentered(26, 200)
			.addCannonCentered(774, 200)
			.setPlayerSpawn(400, 150)
			.build(),
			RoomLayout.builder()
			.setSize(400, 800)
			.setRects(RectangleLayout.of(100, 100, 200, 200), RectangleLayout.of(100, 500, 200, 200))
			.setGaps(leftGap(100, 800), rightGap(100, 800))
			.addCannonCentered(200, 50)
			.addCannonCentered(200, 750)
			.setPlayerSpawn(200, 400)
			.build()
	));
	
	public static final List<Floor> ORDER = Arrays.asList(Floor.TEST);
//	public static final List<Floor> ORDER = Arrays.asList(Floor.FIRST, Floor.SECOND, Floor.THIRD);
	
	private static final double INTERIOR_CUT = RoomLayout.DEFAULT_BORDER_THICKNESS * 2;

	public static int count() {
		return ORDER.size();
	}
	
	private static DoorGap topGap(double width, double exteriorLength) {
		double side = (exteriorLength - INTERIOR_CUT - width) / 2;
		return DoorGap.top(side, side);
	}
	
	private static DoorGap bottomGap(double width, double exteriorLength) {
		double side = (exteriorLength - INTERIOR_CUT - width) / 2;
		return DoorGap.bottom(side, side);
	}
	
	private static DoorGap leftGap(double height, double exteriorHeight) {
		double side = (exteriorHeight - INTERIOR_CUT - height) / 2;
		return DoorGap.left(side, side);
	}
	
	private static DoorGap rightGap(double height, double exteriorHeight) {
		double side = (exteriorHeight - INTERIOR_CUT - height) / 2;
		return DoorGap.right(side, side);
	}
	
	private final int suggestedRoomCount;
	private final List<RoomLayout> layouts;
	
	Floor(int suggestedRoomCount, List<RoomLayout> layouts) {
		this.suggestedRoomCount = suggestedRoomCount;
		this.layouts = layouts;
	}
	
	public List<RoomLayout> layouts() {
		return layouts;
	}
	
	public RoomLayout random() {
		return layouts.get(RNG.intExclusive(0, layouts.size()));
	}
	
	public int suggestedRoomCount() {
		return suggestedRoomCount;
	}
	
}
