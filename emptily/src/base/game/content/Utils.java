package base.game.content;

import java.util.*;

import base.game.*;
import hallways.*;
import rooms.*;
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
	
	public static Collection<Platform> getRoomPlatforms(RoomLayout layout, double tlx, double tly) {
		Collection<Platform> platforms = new ArrayList<>();
		double t = layout.borderThickness(), iw = layout.interiorWidth(), ih = layout.interiorHeight();
		getHorizontalSide(iw, t, tlx, tly, layout.topGaps(), platforms);
		getHorizontalSide(iw, t, tlx, tly + t + ih, layout.bottomGaps(), platforms);
		getVerticalSide(ih, t, tlx, tly, layout.leftGaps(), platforms);
		getVerticalSide(ih, t, tlx + t + iw, tly, layout.rightGaps(), platforms);
		for(RectangleLayout r : layout.interiorRectsUnmodifiable())
			platforms.add(new Platform(tlx + r.x(), tly + r.y(), r.width(), r.height()));
		return platforms;
	}
	
	private static void getHorizontalSide(double iw, double t, double x0, double y,
			HorizontalGapCollection gcoll, Collection<Platform> platforms) {
		double x = x0;
		//assuming 1+ gaps:
		List<HorizontalGap> hgaps = gcoll.sorted(WallDirection.LEFT_TO_RIGHT);
		for(int i = 0; i < hgaps.size(); i++) {
			HorizontalGap gap = hgaps.get(i);
			Platform p = Platform.fromCorners(x, y, x0 + t + gap.leftDist(), y + t);
			platforms.add(p);
			x = x0 + t + iw - gap.rightDist();
		}
		//draw last section, after the last gap:
		platforms.add(Platform.fromCorners(x, y, x0 + iw + 2 * t, y + t));
	}
	
	private static void getVerticalSide(double ih, double t, double x, double y0, VerticalGapCollection vcoll,
			Collection<Platform> platforms) {
		double y = y0;
		List<VerticalGap> vgaps = vcoll.sorted(WallDirection.TOP_TO_BOTTOM);
		for(int i = 0; i < vgaps.size(); i++) {
			VerticalGap gap = vgaps.get(i);
			Platform p = Platform.fromCorners(x, y, x + t, y0 + t + gap.topDist());
			platforms.add(p);
			y = y0 + t + ih - gap.bottomDist();
		}
		platforms.add(Platform.fromCorners(x, y, x + t, y0 + 2 * t + ih));
	}
	
	public static Collection<Platform> getHallwayPlatforms(HallwayInfo hi) {
		return getHallwayPlatforms(hi, false);
	}
	
	public static Collection<Platform> getHallwayPlatforms(HallwayInfo hi, boolean bordered) {
		Collection<Platform> platforms = new ArrayList<>();
		HallwayLayout hl = hi.layout();
		double tlx = hi.tlx(), tly = hi.tly();
		if(hl.isVertical()) {
			platforms.add(new Platform(tlx, tly, hl.wallWidth(), hl.length(), bordered));
			platforms.add(new Platform(tlx + hl.wallWidth() + hl.girth(), tly, hl.wallWidth(), hl.length(), bordered));
		}
		else {
			platforms.add(new Platform(tlx, tly, hl.length(), hl.wallWidth(), bordered));
			platforms.add(new Platform(tlx, tly + hl.wallWidth() + hl.girth(), hl.length(), hl.wallWidth(), bordered));
		}
		return platforms;
	}
	
}
