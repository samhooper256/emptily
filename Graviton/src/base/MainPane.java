package base;

import java.util.*;

import fxutils.Nodes;
import javafx.collections.ObservableList;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;

public class MainPane extends StackPane implements DelayUpdatable {

	private final Pane content;
	private final Player player;
	
	private final Collection<Platform> platforms;
	private final Set<Node> removeRequests;
	
	MainPane() {
		
		content = new Pane();
		player = new Player();
		
		Platform platform2 = new Platform(120, 120);
		Platform top = new Platform(400, 20);
		Platform left = new Platform(20, 400);
		Platform right = new Platform(20, 400);
		Platform bottom = new Platform(400, 20);
		
		Nodes.setLayout(top, 0, 0);
		Nodes.setLayout(left, 0, 0);
		Nodes.setLayout(right, 400, 0);
		Nodes.setLayout(bottom, 0, 300);
		
		Nodes.setLayout(platform2, 200, 100);
		
		platforms = new ArrayList<>();
		Collections.addAll(platforms, platform2, top, right, bottom, left);
		
		removeRequests = new HashSet<>();
		
		content.getChildren().addAll(player);
		for(Platform p : platforms)
			content.getChildren().add(p);
		
		BasicEnemy e1 = new BasicEnemy(), e2 = new BasicEnemy(), e3 = new BasicEnemy();
		e1.setLocation(70, 70);
		e2.setLocation(90, 90);
		e3.setLocation(70, 150);
		content.getChildren().addAll(e1, e2, e3);
		
		player.setLayoutX(50);
		player.setLayoutY(300);
		
		getChildren().addAll(content);
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
	
}
