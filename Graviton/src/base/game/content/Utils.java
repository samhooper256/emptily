package base.game.content;

import base.game.Door;
import rooms.RoomInfo;
import rooms.gaps.*;

final class Utils {
	
	private Utils() {
		
	}
	
	public static Door createTopDoor(RoomInfo ri, HorizontalGap dg) {
		return Door.horizontal(ri.tlx() + ri.layout().borderThickness() + dg.leftDist(), ri.tly(), dg.sizeIn(ri),
				ri.layout().borderThickness());
	}
	
	public static Door createBottomDoor(RoomInfo ri, HorizontalGap dg) {
		return Door.horizontal(ri.tlx() + ri.layout().borderThickness() + dg.leftDist(), ri.tly() +
				ri.layout().borderThickness() + ri.layout().interiorHeight(), dg.sizeIn(ri),
				ri.layout().borderThickness());
	}
	
	public static Door createLeftDoor(RoomInfo ri, VerticalGap dg) {
		return Door.vertical(ri.tlx(), ri.tly() + ri.layout().borderThickness() + dg.topDist(),
				ri.layout().borderThickness(), dg.sizeIn(ri));
	}
	
	public static Door createRightDoor(RoomInfo ri, VerticalGap dg) {
		return Door.vertical(ri.tlx() + ri.layout().borderThickness() + ri.layout().interiorWidth(),
				ri.tly() + ri.layout().borderThickness() + dg.topDist(),
				ri.layout().borderThickness(), dg.sizeIn(ri));
	}
	
}
