package rooms;

import java.util.*;

import javafx.geometry.*;

public interface RoomLayout {
	
	double DEFAULT_BORDER_THICKNESS = 10;
	
	static List<RoomLayout> all() {
		return RoomLayoutHelper.all();
	}
	
	static RoomLayout random() {
		return RoomLayoutHelper.random();
	}
	
	static LayoutBuilder builder() {
		return new LayoutBuilder();
	}
	
	default double borderThickness() {
		return DEFAULT_BORDER_THICKNESS;
	}
	
	double exteriorWidth();
	
	default double interiorWidth() {
		return exteriorWidth() - 2 * borderThickness();
	}
	
	double exteriorHeight();
	
	default double interiorHeight() {
		return exteriorHeight() - 2 * borderThickness();
	}
	
	Collection<RectangleLayout> rectsUnmodifiable();
	
	default DoorGapCollection<? extends DoorGap> gaps(Side side) {
		return switch(side) {
			case TOP -> topGaps();
			case BOTTOM -> bottomGaps();
			case LEFT -> leftGaps();
			case RIGHT -> rightGaps();
		};
	}
	
	HorizontalGapCollection topGaps();
	
	HorizontalGapCollection bottomGaps();
	
	VerticalGapCollection leftGaps();
	
	VerticalGapCollection rightGaps();

	DoorGapCollection<DoorGap> gaps();
	
	default int gapCount(Side side) {
		return gaps(side).size();
	}
	
	/** Returns the total number of gaps on all sides of the room. */
	default int gapCount() {
		return gapCount(Side.TOP) + gapCount(Side.BOTTOM) + gapCount(Side.LEFT) + gapCount(Side.RIGHT);
	}
	
	/** Returns {@code true} iff this {@link RoomLayout} has at least one {@link DoorGap} on the given {@link Side}.*/
	default boolean hasGap(Side side) {
		return !gaps(side).isEmpty();
	}
	
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
	default boolean intervisible(double x1, double y1, double x2, double y2) {
		return !lineIntersects(x1, y1, x2, y2);
	}
	
	boolean lineIntersects(double x1, double y1, double x2, double y2);
	
}
