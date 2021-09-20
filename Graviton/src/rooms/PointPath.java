package rooms;

import java.util.List;

import javafx.geometry.Point2D;

/** A mutable path of points. */
public interface PointPath extends List<Point2D> {
	
	static PointPath empty() {
		return new PointPathImpl();
	}
	
	static PointPath withPoints(Point2D... points) {
		return new PointPathImpl(points);
	}
	
	default Point2D start() {
		return isEmpty() ? null : get(0);
	}
	
	default Point2D end() {
		return isEmpty() ? null : get(size() - 1);
	}
	
	default void reverse() {
		for(int i = 0; i < size() / 2; i++) {
			Point2D temp = get(i);
			set(i, get(size() - i - 1));
			set(size() - i - 1, temp);
		}
	}
	
}
