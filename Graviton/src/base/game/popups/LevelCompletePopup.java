package base.game.popups;

import base.Main;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.*;

public class LevelCompletePopup extends ExpandingPopup {
	
	private static final double LEVEL_COMPLETE_FONT_SIZE = 48;
	private static final double NEXT_LEVEL_FONT_SIZE = 36;
	private static final String UNHOVERED_BUTTON_STYLE = "-fx-background-color: green; -fx-background-radius: 12";
	private static final String HOVERED_BUTTON_STYLE = "-fx-background-color: #018f01; -fx-background-radius: 12";
	
	private final VBox vBox;
	private final Label title;
	private final Button nextLevel;
	
	public LevelCompletePopup() {
		super(500, 300);
		
		title = new Label("Level Complete!");
		title.setFont(Font.font("Courier New", FontWeight.BOLD, LEVEL_COMPLETE_FONT_SIZE));
		title.setTextFill(Color.BLUE);
		
		nextLevel = new Button("Next Level");
		initNextLevel();
		
		vBox = new VBox(10, title, nextLevel);
		vBox.setAlignment(Pos.CENTER);
		getChildren().add(vBox);
	}
	
	private void initNextLevel() {
		nextLevel.setFont(Font.font("Courier New", FontWeight.BOLD, NEXT_LEVEL_FONT_SIZE));
		nextLevel.setStyle(UNHOVERED_BUTTON_STYLE);
		nextLevel.hoverProperty().addListener((obj, ov, isHovered) -> {
			if(isHovered)
				nextLevel.setStyle(HOVERED_BUTTON_STYLE);
			else
				nextLevel.setStyle(UNHOVERED_BUTTON_STYLE);
		});
		nextLevel.setOnAction(eh -> Main.pane().startNextLevel());
	}
	
}
