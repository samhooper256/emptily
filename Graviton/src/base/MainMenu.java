package base;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class MainMenu extends StackPane {

	private final VBox vBox;
	private final Label title;
	private final Button playButton;
	
	public MainMenu() {
		vBox = new VBox();
		vBox.setAlignment(Pos.CENTER);
		
		title = new Label("Graviton");
		
		playButton = new Button("Play");
		playButton.setOnAction(eh -> playButtonAction());
		
		vBox.getChildren().addAll(title, playButton);
		
		getChildren().add(vBox);
	}
	
	private void playButtonAction() {
		Main.outerScene().startGame();
	}
	
}
