package base.mainmenu;

import javafx.animation.FadeTransition;
import javafx.scene.control.Label;
import javafx.util.Duration;

class AuthorLabel extends Label {
	
	private static final String CSS = "author-label";
	private static final Duration INTRO_DURATION = Duration.millis(250);
	
	private final FadeTransition intro;
	
	AuthorLabel() {
		super("by Sam Hooper");
		getStyleClass().add(CSS);
		
		setOpacity(0);
		intro = new FadeTransition(INTRO_DURATION, this);
		intro.setFromValue(0);
		intro.setToValue(1);
	}

	void animateIn() {
		intro.playFromStart();
	}
	
}
