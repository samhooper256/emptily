package rooms;

import java.util.*;

import fxutils.Lines;
import javafx.geometry.*;
import javafx.scene.shape.Line;

public class VisibilityGraph {
	
	private final RoomLayout layout;
	private final Map<Point2D, Set<Point2D>> adj;
	
	private VisibilityGraph(RoomLayout layout, Map<Point2D, Set<Point2D>> adj) {
		this.layout = layout;
		this.adj = adj;
	}
	
	/** Returns a {@link PointPath} connecting {@code start} and {@code dest} through this {@link VisibilityGraph}.
	 * {@code start} and {@code dest} do not need to be in this graph. The given points must be in the same coordinate
	 * space as this graph - that is, where (0, 0) is the top-left of the {@link RoomLayout} this graph was created
	 * from.*/
	public PointPath path(Point2D source, Point2D dest) {
		return new PathFinder(source, dest).find();
	}
	
	private class PathFinder {

		final Point2D source, dest;
		final Set<Point2D> Q = new HashSet<>(), sourceNeighbors;
		final Map<Point2D, Double> dist = new HashMap<>();
		final Map<Point2D, Point2D> prev = new HashMap<>();
		final boolean sourceInGraph;
		
		PathFinder(Point2D source, Point2D dest) {
			this.source = source;
			this.dest = dest;
			sourceInGraph = contains(source);
			if(sourceInGraph) {
				sourceNeighbors = adj.get(source);
			}
			else {
				sourceNeighbors = new HashSet<>();
				for(Point2D p : adj.keySet())
					if(layout.intervisible(p, source))
						sourceNeighbors.add(p);
			}
		}
		
		PointPath find() {
			if(!contains(source))
				initVertex(source);
			for(Point2D v : adj.keySet())
				initVertex(v);
			dist.put(source, 0.0);
			while(!Q.isEmpty()) {
				Point2D u = minDistVertex();
				Q.remove(u);
				if(u == source) {
					for(Point2D v : sourceNeighbors)
						innerloop(u, v);
				}
				else {
					for(Point2D v : adj.get(u))
						innerloop(u, v);
					if(!sourceInGraph && isNeighborOfSource(u))
						innerloop(u, source);
				}
			}
			
			if(!contains(dest)) {
				double min = Double.MAX_VALUE;
				Point2D bestPrev = null;
				for(Map.Entry<Point2D, Double> e : dist.entrySet()) {
					Point2D p = e.getKey();
					double len = e.getValue();
					if(layout.intervisible(p, dest) && len < min) {
						min = len;
						bestPrev = p;
					}
				}
				prev.put(dest, bestPrev);
			}
			PointPath path = PointPath.empty();
			Point2D p = dest;
			do {
				path.add(p);
				p = prev.get(p);
			} while(p != null);
			path.reverse();
			return path;
		}

		private void innerloop(Point2D u, Point2D v) {
			if(Q.contains(v)) {
				double alt = dist.get(u) + u.distance(v);
				if(alt < dist.get(v)) {
					dist.put(v, alt);
					prev.put(v, u);
				}
			}
		}

		private void initVertex(Point2D v) {
			dist.put(v, Double.POSITIVE_INFINITY);
			prev.put(v, null);
			Q.add(v);
		}
		
		private Point2D minDistVertex() {
			double min = Double.POSITIVE_INFINITY;
			Point2D minVertex = Q.iterator().next();
			for(Point2D p : Q) {
				double d = dist.get(p);
				if(d < min) {
					min = d;
					minVertex = p;
				}
			}
			return minVertex;
		}

		private boolean isNeighborOfSource(Point2D v) {
			return sourceNeighbors.contains(v);
		}
		
	}
	
	public boolean contains(Point2D point) {
		return adj.containsKey(point);
	}
	
	public static VisibilityGraph forRoom(RoomLayout layout, double cornerDist) {
		Map<Point2D, Set<Point2D>> adj = new HashMap<>();
		Set<Point2D> points = pointsFor(layout, cornerDist);
		for(Point2D point : points)
			adj.put(point, new HashSet<>());
		for(Point2D a : points)
			for(Point2D b : points)
				if(a != b && layout.intervisible(a, b))
					adj.get(a).add(b);
		return new VisibilityGraph(layout, adj);
	}
	
	/** The returned points are in the coordinate space of the given {@link RoomLayout}.*/
	public static Set<Point2D> pointsFor(RoomLayout room, double cornerDist) {
		Set<Point2D> points = new HashSet<>();
		for(RectangleLayout r : room.interiorRectsUnmodifiable()) {
			points.add(new Point2D(r.ulx() - cornerDist, r.uly() - cornerDist));
			points.add(new Point2D(r.urx() + cornerDist, r.ury() - cornerDist));
			points.add(new Point2D(r.llx() - cornerDist, r.lly() + cornerDist));
			points.add(new Point2D(r.lrx() + cornerDist, r.lry() + cornerDist));
		}
		removePointsThatIntersectRectangles(points, room);
		removePointsThatAreOutsideRoom(points, room);
		consolidatePoints(points, cornerDist);
		return points;
	}
	
	private static void removePointsThatIntersectRectangles(Set<Point2D> points, RoomLayout room) {
		points.removeIf(p -> room.containsInRect(p));
	}
	
	private static void removePointsThatAreOutsideRoom(Set<Point2D> points, RoomLayout room) {
//		System.out.printf("FILTERING points=%s%n", points);
		points.removeIf(p -> {
			boolean shouldRemove = !room.containsWithinInterior(p);
//			if(shouldRemove)
//				System.out.printf("\tFiltered %s%n", p);
			return shouldRemove;
		});
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
//		System.out.printf("[enter] lineSet(points=%s)%n", points);
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
	
	public Map<Point2D, Set<Point2D>> adj() {
		return adj;
	}
	
}