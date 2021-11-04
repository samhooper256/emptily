package base.mainmenu;

import javafx.animation.Transition;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class ControlsButton extends StackPane {
	
	private static final double HIDDEN_WIDTH = 300, SHOWN_WIDTH = 600, HEIGHT = 100;
	private static final Duration showDuration = Duration.millis(500);
	
	private static final String
			CONTROLS_BUTTON_LABEL_CSS = "controls-button-label",
			CONTROLS_BUTTON_CSS = "controls-button";
	
	private final Transition showTransition = new Transition() {
		
		{
			setCycleDuration(showDuration);
			setOnFinished(eh -> {
				getChildren().clear();
				getChildren().add(controlsBox());
			});
		}

		@Override
		protected void interpolate(double frac) {
			setMaxWidth(HIDDEN_WIDTH + frac * (SHOWN_WIDTH - HIDDEN_WIDTH));
			label.setOpacity(1 - frac);
		}
		
	};
	
	private final Label label;
	private final ControlsBox controlsBox;
	
	public ControlsButton() {
		label = new Label("Controls");
		label.getStyleClass().add(CONTROLS_BUTTON_LABEL_CSS);
		controlsBox = new ControlsBox();
		
		getStyleClass().add(CONTROLS_BUTTON_CSS);
		setMaxWidth(HIDDEN_WIDTH);
		setMinHeight(HEIGHT);
		setMaxHeight(HEIGHT);
		
		setOnMouseClicked(eh -> showControls());
		getChildren().add(label);
	}

	private void showControls() {
		showTransition.playFromStart();
	}
	
	private ControlsBox controlsBox() {
		return controlsBox;
	}
	
}
