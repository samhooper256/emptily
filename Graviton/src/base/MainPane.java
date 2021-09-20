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
	
	MainPane() {
		
		content = new Pane();
		player = new Player();
		
		platforms = new ArrayList<>();
		enemies = new HashSet<>();
		
		RoomLayout layout = Colls.any(RoomLayout.all());
		double roomx = 0, roomy = 0;
		displayRoom(layout, roomx, roomy);
		currentInfo = RoomInfo.re(layout, roomx, roomy);
		
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
	
	public void displayRoom(RoomLayout layout, double tlx, double tly) {
		double t = layout.borderThickness(), w = layout.width(), h = layout.height();
		Platform top = new Platform(tlx, tly, w, t);
		Platform right = new Platform(tlx + w - t, tly, t, h);
		Platform bottom = new Platform(tlx, tly + h - t, w, t);
		Platform left = new Platform(tlx, tly, t, h);
		addPlatforms(top, right, bottom, left);
		for(RectangleLayout r : layout.rectsUnmodifiable())
			addPlatforms(new Platform(tlx + r.x(), tly + r.y(), r.width(), r.height()));
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
