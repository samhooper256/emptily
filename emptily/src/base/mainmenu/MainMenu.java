package base.mainmenu;

import base.Main;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class MainMenu extends StackPane {

	private static final String PLAY_BUTTON_CSS = "main-menu-play";
	
	private final VBox vBox, buttonBox;
	private final Title title;
	private final Button play;
	private final ControlsButton controlsButton;
	
	public MainMenu() {
		vBox = new VBox();
		vBox.setAlignment(Pos.CENTER);
		
		title = new Title();
		
		play = new Button("Play");
		play.getStyleClass().add(PLAY_BUTTON_CSS);
		play.setOnAction(eh -> playButtonAction());
		
		controlsButton = new ControlsButton();
		
		buttonBox = new VBox(12, play, controlsButton);
		buttonBox.setAlignment(Pos.TOP_CENTER);
		vBox.getChildren().addAll(title, buttonBox);
		
		getChildren().add(vBox);
	}
	
	private void playButtonAction() {
		Main.outerScene().startGame();
	}
	
	public void animateIn() {
		if(controlsButton.isExpanded())
			controlsButton.reset();
		title.animateIn();
	}
	
	public Title title() {
		return title;
	}
	
}
