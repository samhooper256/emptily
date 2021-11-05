package rooms;

import java.awt.geom.Line2D;

import javafx.geometry.Point2D;

public interface RectangleLayout {
	
	static RectangleLayout of(double x, double y, double width, double height) {
		return new RectangleLayoutImpl(x, y, width, height);
	}
	
	static RectangleLayout centered(double centerX, double centerY, double width, double height) {
		return of(centerX - width / 2, centerY - height / 2, width, height);
	}
	
	double x();
	
	double y();
	
	double width();
	
	double height();
	
	default boolean contains(double x, double y) {
		return x >= x() && x <= urx() && y >= y() && y <= lly();
	}
	
	default boolean contains(Point2D point) {
		return contains(point.getX(), point.getY());
	}
	
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
