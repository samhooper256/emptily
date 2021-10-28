package base.game.popups;

import base.Main;
import fxutils.Images;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;

public class DeathPopup extends ExpandingPopup {
	
	private static final double YOU_DIED_FONT_SIZE = 72;
	private static final double REPLAY_FONT_SIZE = 60;
	private static final double BUTTON_BAR_HEIGHT = 120;
	private static final String UNHOVERED_BUTTON_STYLE = "-fx-background-color: orange; -fx-background-radius: 12";
	private static final String HOVERED_BUTTON_STYLE = "-fx-background-color: #fca63d; -fx-background-radius: 12";
	
	private static ChangeListener<Boolean> makeHoverListener(Button button) {
		return (obj, ov, isHovered) -> {
			if(isHovered)
				button.setStyle(HOVERED_BUTTON_STYLE);
			else
				button.setStyle(UNHOVERED_BUTTON_STYLE);
		};
	}
	
	private final VBox vBox;
	private final Label youDied;
	private final HBox buttonBar;
	private final Button mainMenu, replay;
	
	public DeathPopup() {
		super(500, 300);
		
		youDied = new Label("You Died!");
		youDied.setFont(Font.font("Courier New", FontWeight.BOLD, YOU_DIED_FONT_SIZE));
		youDied.setTextFill(Color.RED);
		
		replay = new Button("Replay");
		replay.setFont(Font.font("Courier New", FontWeight.BOLD, REPLAY_FONT_SIZE));
		replay.setStyle(UNHOVERED_BUTTON_STYLE);
		replay.hoverProperty().addListener(makeHoverListener(replay));
		replay.setOnAction(eh -> replayAction());
		replay.setMaxHeight(BUTTON_BAR_HEIGHT);
		replay.setMinHeight(BUTTON_BAR_HEIGHT);
		
		mainMenu = new Button();
		mainMenu.setStyle(UNHOVERED_BUTTON_STYLE);
		mainMenu.hoverProperty().addListener(makeHoverListener(mainMenu));
		mainMenu.setMaxHeight(BUTTON_BAR_HEIGHT);
		mainMenu.setMinHeight(BUTTON_BAR_HEIGHT);
		mainMenu.setGraphic(new ImageView(Images.get("home.png", 100, 100, true, true)));
		mainMenu.setContentDisplay(ContentDisplay.CENTER);
		mainMenu.setOnAction(eh -> mainMenuAction());
		
		buttonBar = new HBox(10, mainMenu, replay);
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
	
	private void mainMenuAction() {
		Main.outerScene().switchToMainMenu();
	}
	
}