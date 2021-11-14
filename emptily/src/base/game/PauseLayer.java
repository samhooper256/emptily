package base.game;

import fxutils.Backgrounds;
import javafx.animation.*;
import javafx.animation.Animation.Status;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.util.Duration;

public class PauseLayer extends StackPane {

	private static final Duration INTRO_DURATION = Duration.millis(400), OUTRO_DURATION = INTRO_DURATION;
	private static final double BUTTON_FONT_SIZE = 60;
	private static final double BUTTON_BAR_HEIGHT = 120;
	private static final String
			TITLE_CSS = "pause-title",
			RESUME_BUTTON_CSS = "pause-resume",
			HOME_BUTTON_CSS = "pause-home";
	
	private static final double DEST_OPACITY = 1;
	
	private final FadeTransition introFade, outroFade;
	private final VBox vBox;
	private final Label title;
	private final Button resume, home;
	private final HBox buttonBar;
	private final Pane over;
	
	public PauseLayer(Pane over) {
		setVisible(false);
		setOpacity(DEST_OPACITY);
		setBackground(Backgrounds.of(Color.rgb(0, 0, 0, 0.5)));
		this.over = over;
		
		introFade = new FadeTransition(INTRO_DURATION, this);
		introFade.setFromValue(0);
		introFade.setToValue(DEST_OPACITY);
		
		outroFade = new FadeTransition(OUTRO_DURATION, this);
		outroFade.setFromValue(DEST_OPACITY);
		outroFade.setToValue(0);
		outroFade.setOnFinished(eh -> {
			setVisible(false);
			this.over.getChildren().remove(this);
		});
		
		title = new Label("Paused");
		title.getStyleClass().add(TITLE_CSS);
		
		resume = new Button("Resume");
		resume.getStyleClass().add(RESUME_BUTTON_CSS);
		resume.setMinHeight(BUTTON_BAR_HEIGHT);
		resume.setMaxHeight(BUTTON_BAR_HEIGHT);
		resume.setFont(Font.font("Courier New", BUTTON_FONT_SIZE));
		resume.setOnAction(eh -> resumeAction());
		
		home = new MainMenuButton(BUTTON_BAR_HEIGHT);
		home.getStyleClass().add(HOME_BUTTON_CSS);
		
		buttonBar = new HBox(10, home, resume);
		buttonBar.setMinHeight(BUTTON_BAR_HEIGHT);
		buttonBar.setMaxHeight(BUTTON_BAR_HEIGHT);
		buttonBar.setAlignment(Pos.CENTER);
		
		vBox = new VBox(10, title, buttonBar);
		vBox.setAlignment(Pos.CENTER);
		
		getChildren().add(vBox);
	}

	public void animateIn() {
		setOpacity(0);
		setVisible(true);
		introFade.playFromStart();
	}
	
	public void animateOut() {
		if(introFade.getStatus() == Status.RUNNING) {
			introFade.stop();
			outroFade.setFromValue(getOpacity());
		}
		else {
			outroFade.setFromValue(DEST_OPACITY);
		}
		outroFade.playFromStart();
	}

	private void resumeAction() {
		animateOut();
	}
	
}
