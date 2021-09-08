package rooms;

import java.awt.geom.Line2D;

public interface RectangleLayout {
	
	double x();
	
	double y();
	
	double width();
	
	double height();
	
	default boolean lineIntersects(double x1, double y1, double x2, double y2) {
		Line2D.Double line = new Line2D.Double(x1, y1, x2, y2);
		return lineIntersects(line);
	}
	
	boolean lineIntersects(Line2D.Double line);
	
	
	default double ulx() {
		return x();
	}
	
	default double uly() {
		return y();
	}
	
	default double urx() {
		return x() + width();
	}
	
	default double ury() {
		return y();
	}
	
	default double llx() {
		return x();
	}
	
	default double lly() {
		return y() + height();
	}
	
	default double lrx() {
		return x() + width();
	}
	
	default double lry() {
		return y() + height();
	}
	
}
