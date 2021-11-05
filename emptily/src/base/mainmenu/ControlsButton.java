package base.mainmenu;

import javafx.animation.Transition;
import javafx.css.PseudoClass;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class ControlsButton extends StackPane {
	
	private static final double HIDDEN_WIDTH = 300, SHOWN_WIDTH = 560, HEIGHT = 100;
	private static final Duration showDuration = Duration.millis(400);
	private static final PseudoClass HIGHLIGHT = PseudoClass.getPseudoClass("highlight");
	private static final String
			CONTROLS_BUTTON_LABEL_CSS = "controls-button-label",
			CONTROLS_BUTTON_CSS = "controls-button";
	
	private final Transition showTransition = new Transition() {
		
		{
			setCycleDuration(showDuration);
			setOnFinished(eh -> {
				getChildren().clear();
				controlsBox().animateIn();
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
	
	private boolean expanded;
	
	public ControlsButton() {
		expanded = false;
		
		label = new Label("Controls");
		label.getStyleClass().add(CONTROLS_BUTTON_LABEL_CSS);
		controlsBox = new ControlsBox();
		
		getStyleClass().add(CONTROLS_BUTTON_CSS);
		setMaxWidth(HIDDEN_WIDTH);
		setMinHeight(HEIGHT);
		setMaxHeight(HEIGHT);
		
		setOnMouseClicked(eh -> mouseClicked());
		this.setOnMouseEntered(eh -> {
			if(!expanded)
				this.pseudoClassStateChanged(HIGHLIGHT, true);
		});
		this.setOnMouseExited(eh -> this.pseudoClassStateChanged(HIGHLIGHT, false));
		getChildren().add(label);
	}

	private void mouseClicked() {
		if(!expanded)
			showControls();
	}
	
	private void showControls() {
		expanded = true;
		this.pseudoClassStateChanged(HIGHLIGHT, false);
		showTransition.playFromStart();
	}
	
	private ControlsBox controlsBox() {
		return controlsBox;
	}
	
	public void reset() {
		controlsBox.reset();
		setMinWidth(HIDDEN_WIDTH);
		setMaxWidth(HIDDEN_WIDTH);
		expanded = false;
		getChildren().clear();
		label.setOpacity(1);
		getChildren().add(label);
	}
	
	public boolean isExpanded() {
		return expanded;
	}
	
}
