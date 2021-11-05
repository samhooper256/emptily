package base.game.popups;

import base.Main;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class DeathPopup extends ExpandingPopup {
	
	private static final String
			YOU_DIED_CSS = "you-died-label",
			HOME_BUTTON_CSS = "death-home",
			REPLAY_BUTTON_CSS = "death-replay";
	
	private final VBox vBox;
	private final Label youDied;
	private final ButtonBar buttonBar;
	private final Button replay;
	
	public DeathPopup() {
		super(500, 300);
		
		youDied = new Label("You Died!");
		youDied.getStyleClass().add(YOU_DIED_CSS);
		
		replay = new Button("Replay");
		replay.getStyleClass().add(REPLAY_BUTTON_CSS);
		replay.setOnAction(eh -> replayAction());
		
		buttonBar = new ButtonBar(replay);
		buttonBar.home().getStyleClass().add(HOME_BUTTON_CSS);
		
		vBox = new VBox(10, youDied, buttonBar);
		vBox.setAlignment(Pos.CENTER);
		
		getChildren().add(vBox);
	}
	
	private void replayAction() {
		Main.outerScene().replay();
	}
	
}