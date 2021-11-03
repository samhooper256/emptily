package base.game.hints;

import javafx.scene.control.Label;

public class MovementRulesHint extends UserHint {

	private static final double WIDTH = 200;
	private static final String CSS = "movement-rules-hint";
	
	private final Label text;
	
	public MovementRulesHint() {
		text = new Label("Use WASD to change the direction of gravity");
		text.setWrapText(true);
		setMaxWidth(WIDTH);
		setMinWidth(WIDTH);
		getStyleClass().add(CSS);
		getChildren().add(text);
	}
	
	public double width() {
		return WIDTH;
	}
	
}
