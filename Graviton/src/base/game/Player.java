package base.game;

import java.util.function.Consumer;

import base.DelayUpdatable;
import base.Main;
import base.game.content.Intersections;
import enemies.Enemy;
import fxutils.Backgrounds;
import javafx.geometry.*;
import javafx.scene.Node;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class Player extends StackPane implements DelayUpdatable {
	
	private static final GravityMode DEFAULT_MODE = GravityMode.DOWN;
	private static final double DEFAULT_WIDTH = 20, DEFAULT_HEIGHT = DEFAULT_WIDTH;
	private static final double COLLIDE_FLUSH = 0.5;
	/** in nanos*/
	private static final long INVINCIBILITY_TIME = 1_000_000_000L; 
	
	private final StackPane color;
	
	private GravityMode mode;
	private double xvel, yvel, x, y;
	private long invincibilityTimer = 0;
	
	public Player() {
		yvel = 0;
		xvel = 0;
		mode = DEFAULT_MODE;
		color = new StackPane();
		color.setOpaqueInsets(new Insets(1));
		color.setBackground(Backgrounds.of(Color.BLUE));
		color.setMinWidth(DEFAULT_WIDTH);
		color.setMinHeight(DEFAULT_HEIGHT);
		color.setMaxWidth(DEFAULT_WIDTH);
		color.setMaxHeight(DEFAULT_HEIGHT);
		getChildren().addAll(color);
		
	}
	
	@Override
	public void update(long nsSinceLastFrame) {
		double sec = nsSinceLastFrame / 1e9;
		double oldX = x, oldY = y;
		double xaccel = mode.xAccel(), yaccel = mode.yAccel();
		if(vincible()) {
			Enemy enemy = Intersections.getEnemyIntersectingPlayer();
			if(enemy != null)
				takeHit();
		}
		else {
			invincibilityTimer = Math.max(invincibilityTimer - nsSinceLastFrame, 0);
			setVisible(invincibilityTimer == 0 || (invincibilityTimer & 1L << 28) == 0);
		}
		
		x += xvel;
		y += yvel;
		
		boolean backtrackedX = false, backtrackedY = false;
		//X:
		setLayoutX(x);
		Node p = Intersections.getPlatformOrDoorIntersecting(this);
		if(p != null) {
			backtrackedX = true;
			x = findX(oldX, x, p);
			setLayoutX(x);
			xvel = 0;
		}
		if(!backtrackedX)
			xvel += xaccel * sec;
		
		//Y:
		setLayoutY(y);
		p = Intersections.getPlatformOrDoorIntersecting(this);
		if(p != null) {
			backtrackedY = true;
			y = findY(oldY, y, p);
			setLayoutY(y);
			yvel = 0;
		}
		if(!backtrackedY)
			yvel += yaccel * sec;
		
	}
	
	
	private boolean vincible() {
		return invincibilityTimer == 0;
	}
	
	private boolean intersects(Node p) {
		return p.getBoundsInParent().intersects(getBoundsInParent());
	}
	
	public void takeHit() {
		System.out.printf("[enter] takeHit()%n");
		Main.healthBar().hit();
		if(hp() == 0) {
			Main.outerScene().die();
		}
		else {
			invincibilityTimer = INVINCIBILITY_TIME;
		}
	}
	
	public void setMode(GravityMode mode) {
		this.mode = mode;
	}
	
	public double x() {
		return x;
	}
	
	public double y() {
		return y;
	}
	
	public void setX(double x) {
		this.x = x;
		setLayoutX(x);
	}
	
	public void setY(double y) {
		this.y = y;
		setLayoutY(y);
	}
	
	/** Moves this {@link Player} to the given coordinates, sets its velocity to zero, and sets its gravity
	 * direction to down.*/
	public void resetTo(double x, double y) {
		xvel = 0;
		yvel = 0;
		setX(x);
		setY(y);
		mode = GravityMode.DOWN;
	}
	
	public double width() {
		return getWidth();
	}
	
	public double height() {
		return getHeight();
	}
	
	public Point2D center() {
		return new Point2D(x() + width() / 2, y() + height() / 2);
	}
	
	public int hp() {
		return Main.healthBar().hp();
	}
	
	private double findX(double safe, double collide, Node p) {
		return findCoord(safe, collide, p, this::setLayoutX);
	}
	
	private double findY(double safe, double collide, Node p) {
		return findCoord(safe, collide, p, this::setLayoutY);
	}

	/** assumes current position is collideY*/
	private double findCoord(double safe, double collide, Node p, Consumer<Double> setter) {
		while(Math.abs(safe - collide) > COLLIDE_FLUSH) {
			double mid = (safe + collide) / 2.0;
			setter.accept(mid);
			if(intersects(p)) {
				collide = mid;
			}
			else {
				safe = mid;
			}
		}
		return safe;
	}
}
