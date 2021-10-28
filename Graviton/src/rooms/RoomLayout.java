package rooms;

import java.util.*;
import java.util.function.Predicate;

import javafx.geometry.*;
import rooms.gaps.DoorGap;
import rooms.gaps.DoorGapCollection;
import rooms.gaps.HorizontalGapCollection;
import rooms.gaps.VerticalGapCollection;
import rooms.spawns.EnemySpawn;

public interface RoomLayout {
	
	double DEFAULT_BORDER_THICKNESS = 10;
	
	static RoomLayout copyOf(RoomLayout rl) {
		if(rl == null)
			return null;
		return rl.copy();
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
	
	/** Returns all of the rectangle <em>inside</em> this room. The returned {@link Collection} does <em>not</em>
	 * contain the walls of this {@link RoomLayout}.*/
	Collection<RectangleLayout> interiorRectsUnmodifiable();
	
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
	
	/** The x-coordinate of the <b>CENTER</b> of the player. */
	double playerSpawnX();
	
	/** The y-coordinate of the <b>CENTER</b> of the player. */
	double playerSpawnY();
	
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
	 * room's {@link #interiorRectsUnmodifiable() rectangles} contain the given point.*/
	default boolean containsInRect(Point2D point) {
		for(RectangleLayout r : interiorRectsUnmodifiable())
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
	
	/** Returns {@code true} iff the given line intersects this {@link RoomLayout}. Assumes the given coordinates are in
	 * the local coordinate space of this room - that is, (0, 0) is the top-left corner of this room. This method
	 * <em>does</em> include the walls of this room, but it ignores {@linkplain #gaps() gaps}. */
	boolean lineIntersects(double x1, double y1, double x2, double y2);
	
	/** removes all {@link DoorGap DoorGaps} from this {@link RoomLayout} that satisfy the given condition. */
	void removeGapsIf(Predicate<? super DoorGap> predicate);
	
	RoomLayout copy();
	
	Collection<EnemySpawn> spawns();
	
}
