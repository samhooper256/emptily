package enemies;

import base.*;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Cannon extends AbstractSimpleMovementEnemy implements DelayUpdatable {

	public static final double SIZE = 20;
	
	private static final double
			DEFAULT_HEALTH = 5,
			CORNER_DIST = SIZE * Math.sqrt(2) / 2 + 1,
			VELOCITY = 30, /** in pixels per second. */
			MIN_PLAYER_DIST = 200,
			SHOOT_COOLDOWN = 2_000_000_000; /* in nanoseconds */
	
	private final PathManager pathManager;
	private final Circle circle;

	private long nsSinceLastCannonball;
	
	public Cannon() {
		super(DEFAULT_HEALTH);
		setMinWidth(SIZE);
		setMaxWidth(SIZE);
		setMinHeight(SIZE);
		setMaxHeight(SIZE);
		circle = new Circle(10, Color.TRANSPARENT);
		circle.setStroke(Color.RED);
		circle.setStrokeWidth(4);
		getChildren().add(circle);
		pathManager = new DistancePathManager(this, CORNER_DIST, MIN_PLAYER_DIST);
		nsSinceLastCannonball = 0;
	}
	
	@Override
	public void update(long nsSinceLastFrame) {
		pathManager.update(nsSinceLastFrame);
		nsSinceLastCannonball += nsSinceLastFrame;
		if(pathManager.couldSeePlayerAfterLastUpdate() && nsSinceLastCannonball >= SHOOT_COOLDOWN) {
			blast();
			nsSinceLastCannonball = 0;
		}
	}
	
	private void blast() {
		Main.content().requestAdd(new Cannonball(center(), Main.content().player().center()));
	}
	
	
	@Override
	public Point2D center() {
		return new Point2D(centerX(), centerY());
	}

	@Override
	public double centerX() {
		return x() + SIZE / 2;
	}
	
	@Override
	public double centerY() {
		return y() + SIZE / 2;
	}

	@Override
	public double maxVelocity() {
		return VELOCITY;
	}
	
}
