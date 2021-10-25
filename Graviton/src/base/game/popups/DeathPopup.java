package base.game.popups;

import base.Main;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;

public class DeathPopup extends ExpandingPopup {
	
	private static final double YOU_DIED_FONT_SIZE = 72;
	private static final double REPLAY_FONT_SIZE = 60;
	private static final String UNHOVERED_BUTTON_STYLE = "-fx-background-color: orange; -fx-background-radius: 12";
	private static final String HOVERED_BUTTON_STYLE = "-fx-background-color: #fca63d; -fx-background-radius: 12";
	
	private final VBox vBox;
	private final Label youDied;
	private final Button replay;
	
	public DeathPopup() {
		super(400, 300);
		
		youDied = new Label("You Died!");
		youDied.setFont(Font.font("Courier New", FontWeight.BOLD, YOU_DIED_FONT_SIZE));
		youDied.setTextFill(Color.RED);
		
		replay = new Button("Replay");
		replay.setFont(Font.font("Courier New", FontWeight.BOLD, REPLAY_FONT_SIZE));
		replay.setStyle(UNHOVERED_BUTTON_STYLE);
		replay.hoverProperty().addListener((obj, ov, isHovered) -> {
			if(isHovered)
				replay.setStyle(HOVERED_BUTTON_STYLE);
			else
				replay.setStyle(UNHOVERED_BUTTON_STYLE);
		});
		replay.setOnAction(eh -> replayAction());
		
		vBox = new VBox(10, youDied, replay);
		vBox.setAlignment(Pos.CENTER);
		
		getChildren().add(vBox);
	}
	
	private void replayAction() {
		Main.outerScene().replay();
	}
	
	
}