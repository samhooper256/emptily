package base;

import fxutils.Backgrounds;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class Platform extends StackPane {
	
	private final double width, height;
	
	public Platform(double width, double height) {
		setBackground(Backgrounds.of(Color.GREEN));
		this.width = width;
		this.height = height;
		setMinWidth(width);
		setMinHeight(height);
		
	}
	
}
