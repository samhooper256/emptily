package hallways;

import javafx.geometry.BoundingBox;

/** The point (tlx, tly) is the location of the top-left corner of the hallway. This point is on the exterior edge
 * of one of the walls.*/
public interface HallwayInfo {

	static HallwayInfo of(double tlx, double tly, double girth, double length, double wallWidth, boolean vertical) {
		return of(tlx, tly, HallwayLayout.of(girth, length, wallWidth, vertical));
	}
	
	static HallwayInfo of(double tlx, double tly, HallwayLayout info) {
		return new HallwayInfoImpl(info, tlx, tly);
	}
	
	HallwayLayout layout();
	
	double tlx();
	
	double tly();
	
	default boolean lineIntersects(double x1, double y1, double x2, double y2) {
		return layout().lineIntersects(x1 - tlx(), y1 - tly(), x2 - tlx(), y2 - tly());
	}
	
	default BoundingBox bounds() {
		return new BoundingBox(tlx(), tly(), layout().width(), layout().height());
	}
	
}
