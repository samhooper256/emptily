package base.mainmenu;

import base.Main;
import javafx.animation.FadeTransition;
import javafx.scene.control.Button;
import javafx.util.Duration;

class PlayButton extends Button {

	private static final String CSS = "main-menu-play";
	private static final Duration INTRO_DURATION = Duration.millis(250);
	
	private final FadeTransition intro;
	
	PlayButton() {
		super("Play");
		getStyleClass().add(CSS);
		setOnAction(eh -> playButtonAction());
		
		setOpacity(0);
		intro = new FadeTransition(INTRO_DURATION, this);
		intro.setFromValue(0);
		intro.setToValue(1);
	}
	
	private void playButtonAction() {
		Main.outerScene().startGame();
	}
	
	void animateIn() {
		intro.playFromStart();
	}
	
}
