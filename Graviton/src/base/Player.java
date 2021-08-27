package base;

import java.util.function.Consumer;

import fxutils.Backgrounds;
import javafx.geometry.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class Player extends StackPane {

	private static class BottomLine extends CollisionLine {
		
		Platform platform = null;
		
		public BottomLine(double startX, double startY, double endX, double endY) {
			super(Side.BOTTOM, startX, startY, endX, endY);
		}
		boolean hasPlatform() {
			return platform != null;
		}
		
		public Platform platform() {
			return platform;
		}
		
		public void setPlatform(Platform p) {
			this.platform = p;
		}
		
		public void clearPlatform() {
			setPlatform(null);
		}
	}
	
	private static final GravityMode DEFAULT_MODE = GravityMode.DOWN;
	private static final double DEFAULT_WIDTH = 20, DEFAULT_HEIGHT = DEFAULT_WIDTH;
	private static final double COLLIDE_CLAMP = 0.5, COLLIDE_FLUSH = 0.5;
	
	private final StackPane red;
	
	private GravityMode mode;
	private double xvel, yvel, x, y;
	
	public Player() {
		yvel = 0;
		xvel = 0;
		x = 100;
		y = 100;
		mode = DEFAULT_MODE;
		red = new StackPane();
		red.setOpaqueInsets(new Insets(1));
		red.setBackground(Backgrounds.of(Color.RED));
		red.setMinWidth(DEFAULT_WIDTH);
		red.setMinHeight(DEFAULT_HEIGHT);
		red.setMaxWidth(DEFAULT_WIDTH);
		red.setMaxHeight(DEFAULT_HEIGHT);
		getChildren().addAll(red);
		
	}
	
	public void update(long nsSinceLastFrame) {
//		System.out.printf("%n%nUPDATE %d%n", nsSinceLastFrame);
		
		double sec = nsSinceLastFrame / 1e9;
		double oldX = x, oldY = y;
		double oldXvel = xvel, oldYvel = yvel;
		double xaccel = mode.xAccel(), yaccel = mode.yAccel();
		
		x += xvel;
		y += yvel;
		
		boolean backtrackedX = false, backtrackedY = false;
		//X:
		setLayoutX(x);
		for(Platform p : Main.scene().pane().platforms()) {
			if(backtrackedX)
				break;
			if(intersects(p)) {
				backtrackedX = true;
				x = findX(oldX, x, p);
				setLayoutX(x);
				xvel = 0;
			}
		}
		if(!backtrackedX)
			xvel += xaccel * sec;
		
		//Y:
		setLayoutY(y);
		for(Platform p : Main.scene().pane().platforms()) {
			if(backtrackedY)
				break;
			if(intersects(p)) {
				backtrackedY = true;
				y = findY(oldY, y, p);
				setLayoutY(y);
				yvel = 0;
			}
		}
		if(!backtrackedY)
			yvel += yaccel * sec;
		
//		System.out.printf("ends with (x,y)=(%f,%f)\t\t\t(xv,yv)=(%f,%f)", x, y, xvel, yvel);
	}
	
	private boolean intersects(Platform p) {
		return p.getBoundsInParent().intersects(getBoundsInParent());
	}
	
	private boolean intersectsAny() {
		for(Platform p : Main.scene().pane().platforms())
			if(intersects(p))
				return true;
		return false;
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
	
	private double findX(double safe, double collide, Platform p) {
		return findCoord(safe, collide, p, this::setLayoutX);
	}
	
	private double findY(double safe, double collide, Platform p) {
		return findCoord(safe, collide, p, this::setLayoutY);
	}

	/** assumes current position is collideY*/
	private double findCoord(double safe, double collide, Platform p, Consumer<Double> setter) {
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
