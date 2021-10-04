package base;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Door extends Rectangle {

	public Door(double x, double y, double width, double height) {
		super(x, y, width, height);
		setFill(Color.ORANGE);
		setVisible(false);
	}
	
	public void appear() {
		setVisible(true);
	}
	
}
