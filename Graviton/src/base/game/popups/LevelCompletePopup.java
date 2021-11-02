package base.game.popups;

import base.Main;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class LevelCompletePopup extends ExpandingPopup {
	
	private static final String
			TITLE_CSS = "lcp-title",
			HOME_CSS = "lcp-home",
			NEXT_LEVEL_CSS = "lcp-next-level";
	
	private final VBox vBox;
	private final Label title;
	private final ButtonBar buttonBar;
	private final Button nextLevel;
	
	public LevelCompletePopup() {
		super(600, 300);
		
		title = new Label("Level Complete!");
		title.getStyleClass().add(TITLE_CSS);
		
		nextLevel = new Button("Next Level");
		nextLevel.getStyleClass().add(NEXT_LEVEL_CSS);
		nextLevel.setOnAction(eh -> Main.pane().startNextLevel());
		
		buttonBar = new ButtonBar(nextLevel);
		buttonBar.home().getStyleClass().add(HOME_CSS);
		
		vBox = new VBox(10, title, buttonBar);
		vBox.setAlignment(Pos.CENTER);
		getChildren().add(vBox);
	}
	
	public void startOpeningAnimation(int levelIndex) {
		title.setText(String.format("Level %d Complete!", levelIndex + 1));
		super.startOpeningAnimation();
	}

	@Override
	public void startOpeningAnimation() {
		title.setText("Level Complete!");
		super.startOpeningAnimation();
	}
	
}
