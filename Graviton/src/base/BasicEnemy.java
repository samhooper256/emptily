package base;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class BasicEnemy extends Rectangle implements HittableEnemy, DelayUpdatable {

	private static final double DEFAULT_HEALTH = 3;
	private static final double SIZE = 20;
	/** in pixels per second. */
	private static final double VELOCITY = 50;
	
	private double xvel, yvel, health;
	
	public BasicEnemy() {
		super(SIZE, SIZE);
		setFill(Color.RED);
		health = DEFAULT_HEALTH;
	}

	@Override
	public void update(long nsSinceLastFrame) {
		double sec = nsSinceLastFrame / 1e9;
		Point2D enemy = center();
		Point2D player = Main.pane().player().center();
		double xdist = player.getX() - enemy.getX();
		double ydist = player.getY() - enemy.getY();
		double angle = Math.atan2(ydist, xdist);
		xvel = VELOCITY * Math.cos(angle);
		yvel = VELOCITY * Math.sin(angle);
		double oldX = x(), oldY = y();
		boolean canX = true, canY = true;
		setLayoutX(oldX + xvel * sec);
		if(Main.pane().intersectsAnyPlatforms(this))
			canX = false;
		setLayoutX(oldX);
		setLayoutY(y() + yvel * sec);
		if(Main.pane().intersectsAnyPlatforms(this))
			canY = false;
		setLayoutY(oldY);
		if(canX && !canY) {
			xvel = xvel > 0 ? VELOCITY : -VELOCITY;
			yvel = 0;
		}
		else if(!canX && canY) {
			xvel = 0;
			yvel = yvel > 0 ? VELOCITY : -VELOCITY;
		}
		setLayoutX(x() + xvel * sec);
		setLayoutY(y() + yvel * sec);
	}
	
	public Point2D center() {
		return new Point2D(x() + SIZE / 2, y() + SIZE / 2);
	}

	@Override
	public double health() {
		return health;
	}

	@Override
	public void takeHit(double damage) {
		health -= damage;
		if(health <= 0)
			Main.pane().requestRemove(this);
	}
	
}
