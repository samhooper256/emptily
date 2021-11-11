package enemies;

import base.DelayUpdatable;
import base.game.content.Intersections;
import base.game.effects.SimpleBurst;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public final class Cannonball extends AbstractSimpleMovementEnemy implements DelayUpdatable {

	private static final double RADIUS = 6, VELOCITY = 100, DEFAULT_HEALTH = 1;
	
	public Cannonball(Point2D from, Point2D to) {
		this(from.getX(), from.getY(), to.getX(), to.getY());
	}
	
	/** from and to coordinates are centers.*/
	public Cannonball(double fromX, double fromY, double toX, double toY) {
		super(DEFAULT_HEALTH);
		setMinWidth(RADIUS * 2);
		setMaxWidth(RADIUS * 2);
		setMinHeight(RADIUS * 2);
		setMaxHeight(RADIUS * 2);
		double angrad = Math.atan2(toY - fromY, toX - fromX);
		setxvel(VELOCITY * Math.cos(angrad));
		setyvel(VELOCITY * Math.sin(angrad));
		setLayoutX(fromX - RADIUS);
		setLayoutY(fromY - RADIUS);
		Circle circle = new Circle(RADIUS, Color.RED);
		getChildren().add(circle);
	}
	
	@Override
	public void update(long nsSinceLastFrame) {
		Movement.applyVelocity(this, nsSinceLastFrame);
		if(Intersections.intersectsAnyPlatformsOrDoors(this)) {
			delete();
		}
	}

	@Override
	public Point2D center() {
		return new Point2D(centerX(), centerY());
	}
	
	@Override
	public double centerX() {
		return x() + RADIUS;
	}

	@Override
	public double centerY() {
		return y() + RADIUS;
	}
	
	@Override
	public double maxVelocity() {
		return VELOCITY;
	}

	@Override
	public void die() {
		delete();
		addBurstCentered(new SimpleBurst(Color.RED, RADIUS));
	}
	
	@Override
	public boolean isRequired() {
		return false;
	}
	
}
