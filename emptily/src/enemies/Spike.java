package enemies;

import base.DelayUpdatable;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class Spike extends AbstractEnemy implements DelayUpdatable {

	public static final double DEFAULT_SIZE = 20;
	
	private static final double HEALTH = Double.POSITIVE_INFINITY;
	/** in pixels per second. */
	
	public Spike() {
		this(DEFAULT_SIZE);
	}
	
	private Spike(double size) {
		super(HEALTH);
		setMinWidth(size);
		setMaxWidth(size);
		setMinHeight(size);
		setMaxHeight(size);
		Polygon polygon = new Polygon(0, 0, size*.5, size*.25, size, 0, size*.75, size*.5, size, size,
				size*.5, size*.75, 0, size, size*.25, size*.5);
		polygon.setFill(Color.RED);
		getChildren().add(polygon);
	}
	
	
	@Override
	public void update(long nsSinceLastFrame) {
		
	}
	
	@Override
	public Point2D center() {
		return new Point2D(centerX(), centerY());
	}

	@Override
	public double centerX() {
		return x() + DEFAULT_SIZE / 2;
	}
	
	@Override
	public double centerY() {
		return y() + DEFAULT_SIZE / 2;
	}

	@Override
	public double maxVelocity() {
		return 0;
	}

	@Override
	public double xvel() {
		return 0;
	}

	@Override
	public double yvel() {
		return 0;
	}

	@Override
	public void setxvel(double xvel) {
		throw new UnsupportedOperationException("Spikes do not move");
	}

	@Override
	public void setyvel(double yvel) {
		throw new UnsupportedOperationException("Spikes do not move");
	}

	@Override
	public boolean isRequired() {
		return false;
	}
	
	@Override
	public void die() {
		delete(); //TODO death/disappear animation?
	}
}
