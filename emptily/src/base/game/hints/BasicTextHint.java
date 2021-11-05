package base.game.hints;

import javafx.scene.control.Label;

public class BasicTextHint extends UserHint {

	private static final String CSS = "basic-text-hint";
	private static final double DEFUALT_WIDTH = 200;
	
	private final double width;
	private final Label label;
	
	public BasicTextHint(String text) {
		this(text, DEFUALT_WIDTH);
	}
	
	public BasicTextHint(String text, double width) {
		this.width = width;
		this.setMinWidth(width);
		this.setMaxWidth(width);
		getStyleClass().add(CSS);
		label = new Label(text);
		label.setWrapText(true);
		getChildren().add(label);
	}
	
	public double width() {
		return width;
	}
	
}
