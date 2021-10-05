package base;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Door extends Rectangle {

	public Door(double x, double y, double width, double height) {
		super(x, y, width, height);
		setFill(Color.ORANGE);
		setVisible(false);
	}
	
	public void close() {
		setVisible(true);
	}
	
	public boolean isClosed() {
		return isVisible();
	}
	
	public void open() {
		setVisible(false);
	}
	
}
