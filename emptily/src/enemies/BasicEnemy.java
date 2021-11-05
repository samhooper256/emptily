package enemies;

import base.DelayUpdatable;
import fxutils.Backgrounds;
import javafx.geometry.Point2D;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class BasicEnemy extends AbstractEnemy implements HittableEnemy, DelayUpdatable {

	public static final double SIZE = 20;
	
	private static final Background BACKGROUND = Backgrounds.of(Color.RED);
	private static final double DEFAULT_HEALTH = 3;
	private static final double CORNER_DIST = SIZE * Math.sqrt(2) / 2 + 1;
	/** in pixels per second. */
	private static final double VELOCITY = 50;
	private final PathManager pathManager;
	
	private double xvel, yvel;
	
	public BasicEnemy() {
		super(DEFAULT_HEALTH);
		setMinWidth(SIZE);
		setMaxWidth(SIZE);
		setMinHeight(SIZE);
		setMaxHeight(SIZE);
		setBackground(BACKGROUND);
		pathManager = new PathManager(this, CORNER_DIST);
	}
	
	@Override
	public void update(long nsSinceLastFrame) {
		pathManager.update(nsSinceLastFrame);
	}
	
	@Override
	public Point2D center() {
		return new Point2D(x() + SIZE / 2, y() + SIZE / 2);
	}
	
	@Override
	public double xvel() {
		return xvel;
	}
	
	@Override
	public double yvel() {
		return yvel;
	}
	
	@Override
	public void setxvel(double xvel) {
		this.xvel = xvel;
	}
	
	@Override
	public void setyvel(double yvel) {
		this.yvel = yvel;
	}
	
	@Override
	public double maxVelocity() {
		return VELOCITY;
	}
	
}
