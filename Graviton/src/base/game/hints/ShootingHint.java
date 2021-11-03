package base.game.hints;

import javafx.scene.control.Label;

public class ShootingHint extends UserHint {

	private static final double WIDTH = 200;
	private static final String CSS = "shooting-hint";
	
	private final Label text;
	
	public ShootingHint() {
		text = new Label("Left click to shoot at enemies");
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
