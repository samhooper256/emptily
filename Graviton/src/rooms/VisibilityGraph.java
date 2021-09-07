package rooms;

import java.util.*;

import base.*;
import fxutils.Lines;
import javafx.geometry.*;
import javafx.scene.shape.Line;

public class VisibilityGraph {
	
	public Map<Point2D, Set<Point2D>> adj;
	
	private VisibilityGraph() {
		
	}
	
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
	
	public static Set<Line> lineSet(Set<Point2D> points, MainPane mp) {
		System.out.printf("[enter] lineSet(points=%s)%n", points);
		Set<Line> lines = new HashSet<>();
		for(Point2D a : points)
			for(Point2D b : points)
				if(a != b && mp.intervisible(a, b))
					lines.add(Lines.between(a, b));
		return lines;
	}
	
}
