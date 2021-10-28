package base.game.popups;

import base.Main;
import base.game.MainMenuButton;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class DeathPopup extends ExpandingPopup {
	
	private static final double BUTTON_BAR_HEIGHT = 120;
	private static final String
			YOU_DIED_CSS = "you-died-label",
			HOME_BUTTON_CSS = "death-home",
			REPLAY_BUTTON_CSS = "death-replay";
	
	private final VBox vBox;
	private final Label youDied;
	private final HBox buttonBar;
	private final Button home, replay;
	
	public DeathPopup() {
		super(500, 300);
		
		youDied = new Label("You Died!");
		youDied.getStyleClass().add(YOU_DIED_CSS);
		
		replay = new Button("Replay");
		replay.getStyleClass().add(REPLAY_BUTTON_CSS);
		replay.setOnAction(eh -> replayAction());
		replay.setMaxHeight(BUTTON_BAR_HEIGHT);
		replay.setMinHeight(BUTTON_BAR_HEIGHT);
		
		home = new MainMenuButton(BUTTON_BAR_HEIGHT);
		home.getStyleClass().add(HOME_BUTTON_CSS);
		
		buttonBar = new HBox(10, home, replay);
		buttonBar.setAlignment(Pos.CENTER);
		buttonBar.setMaxHeight(BUTTON_BAR_HEIGHT);
		buttonBar.setMinHeight(BUTTON_BAR_HEIGHT);
		
		vBox = new VBox(10, youDied, buttonBar);
		vBox.setAlignment(Pos.CENTER);
		
		getChildren().add(vBox);
	}
	
	private void replayAction() {
		Main.outerScene().replay();
	}
	
}