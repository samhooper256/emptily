package base.game.hints;

import fxutils.Nodes;
import javafx.animation.Animation.Status;
import javafx.animation.FadeTransition;
import javafx.scene.layout.*;
import javafx.util.Duration;

public abstract class UserHint extends StackPane {
	
	private static final Duration
			FADE_IN_DURATION = Duration.millis(200),
			FADE_OUT_DURATION = Duration.millis(500);
	
	private final FadeTransition fadeIn, fadeOut;
	
	private Pane over;
	
	public UserHint() {
		super();
		setVisible(false);
		fadeOut = new FadeTransition(FADE_OUT_DURATION, this);
		fadeOut.setFromValue(1);
		fadeOut.setToValue(0);
		fadeOut.setOnFinished(eh -> hide());
		fadeIn = new FadeTransition(FADE_IN_DURATION, this);
		fadeIn.setFromValue(0);
		fadeIn.setToValue(1);
	}
	
	
	public void animateOn(Pane p, double layoutX, double layoutY) {
		over = p;
		Nodes.setLayout(this, layoutX, layoutY);
		setOpacity(0);
		setVisible(true);
		over.getChildren().add(this);
		fadeIn.playFromStart();
	}
	
	public void animateOff() {
		if(isVisible() && fadeOut.getStatus() != Status.RUNNING)
			fadeOut.playFromStart();
	}


	private void hide() {
		over.getChildren().remove(this);
		over = null;
		setVisible(false);
	}
	
}
