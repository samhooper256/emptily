package rooms;

import java.util.Collection;

import javafx.geometry.Point2D;

public interface RoomLayout {
	
	static Collection<RoomLayout> all() {
		return RoomLayoutHelper.all();
	}
	
	static RoomLayout random() {
		return RoomLayoutHelper.random();
	}
	
	static RoomLayout of(double width, double height, RectangleLayoutImpl... rects) {
		return new RoomLayoutImpl(width, height, rects);
	}
	
	default double borderThickness() {
		return 10;
	}
	
	double width();
	
	double height();
	
	Collection<RectangleLayout> rectsUnmodifiable();
	
	/** Assumes the given point is in the coordinate space of room. Returns {@code true} if any of this
	 * room's {@link #rectsUnmodifiable() rectangles} contain the given point.*/
	default boolean containsInRect(Point2D point) {
		for(RectangleLayout r : rectsUnmodifiable())
			if(r.contains(point))
				return true;
		return false;
	}

	/** Assumes points are in the coordinate space of this room.*/
	default boolean intervisible(Point2D a, Point2D b) {
		return intervisible(a.getX(), a.getY(), b.getX(), b.getY());
	}
	
	/** Assumes points are in the coordinate space of this room.*/
	boolean intervisible(double x1, double y1, double x2, double y);
	
}
