package floors;

import java.util.*;

import rooms.RectangleLayout;
import rooms.RoomLayout;
import rooms.gaps.DoorGap;
import utils.RNG;

public enum Floor {

	FIRST(5, Arrays.asList(
			RoomLayout.builder("1square")
			.setSize(400, 400)
			.setGaps(leftGap(100, 400), rightGap(100, 400), topGap(100, 400), bottomGap(100, 400))
			.addBasicEnemyCentered(200, 200)
			.setPlayerSpawn(200, 200)
			.build(),
			RoomLayout.builder("1horizrect")
			.setSize(800, 400)
			.setRects(RectangleLayout.of(180, 200, 400, 40))
			.setGaps(topGap(100, 800), bottomGap(100, 800))
			.addBasicEnemy(90, 190)
			.addBasicEnemy(690, 190)
			.setPlayerSpawn(400, 150)
			.build(),
			RoomLayout.builder("1vertrect")
			.setSize(400, 800)
			.setRects(RectangleLayout.of(100, 100, 200, 200), RectangleLayout.of(100, 500, 200, 200))
			.setGaps(leftGap(100, 800), rightGap(100, 800))
			.addBasicEnemyCentered(200, 50)
			.addBasicEnemyCentered(200, 750)
			.setPlayerSpawn(200, 400)
			.build(),
			RoomLayout.builder("1big")
			.setSize(1000, 500)
			.setRects(RectangleLayout.of(350, 250, 300, 250))
			.setGaps(leftGap(100, 500), rightGap(100, 500), topGap(100, 1000),
					DoorGap.bottom(200 - RoomLayout.BORDER_THICKNESS, 700 - RoomLayout.BORDER_THICKNESS))
			.addBasicEnemiesCentered(150, 125, 850, 125, 150, 75, 850, 75)
			.addSpikesCentered(25, 25, 975, 25, 335, 475, 665, 475)
			.setPlayerSpawn(500, 200)
			.build()
	)),
	SECOND(7, Arrays.asList(
			RoomLayout.builder("2square")
			.setSize(400, 400)
			.setGaps(leftGap(100, 400), rightGap(100, 400), /*topGap(100, 400),*/ bottomGap(100, 400))
			.addBasicEnemyCentered(25, 25)
			.addBasicEnemyCentered(375, 375)
			.addSpikesCentered(25, 375, 25, 350, 50, 375, 375, 25, 350, 25, 375, 50)
			.setPlayerSpawn(200, 200)
			.build(),
			RoomLayout.builder("2fourbox")
			.setSize(600, 600)
			.setRects(RectangleLayout.centered(150, 150, 150, 150), RectangleLayout.centered(450, 150, 150, 150),
					RectangleLayout.centered(150, 450, 150, 150), RectangleLayout.centered(450, 450, 150, 150))
			.setGaps(leftGap(100, 600), rightGap(100, 600))
			.addBasicEnemyCentered(300, 25)
			.addBasicEnemyCentered(300, 75)
			.addBasicEnemyCentered(300, 575)
			.addBasicEnemyCentered(300, 525)
			.addSpikesCentered(25, 25, 25, 575, 575, 25, 575, 575, 250, 215, 350, 215, 250, 386, 350, 385)
			.setPlayerSpawn(300, 300)
			.build(),
			RoomLayout.builder("2enemiesblocked")
			.setSize(600, 600)
			.setRects(RectangleLayout.centered(300, 80, 300, 40), RectangleLayout.centered(300, 520, 300, 40),
					RectangleLayout.centered(80, 300, 40, 300), RectangleLayout.centered(520, 300, 40, 300))
			.setGaps(leftGap(100, 600), rightGap(100, 600), topGap(100, 600), bottomGap(100, 600))
			.addBasicEnemiesCentered(250, 250, 250, 350, 350, 250, 350, 350)
			.addCannonCentered(300, 300)
			.setPlayerSpawn(300, 300)
			.build(),
			RoomLayout.builder("2verticalcornerE")
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
			.build(),
			RoomLayout.builder("2spikewall")
			.setSize(600, 400)
			.setRects(RectangleLayout.centered(120, 200, 60, 250), RectangleLayout.centered(480, 200, 60, 250))
			.setPlayerSpawn(200, 300)
			.setGaps(bottomGap(100, 600))
			.addCannonCentered(300, 100)
			.addSpikesCentered(165, 200, 195, 200, 225, 200, 255, 200, 285, 200, 315, 200, 345, 200, 375, 200, 405, 200,
					435, 200)
			.build(),
			RoomLayout.builder("2horizlong")
			.setSize(1200, 300)
			.setRects(RectangleLayout.centered(600, 80, 200, 40))
			.addBasicEnemiesCentered(400, 90, 400, 210, 800, 90, 800, 210)
			.addCannonCentered(600, 40)
			.addSpikesCentered(25, 25, 1175, 25, 25, 275, 1175, 275)
			.setGaps(bottomGap(100, 1200),
					DoorGap.top(200 - RoomLayout.BORDER_THICKNESS, 900 - RoomLayout.BORDER_THICKNESS),
					DoorGap.top(900 - RoomLayout.BORDER_THICKNESS, 200 - RoomLayout.BORDER_THICKNESS))
			.setPlayerSpawn(600, 150)
			.build()
	)),
	THIRD(8, Arrays.asList(
			RoomLayout.builder("3square")
			.setSize(400, 400)
			.setPlayerSpawn(200, 200)
			.addBasicEnemiesCentered(25, 25, 25, 375, 375, 25, 375, 375)
			.addSpikesCentered(200, 200, 175, 175, 175, 225, 225, 175, 225, 225)
			.setGaps(leftGap(100, 400), rightGap(100, 400), topGap(100, 400), bottomGap(100, 400))
			.build(),
			RoomLayout.builder("3navigate")
			.setSize(600, 300)
			.setPlayerSpawn(300, 150)
			.addCannonsCentered(120, 30, 480, 270)
			.addBasicEnemiesCentered(300, 150)
			.addSpikesCentered(25, 25, 50, 50, 75, 75, 100, 100, 125, 125, 150, 150, 175, 175,
					175, 275, 200, 275, 225, 275, 250, 275, 275, 275,
					575, 275, 550, 250, 525, 225, 500, 200, 475, 175, 450, 150, 425, 125,
					425, 25, 400, 25, 375, 25, 350, 25, 325, 25)
			.setGaps(leftGap(100, 300), rightGap(100, 300))
			.build(),
			RoomLayout.builder("3forktoleft")
			.setSize(400, 800)
			.setPlayerSpawn(200, 400)
			.setRects(RectangleLayout.centered(75, 225, 150, 50), RectangleLayout.centered(75, 575, 150, 50))
			.setGaps(rightGap(100, 800), DoorGap.left(50 - RoomLayout.BORDER_THICKNESS, 650 - RoomLayout.BORDER_THICKNESS),
					DoorGap.left(650 - RoomLayout.BORDER_THICKNESS, 50 - RoomLayout.BORDER_THICKNESS))
			.addSpikesCentered(25, 375, 25, 400, 25, 425, 25, 275, 25, 525, 375, 25, 375, 775,
					375, 325, 375, 475, 25, 25, 25, 775)
			.addBasicEnemiesCentered(300, 50, 350, 50, 300, 100, 350, 100,
					300, 700, 300, 750, 350, 700, 350, 750)
			.addCannonsCentered(30, 470, 30, 330)
			.build(),
			RoomLayout.builder("3big")
			.setSize(1000, 1000)
			.setRects(RectangleLayout.centered(150, 150, 150, 150), RectangleLayout.centered(850, 150, 150, 150),
					RectangleLayout.centered(150, 850, 150, 150), RectangleLayout.centered(850, 850, 150, 150))
			.setPlayerSpawn(500, 500)
			.addCannonsCentered(100, 50, 50, 100, 900, 50, 950, 100, 50, 900, 100, 950, 950, 900, 900, 950)
			.addSpikesCentered(500, 500,400, 400, 400, 600, 600, 400, 600, 600,
					30, 220, 55, 220, 220, 30, 220, 55, 780, 30, 780, 55, 970, 220, 945, 220,
					30, 780, 55, 780, 220, 970, 220, 945, 780, 970, 780, 945, 945, 780, 970, 780)
			.addBasicEnemiesCentered(250, 150, 150, 250, 250, 250,
					750, 850, 850, 750, 750, 750,
					150, 750, 250, 850, 250, 750,
					750, 150, 850, 250, 750, 250)
			.setGaps(leftGap(100, 1000), rightGap(100, 1000), topGap(100, 1000), bottomGap(100, 1000))
			.build()
	)),
	FOURTH(2, Arrays.asList(
			RoomLayout.builder("4only")
			.setSize(1000, 1000)
			.addBasicEnemiesCentered(100, 100, 100, 200, 100, 300, 100, 400, 100, 500, 100, 600, 100, 700, 100, 800, 100, 900,
			900, 100, 900, 200, 900, 300, 900, 400, 900, 500, 900, 600, 900, 700, 900, 800, 900, 900)
			.addCannonsCentered(400, 400, 400, 600, 600, 400, 600, 600)
			.addSpikesCentered(25, 25, 975, 25, 25, 975, 975, 975, 300, 25, 700, 25, 300, 975, 700, 975,
					500, 500,
					475, 475, 450, 450, 425, 425,
					525, 475, 550, 450, 575, 425,
					525, 525, 550, 550, 575, 575,
					475, 525, 450, 550, 425, 575,
					25, 300, 25, 700, 975, 300, 975, 700)
			.setGaps(topGap(100, 1000), bottomGap(100, 1000))
			.setPlayerSpawn(500, 500)
			.build()
	));
//	TEST(5, Arrays.asList(
//			RoomLayout.builder()
//			.setSize(400, 400)
//			.setGaps(leftGap(100, 400), rightGap(100, 400), topGap(100, 400), bottomGap(100, 400))
//			.addCannonCentered(200, 200)
//			.setPlayerSpawn(200, 200)
//			.build(),
//			RoomLayout.builder()
//			.setSize(400, 800)
//			.setRects(RectangleLayout.of(100, 100, 200, 200), RectangleLayout.of(100, 500, 200, 200))
//			.setGaps(leftGap(100, 800), rightGap(100, 800))
//			.addCannonCentered(200, 50)
//			.addSpawn(SpikeSpawn.centered(200, 400))
//			.setPlayerSpawn(200, 400)
//			.build()
//	)),
//	;
	
	public static final List<Floor> ORDER = Arrays.asList(Floor.FIRST, Floor.SECOND, Floor.THIRD, Floor.FOURTH);
	
	private static final double INTERIOR_CUT = RoomLayout.BORDER_THICKNESS * 2;

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
