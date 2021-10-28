package base.game;

import java.util.function.Consumer;

import base.DelayUpdatable;
import base.Main;
import base.game.content.Intersections;
import enemies.Enemy;
import fxutils.*;
import javafx.animation.*;
import javafx.animation.Animation.Status;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.*;
import javafx.scene.Node;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.util.Duration;

public class Player extends StackPane implements DelayUpdatable {
	
	private static final GravityMode DEFAULT_MODE = GravityMode.DOWN;
	private static final Color PLAYER_COLOR = Color.BLUE;
	private static final double DEFAULT_WIDTH = 20, DEFAULT_HEIGHT = DEFAULT_WIDTH;
	private static final double INTRO_RADIUS = DEFAULT_WIDTH / 2 + 20;
	private static final double COLLIDE_FLUSH = 0.5;
	/** in nanos*/
	private static final long INVINCIBILITY_TIME = 1_000_000_000L; 
	
	private final StackPane color;
	private final Timeline introTimeline;
	private final Circle introCircle1, introCircle2;
	private final SimpleDoubleProperty introFrac;
	
	private GravityMode mode;
	private double xvel, yvel, x, y;
	private long invincibilityTimer = 0;
	private boolean introStarted;
	
	public Player() {
		yvel = 0;
		xvel = 0;
		mode = DEFAULT_MODE;
		color = new StackPane();
		color.setOpaqueInsets(new Insets(1));
		color.setBackground(Backgrounds.of(PLAYER_COLOR));
		color.setMinWidth(DEFAULT_WIDTH);
		color.setMinHeight(DEFAULT_HEIGHT);
		color.setMaxWidth(DEFAULT_WIDTH);
		color.setMaxHeight(DEFAULT_HEIGHT);
		getChildren().addAll(color);
		introStarted = false;
		setVisible(false);
		introCircle1 = new Circle(0, 0, 4);
		introCircle1.setFill(PLAYER_COLOR);
		introCircle2 = new Circle(0, 0, 4);
		introCircle2.setFill(PLAYER_COLOR);
		introFrac = new SimpleDoubleProperty();
		introFrac.addListener((x, ov, nv) -> {
			double frac = nv.doubleValue();
			animateIntro(frac);
		});
		introTimeline = new Timeline();
		introTimeline.getKeyFrames().add(new KeyFrame(Duration.ZERO, new KeyValue(this.opacityProperty(), 0)));
		introTimeline.getKeyFrames().add(new KeyFrame(Duration.ZERO, new KeyValue(introCircle1.opacityProperty(), 1)));
		introTimeline.getKeyFrames().add(new KeyFrame(Duration.ZERO, new KeyValue(introCircle2.opacityProperty(), 1)));
		introTimeline.getKeyFrames().add(new KeyFrame(Duration.ZERO, new KeyValue(introFrac, 0)));
		introTimeline.getKeyFrames().add(new KeyFrame(Duration.millis(1000), new KeyValue(this.opacityProperty(), 1)));
		introTimeline.getKeyFrames().add(new KeyFrame(Duration.millis(800), new KeyValue(introCircle1.opacityProperty(), 0)));
		introTimeline.getKeyFrames().add(new KeyFrame(Duration.millis(800), new KeyValue(introCircle2.opacityProperty(), 0)));
		introTimeline.getKeyFrames().add(new KeyFrame(Duration.millis(1000), new KeyValue(introFrac, 1)));
		
	}
	
	private void animateIntro(double frac) {
		double ang1 = frac * 2 * Math.PI - Math.PI / 2;
		double ang2 = ang1 + Math.PI;
		double pcx = getLayoutX() + width() / 2, pcy = getLayoutY() + height() / 2;
		double radius = INTRO_RADIUS * (1 - frac);
		double c1cx = Math.cos(ang1) * radius + pcx, c1cy = Math.sin(ang1) * radius + pcy;
		double c2cx = Math.cos(ang2) * radius + pcx, c2cy = Math.sin(ang2) * radius + pcy;
		
		Nodes.setLayout(introCircle1, c1cx, c1cy);
		Nodes.setLayout(introCircle2, c2cx, c2cy);
	}
	
	@Override
	public void update(long nsSinceLastFrame) {
		if(!introStarted) {
			introStarted = true;
			setOpacity(0);
			setVisible(true);
			introTimeline.playFromStart();
		}
		else if(introTimeline.getStatus() != Status.RUNNING) {
			updateIfNotInIntro(nsSinceLastFrame);
		}
	}

	private void updateIfNotInIntro(long nsSinceLastFrame) {
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
		Main.healthBar().hit();
		if(hp() == 0)
			Main.outerScene().die();
		else
			invincibilityTimer = INVINCIBILITY_TIME;
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
		introCircle1.setLayoutX(x);
		setLayoutX(x);
	}
	
	public void setY(double y) {
		this.y = y;
		introCircle1.setLayoutY(y);
		setLayoutY(y);
	}
	
	/** Moves this {@link Player} to the given coordinates, sets its velocity to zero, and sets its gravity
	 * direction to down.*/
	public void resetTo(double x, double y) {
		xvel = 0;
		yvel = 0;
		setX(x);
		setY(y);
		introTimeline.stop();
		introStarted = false;
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
	
	public Node[] introNodes() {
		return new Node[] {introCircle1, introCircle2};
	}
	
}
