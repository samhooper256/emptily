package base;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class MainMenu extends StackPane {

	private static final String
			TITLE_CSS = "main-menu-title",
			PLAY_BUTTON_CSS = "main-menu-play";
	
	private final VBox vBox;
	private final Label title;
	private final Button play;
	
	public MainMenu() {
		vBox = new VBox();
		vBox.setAlignment(Pos.CENTER);
		
		title = new Label("Graviton");
		title.getStyleClass().add(TITLE_CSS);
		
		play = new Button("Play");
		play.getStyleClass().add(PLAY_BUTTON_CSS);
		play.setOnAction(eh -> playButtonAction());
		
		vBox.getChildren().addAll(title, play);
		
		getChildren().add(vBox);
	}
	
	private void playButtonAction() {
		Main.outerScene().startGame();
	}
	
}
