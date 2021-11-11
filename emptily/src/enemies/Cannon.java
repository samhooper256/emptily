package enemies;

import base.*;
import base.game.effects.*;
import enemies.paths.*;
import fxutils.*;
import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.util.Duration;
import utils.Angles;

public class Cannon extends AbstractSimpleMovementEnemy implements DelayUpdatable {

	public static final double RADIUS = 10, RECT_SIZE = 6, STROKE_WIDTH = 4, OUTER_SIZE = 30;
	
	private static final double
			DEFAULT_HEALTH = 5,
			CORNER_DIST = OUTER_SIZE * Math.sqrt(2) / 2 + 1,
			VELOCITY = 30, /** in pixels per second. */
			MIN_PLAYER_DIST = 200,
			SHAFT_ROTATION_SPEED = Math.PI * 3; //in radians per second.
	
	private static final long
			SHOOT_COOLDOWN = 2_000_000_000,
			MIN_PLAYER_TIME_TO_CHANGE_PATH = 500_000_000,
			MIN_PLAYER_TIME_TO_BLAST = MIN_PLAYER_TIME_TO_CHANGE_PATH;
	
	private final DistanceSightTimedPathManager pathManager;
	private final Pane innerPane;
	private final Circle circle;
	private final Rectangle rect;
	
	
	private long nsSinceLastCannonball;
	private double shaftDestAngrad, shaftCurAngrad;
	
	public Cannon() {
		super(DEFAULT_HEALTH);
		Nodes.fixSize(this, OUTER_SIZE);
		
		innerPane = new Pane();
		
		circle = new Circle(10, Color.TRANSPARENT);
		circle.setStroke(Color.RED);
		circle.setStrokeWidth(4);
		circle.setStrokeType(StrokeType.INSIDE);
		circle.setLayoutX(OUTER_SIZE / 2); //inside innerPane
		circle.setLayoutY(OUTER_SIZE / 2); //inside innerPane
		
		rect = new Rectangle(RECT_SIZE, RECT_SIZE);
		rect.setFill(Color.RED);
		
		innerPane.getChildren().addAll(circle, rect);
		getChildren().add(innerPane);
		pathManager =
				new DistanceSightTimedPathManager(this, CORNER_DIST, MIN_PLAYER_DIST, MIN_PLAYER_TIME_TO_CHANGE_PATH);
		nsSinceLastCannonball = 0;
		shaftDestAngrad = 0;
		shaftCurAngrad = 0;
//		this.setBorder(Borders.of(Color.BLUE));
	}
	
	@Override
	public void update(long nsSinceLastFrame) {
		pathManager.update(nsSinceLastFrame);
		nsSinceLastCannonball += nsSinceLastFrame;
		double sec = nsSinceLastFrame / 1e9;
		if(pathManager.couldSeePlayerAfterLastUpdate()) {
			orientShaftTowards(pathManager.angradToPlayer(), sec);
			if(pathManager.sightTime() >= MIN_PLAYER_TIME_TO_BLAST && nsSinceLastCannonball >= SHOOT_COOLDOWN) {
				blast();
				nsSinceLastCannonball = 0;
			}
		}
		else {
			orientShaftTowards(pathManager.angradOfVelocity(), sec);
		}
	}

	private void orientShaftTowards(double angrad, double sec) {
		//angle will be from -PI to +PI.
		shaftDestAngrad = Angles.make0to2PI(angrad);
		if(shaftCurAngrad == shaftDestAngrad)
			return;
		double 	shaftAdd = sec * SHAFT_ROTATION_SPEED,
				upDist = Angles.upWrapDist2PI(shaftCurAngrad, shaftDestAngrad),
				downDist = Angles.downWrapDist2PI(shaftCurAngrad, shaftDestAngrad);
		double newAngrad;
		if(upDist <= downDist) {
			newAngrad = Angles.make0to2PI(shaftCurAngrad + shaftAdd);
			if(newAngrad > shaftDestAngrad)
				newAngrad = shaftDestAngrad;
		}
		else {
			newAngrad = Angles.make0to2PI(shaftCurAngrad - shaftAdd);
			if(newAngrad < shaftDestAngrad)
				newAngrad = shaftDestAngrad;
		}
		pointShaft(newAngrad);
	}
	
	private void pointShaft(double angrad) {
		shaftCurAngrad = angrad;
		double effectiveRadius = RADIUS;
		double xFromCenter = Math.cos(angrad) *  effectiveRadius, yFromCenter = Math.sin(angrad) * effectiveRadius;
		double finalX = OUTER_SIZE / 2 + xFromCenter - rect.getWidth() / 2;
		double finalY = OUTER_SIZE / 2 + yFromCenter - rect.getHeight() / 2;
		Nodes.setLayout(rect, finalX, finalY);
		rect.setRotate(2 * angrad / 2 / Math.PI * 180);
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
		return x() + OUTER_SIZE / 2;
	}
	
	@Override
	public double centerY() {
		return y() + OUTER_SIZE / 2;
	}

	@Override
	public double maxVelocity() {
		return VELOCITY;
	}

	@Override
	protected void onDeath() {
		delete();
		addBurstCentered(new LayeredBurst(
				new Burst[] {
						new SimpleBurst(Color.RED, RADIUS * 1.5, Duration.millis(400)),
						new SimpleBurst(Color.RED, RADIUS * 2, Duration.millis(600))},
				new Duration[] {Duration.ZERO, Duration.millis(100)}));
	}
	
	

}
