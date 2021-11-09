package enemies;

import base.DelayUpdatable;
import fxutils.Backgrounds;
import javafx.geometry.Point2D;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class BasicEnemy extends AbstractSimpleMovementEnemy implements DelayUpdatable {

	public static final double SIZE = 20;
	
	private static final Background BACKGROUND = Backgrounds.of(Color.RED);
	private static final double DEFAULT_HEALTH = 3;
	private static final double CORNER_DIST = SIZE * Math.sqrt(2) / 2 + 1;
	/** in pixels per second. */
	private static final double VELOCITY = 50;
	private final PathManager pathManager;
	
	public BasicEnemy() {
		super(DEFAULT_HEALTH);
		setMinWidth(SIZE);
		setMaxWidth(SIZE);
		setMinHeight(SIZE);
		setMaxHeight(SIZE);
		setBackground(BACKGROUND);
		pathManager = new ContactPathManager(this, CORNER_DIST);
	}
	
	@Override
	public void update(long nsSinceLastFrame) {
		pathManager.update(nsSinceLastFrame);
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
