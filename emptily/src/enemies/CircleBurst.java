package enemies;

import base.Main;
import javafx.animation.Transition;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class CircleBurst extends Pane {

	private static final Duration DEFAULT_DURATION = Duration.millis(300);
	
	private final class BurstAnimation extends Transition {

		BurstAnimation() {
			setOnFinished(eh -> Main.content().requestRemove(CircleBurst.this));
			setCycleDuration(DEFAULT_DURATION);
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
	
	public CircleBurst(Color color, double radius) {
		this.radius = radius;
		this.circle = new Circle(0, 0, radius, Color.TRANSPARENT);
		circle.setStroke(color);
		this.animation = new BurstAnimation();
		getChildren().add(circle);
	}
	
	public void startAnimation() {
		circle.setRadius(0);
		animation.playFromStart();
	}
	
}
