package rooms;

import java.awt.geom.Line2D;

public interface HallwayLayout {
	
	/** The distance from the interior side of one wall of the hallway to the interior side of the other wall.*/
	double width();
	
	/** The length of this hallway - that is, the distance from one opening to the other. */
	double length();
	
	/** The width of this {@link HallwayLayout HallwayLayout's} walls. */
	double wallWidth();
	
	
	RectangleLayout wall1();
	
	RectangleLayout wall2();
	
	boolean isVertical();

	boolean isHorizonal();
	
	/** Returns {@code true} iff the given line intersects either wall of this {@link HallwayLayout}. */
	default boolean lineIntersects(double x1, double y1, double x2, double y2) {
		Line2D.Double line = new Line2D.Double(x1, y1, x2, y2);
		return wall1().lineIntersects(line) || wall2().lineIntersects(line);
	}
		
}

