package rooms;

import java.util.*;

import fxutils.Lines;
import javafx.geometry.*;
import javafx.scene.shape.Line;

public class VisibilityGraph {
	
	public Map<Point2D, Set<Point2D>> adj;
	
	private VisibilityGraph() {
		
	}
	
	/** The returned points are in the coordinate space of the given {@link RoomLayout}.*/
	public static Set<Point2D> pointsFor(RoomLayout room, double cornerDist) {
		Set<Point2D> points = new HashSet<>();
		for(RectangleLayout r : room.rectsUnmodifiable()) {
			points.add(new Point2D(r.ulx() - cornerDist, r.uly() - cornerDist));
			points.add(new Point2D(r.urx() + cornerDist, r.ury() - cornerDist));
			points.add(new Point2D(r.llx() - cornerDist, r.lly() + cornerDist));
			points.add(new Point2D(r.lrx() + cornerDist, r.lry() + cornerDist));
		}
		consolidatePoints(points, cornerDist);
		return points;
	}
	
	private static void consolidatePoints(Set<Point2D> points, double minDistance) {
		Iterator<Point2D> itr = points.iterator();
		while(itr.hasNext()) {
			Point2D point = itr.next();
			boolean remove = false;
			for(Point2D p : points) {
				if(point != p && point.distance(p) < minDistance) {
					remove = true;
					break;
				}
			}
			if(remove)
				itr.remove();
		}
	}
	
	/** Assumes the given points are in the coordinate space of the {@link RoomLayout}.
	 * For example, a point of (0, 0) would represent the top left of the given room.
	 * The coordinates of the endpoints of all lines in the returned set are shifted by
	 * {@code shiftX} and {@code shiftY}.*/
	public static Set<Line> lineSet(Set<Point2D> points, RoomLayout layout, double shiftX, double shiftY) {
		System.out.printf("[enter] lineSet(points=%s)%n", points);
		Set<Line> lines = new HashSet<>();
		for(Point2D a : points)
			for(Point2D b : points)
				if(a != b && layout.intervisible(a, b))
					lines.add(Lines.between(a, b, shiftX, shiftY));
		return lines;
	}
	
	/** Assumes the given points are in the coordinate space of the {@link RoomLayout}.
	 * For example, a point of (0, 0) would represent the top left of the given room.*/
	public static Set<Line> lineSet(Set<Point2D> points, RoomLayout layout) {
		return lineSet(points, layout, 0, 0);
	}
	
}
