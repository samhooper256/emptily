package base.game;

import javafx.animation.Animation.Status;
import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public abstract class AbstractDoor extends StackPane implements Door /* permits HorizontalDoor, VerticalDoor */ {
	
	protected static final Duration ANIMATION_DURATION = Duration.millis(1000);
	
	private final class CloseAnimation extends Transition {

		CloseAnimation() {
			setCycleDuration(ANIMATION_DURATION);
			setInterpolator(Interpolator.EASE_IN);
		}
		
		@Override
		protected void interpolate(double frac) {
			closeInterpolate(frac);
		}
		
	}
	
	private final class OpenAnimation extends Transition {
		
		public OpenAnimation() {
			setCycleDuration(ANIMATION_DURATION);
			setInterpolator(Interpolator.EASE_IN);
			setOnFinished(eh -> setVisible(false));
		}

		@Override
		protected void interpolate(double frac) {
			openInterpolate(frac);
		}
		
	}
	
	protected final Rectangle rect;
	private final CloseAnimation closeAnimation;
	private final OpenAnimation openAnimation;
	
	protected AbstractDoor(double x, double y, double width, double height) {
		setMaxWidth(width);
		setMinWidth(width);
		setMaxHeight(height);
		setMinHeight(height);
		setLayoutX(x);
		setLayoutY(y);
		
		rect = new Rectangle();
		setRelevantRectDimensionTo0();
		rect.setFill(Color.ORANGE);
		closeAnimation = new CloseAnimation();
		openAnimation = new OpenAnimation();
		
		getChildren().add(rect);
		setVisible(false);
	}
	
	@Override
	public boolean isClosed() {
		return isVisible();
	}
	
	@Override
	public void open() {
		if(closeAnimation.getStatus() == Status.RUNNING) {
			Duration time = ANIMATION_DURATION.subtract(closeAnimation.getCurrentTime());
			closeAnimation.stop();
			openAnimation.playFrom(time);
		}
		else {
			openAnimation.playFromStart();
		}
	}
	
	@Override
	public void close() {
		setRelevantRectDimensionTo0();
		setVisible(true);
		closeAnimation.playFromStart();
	}
	
	protected abstract void openInterpolate(double frac);
	
	protected abstract void closeInterpolate(double frac);
	
	protected abstract void setRelevantRectDimensionTo0();
	
}
