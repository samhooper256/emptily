package base;

import java.util.*;

import fxutils.Nodes;
import javafx.collections.ObservableList;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import rooms.*;
import utils.Colls;

public class MainPane extends StackPane implements DelayUpdatable {

	private record Room(RoomLayout layout, Point2D tl) {}
	
	private final Pane content;
	private final Player player;
	
	private final Collection<Platform> platforms;
	private final Set<Node> removeRequests;
	
	MainPane() {
		
		content = new Pane();
		player = new Player();
		
		platforms = new ArrayList<>();
		
		displayRoom(Colls.any(RoomLayout.all()), 0, 0);
		
		removeRequests = new HashSet<>();
		
		content.getChildren().addAll(player);
		
		BasicEnemy e1 = new BasicEnemy(), e2 = new BasicEnemy(), e3 = new BasicEnemy();
		e1.setLocation(70, 70);
		e2.setLocation(90, 90);
		e3.setLocation(70, 150);
		content.getChildren().addAll(e1, e2, e3);
		
		player.setLayoutX(50);
		player.setLayoutY(300);
		
		getChildren().addAll(content);
	}

	private void generateRooms(int count) {
		Set<Room> layouts = new HashSet<>();
		Room startingRoom = new Room(RoomLayout.random(), Point2D.ZERO);
		layouts.add(startingRoom);
		count--;
		//generate rooms with BFS
	}
	
	
	public void displayRoom(RoomLayout layout, double tlx, double tly) {
		double t = layout.borderThickness(), w = layout.width(), h = layout.height();
		Platform top = new Platform(tlx, tly, w, t);
		Platform right = new Platform(tlx + w - t, tly, t, h);
		Platform bottom = new Platform(tlx, tly + h - t, w, t);
		Platform left = new Platform(tlx, tly, t, h);
		addPlatforms(top, right, bottom, left);
		for(RectangleLayout r : layout.rectsUnmodifiable())
			addPlatforms(new Platform(tlx + r.x(), tly + r.y(), r.width(), r.height()));
		Set<Point2D> points = VisibilityGraph.pointsFor(layout, 6);
		for(Point2D p : points) {
			Circle c = new Circle(2, Color.ORANGE);
			Nodes.setLayout(c, p.getX(), p.getY());
			content.getChildren().add(c);
		}
		Set<Line> lines = VisibilityGraph.lineSet(points, this);
		System.out.printf("lines=%s%n", lines);
		for(Line l : lines)
			content.getChildren().add(l);
	}
	
	private void removeAllPlatforms() {
		content.getChildren().removeAll(platforms);
	}
	
	private void addPlatforms(Platform... platforms) {
		content.getChildren().addAll(platforms);
		Collections.addAll(this.platforms, platforms);
	}	
	
	public void keyPressed(KeyCode code) {
		if(KeyInput.isModeCode(code))
			player.setMode(KeyInput.modeFor(code));
		if(code == KeyCode.SPACE) {
			update(1_000_000_000);
		}
	}
	
	public void leftClicked(Point2D point) {
		Point2D playerCenter = player.center();
//		PerspectiveCamera c = scene().camera();
//		double cx = c.getTranslateX(), cy = c.getTranslateY();
//		Line l = new Line(point.getX(), point.getY(), playerCenter.getX(), playerCenter.getY());
		double xdist = point.getX() - playerCenter.getX();
		double ydist = point.getY() - playerCenter.getY();
		double angle = Math.toDegrees(Math.atan2(ydist, xdist));
		content.getChildren().add(new LineBullet(playerCenter.getX(), playerCenter.getY(), angle));
	}
	
	@Override
	public void update(long nsSinceLastFrame) {
		player.update(nsSinceLastFrame);
		for(Node n : content.getChildren())
			if(n != player && n instanceof DelayUpdatable du)
				du.update(nsSinceLastFrame);
		
		ObservableList<Node> contentChildren = content().getChildren();
		for(Node n : removeRequests)
			contentChildren.remove(n);
		removeRequests.clear();
		
	}

	public Player player() {
		return player;
	}
	
	public Collection<Platform> platforms() {
		return platforms;
	}
	
	public MainScene scene() {
		return (MainScene) getScene();
	}
	
	public Pane content() {
		return content;
	}
	
	/** Assumes {@code node} is an immediate child of {@link #content()}. */
	public boolean intersectsAnyPlatforms(Node node) {
		Bounds bounds = node.getBoundsInParent();
		for(Platform p : platforms)
			if(bounds.intersects(p.getBoundsInParent()))
				return true;
		return false;
	}
	
	/** Assumes {@code node} is an immediate child of {@link #content()}.
	 * Returns {@code null} if no enemy intersected. */
	public HittableEnemy intersectsHittableEnemy(Node node) {
		Bounds bounds = node.getBoundsInParent();
		for(Node n : content().getChildren())
			if(n instanceof HittableEnemy e)
				if(bounds.intersects(e.getBoundsInParent()))
					return e;
		return null;
	}
	
	/** Requests to removes the given {@link Node} from the {@link #content()}'s children at the
	 * end of this {@link #update(long) update} pulse.*/
	public boolean requestRemove(Node node) {
		return removeRequests.add(node);
	}

	/** Assumes that the given coordinates are IN THE COORDINATE SPACE OF THIS MAINPANE.*/
	public boolean intervisible(Point2D a, Point2D b) {
		Line l = new Line(a.getX(), a.getY(), b.getX(), b.getY());
		l.setVisible(false);
		content.getChildren().add(l);
		for(Platform p : platforms()) {
			Bounds bip = l.getBoundsInParent();
			if(bip.intersects(p.getBoundsInParent())) {
				content.getChildren().remove(l);
				return false;
			}
		}
		content.getChildren().remove(l);
		return true;
	}
	
}
