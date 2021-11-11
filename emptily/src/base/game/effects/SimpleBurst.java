package base.game.effects;

import base.Main;
import javafx.animation.Transition;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class SimpleBurst extends Burst {

	private static final Duration DEFAULT_DURATION = Duration.millis(300);
	
	private final class BurstAnimation extends Transition {

		BurstAnimation(Duration duration) {
			setOnFinished(eh -> Main.content().requestRemove(SimpleBurst.this));
			setCycleDuration(duration);
		}
		
		@Override
		protected void interpolate(double frac) {
			circle.setRadius(frac * radius);
			circle.setStrokeWidth((-frac * (frac - 1)) * (radius / 2));
		}
		
	}
	
	private final double radius;
	private final Circle circle;
	private final BurstAnimation animation;
	private final Duration duration;
	
	public SimpleBurst(Color color, double radius) {
		this(color, radius, DEFAULT_DURATION);
	}
	
	public SimpleBurst(Color color, double radius, Duration duration) {
		this.radius = radius;
		this.circle = new Circle(0, 0, radius, Color.TRANSPARENT);
		circle.setStroke(color);
		circle.setRadius(0);
		this.animation = new BurstAnimation(duration);
		this.duration = duration;
		getChildren().add(circle);
	}
	
	@Override
	public void startAnimation() {
		circle.setRadius(0);
		animation.playFromStart();
	}
	
	@Override
	public Duration duration() {
		return duration;
	}

	@Override
	public void finishAnimation() {
		animation.stop();
		circle.setRadius(0);
	}
	
}
