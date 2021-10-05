package base;

import java.util.function.Consumer;

import fxutils.Backgrounds;
import javafx.geometry.*;
import javafx.scene.Node;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class Player extends StackPane implements DelayUpdatable {
	
	private static final GravityMode DEFAULT_MODE = GravityMode.DOWN;
	private static final double DEFAULT_WIDTH = 20, DEFAULT_HEIGHT = DEFAULT_WIDTH;
	private static final double COLLIDE_FLUSH = 0.5;
	private static final long INVINCIBILITY_TIME = 1_000_000_000L; 
	
	private final StackPane color;
	
	private GravityMode mode;
	private double xvel, yvel, x, y;
	private long invincibilityTimer = 0;
	
	public Player() {
		yvel = 0;
		xvel = 0;
		x = 300;
		y = 50;
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
			Enemy enemy = Main.pane().getEnemyIntersectingPlayer();
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
		Node p = Main.pane().getPlatformOrDoorIntersecting(this);
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
		p = Main.pane().getPlatformOrDoorIntersecting(this);
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
		invincibilityTimer = INVINCIBILITY_TIME;
		Main.healthBar().hit();
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
	
	public double width() {
		return getWidth();
	}
	
	public double height() {
		return getHeight();
	}
	
	public Point2D center() {
		return new Point2D(x() + width() / 2, y() + height() / 2);
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
			double mid = Maths.mean(safe, collide);
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
