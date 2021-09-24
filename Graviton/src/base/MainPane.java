package base;

import java.util.*;

import javafx.collections.ObservableList;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.shape.*;
import rooms.*;
import utils.Colls;

public class MainPane extends StackPane implements DelayUpdatable {

	private final Pane content;
	private final Player player;
	private final Collection<Platform> platforms;
	private final Set<Enemy> enemies;
	private final Set<Node> removeRequests;
	
	private RoomInfo currentInfo; //TODO better - change it when you change rooms.
	private FloorPlan floorPlan;
	
	MainPane() {
		
		content = new Pane();
		player = new Player();
		
		platforms = new ArrayList<>();
		enemies = new HashSet<>();
		
		floorPlan = new FloorPlanBuilder(RoomLayout.all(), 23, 80, 20).build();
		currentInfo = floorPlan.startingRoom();
		
		for(RoomInfo ri : floorPlan.rooms())
			displayRoom(ri);
		
		
		removeRequests = new HashSet<>();
		
		content.getChildren().addAll(player);
		
		BasicEnemy e1 = new BasicEnemy(), e2 = new BasicEnemy(), e3 = new BasicEnemy();
		e1.setLocation(20, 70);
		e2.setLocation(120, 70);
		e3.setLocation(220, 70);
		addEnemies(e1, e2, e3);
		
		player.setLayoutX(50);
		player.setLayoutY(300);
		
		getChildren().addAll(content);
	}
	
	public void displayRoom(RoomInfo info) {
		displayRoom(info.layout(), info.tlx(), info.tly());
	}
	
	public void displayRoom(RoomLayout layout, double tlx, double tly) {
		double t = layout.borderThickness(), iw = layout.interiorWidth(), ih = layout.interiorHeight();
		displayHorizontalSide(iw, t, tlx, tly, layout.topGaps());
		displayHorizontalSide(iw, t, tlx, tly + t + ih, layout.bottomGaps());
		displayVerticalSide(ih, t, tlx, tly, layout.leftGaps());
		displayVerticalSide(ih, t, tlx + t + iw, tly, layout.rightGaps());
		for(RectangleLayout r : layout.rectsUnmodifiable())
			addPlatforms(new Platform(tlx + r.x(), tly + r.y(), r.width(), r.height()));
	}
	
	private void displayHorizontalSide(double iw, double t, double x0, double y,
			HorizontalGapCollection gcoll) {
		double x = x0;
		//assuming 1+ gaps:
		List<HorizontalGap> hgaps = gcoll.sorted(WallDirection.LEFT_TO_RIGHT);
		for(int i = 0; i < hgaps.size(); i++) {
			HorizontalGap gap = hgaps.get(i);
			Platform p = Platform.fromCorners(x, y, x0 + t + gap.leftDist(), y + t);
			addPlatform(p);
			x = x0 + t + iw - gap.rightDist();
		}
		//draw last section, after the last gap:
		addPlatform(Platform.fromCorners(x, y, x0 + iw + 2 * t, y + t));
	}
	
	private void displayVerticalSide(double ih, double t, double x, double y0, VerticalGapCollection vcoll) {
		double y = y0;
		List<VerticalGap> vgaps = vcoll.sorted(WallDirection.TOP_TO_BOTTOM);
		for(int i = 0; i < vgaps.size(); i++) {
			VerticalGap gap = vgaps.get(i);
			Platform p = Platform.fromCorners(x, y, x + t, y0 + t + gap.topDist());
			addPlatform(p);
			y = y0 + t + ih - gap.bottomDist();
		}
		addPlatform(Platform.fromCorners(x, y, x + t, y0 + 2 * t + ih));
	}
	
	
	private void addEnemies(Enemy... enemies) {
		for(Enemy e : enemies)
			addEnemy(e);
	}
	
	private void addEnemy(Enemy e) {
		content.getChildren().addAll(e.asNode());
		enemies.add(e);
	}
	
	private void removeAllPlatforms() {
		content.getChildren().removeAll(platforms);
	}
	
	private void addPlatform(Platform platform) {
		content.getChildren().add(platform);
		this.platforms.add(platform);
	}
	
	private void addPlatforms(Platform... platforms) {
		content.getChildren().addAll(platforms);
		Collections.addAll(this.platforms, platforms);
	}	
	
	public void keyPressed(KeyCode code) {
		if(KeyInput.isModeCode(code))
			player.setMode(KeyInput.modeFor(code));
	}
	
	public void leftClicked(Point2D point) {
		Point2D playerCenter = player.center();
		double xdist = point.getX() - playerCenter.getX();
		double ydist = point.getY() - playerCenter.getY();
		double angdeg = Math.toDegrees(Math.atan2(ydist, xdist));
		content.getChildren().add(new LineBullet(playerCenter.getX(), playerCenter.getY(), angdeg));
	}
	
	@Override
	public void update(long nsSinceLastFrame) {
		player.update(nsSinceLastFrame);
		for(Node n : content.getChildren())
			if(n != player && n instanceof DelayUpdatable du)
				du.update(nsSinceLastFrame);
		
		ObservableList<Node> contentChildren = content().getChildren();
		for(Node n : removeRequests) {
			boolean result = contentChildren.remove(n);
			if(result && n instanceof Enemy)
				enemies.remove(n);
		}
		removeRequests.clear();
		
	}

	public Player player() {
		return player;
	}
	
	public Collection<Platform> platforms() {
		return platforms;
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
	
	public Enemy getEnemyIntersectingPlayer() {
		for(Enemy e : enemies)
			if(intersectsPlayer(e))
				return e;
		return null;
	}
	
	public boolean intersectsPlayer(Enemy enemy) {
		Bounds enemyBounds = enemy.getBoundsInParent();
		return enemyBounds.intersects(player.getBoundsInParent());
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
	
	public RoomInfo roomInfo() {
		return currentInfo;
	}
	
}
