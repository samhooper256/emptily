package base.game;

import javafx.geometry.Bounds;
import javafx.scene.Node;

/** All implementing classes must be subclasses of {@link Node}. */
public interface Door {

	static Door horizontal(double x, double y, double width, double height) {
		return new HorizontalDoor(x, y, width, height);
	}
	
	static Door vertical(double x, double y, double width, double height) {
		return new VerticalDoor(x, y, width, height);
	}
	
	void close();
	
	boolean isClosed();
	
	void open();
	
	default boolean isOpen() {
		return !isClosed();
	}
	
	Bounds getBoundsInParent();
	
	default Node asNode() {
		return (Node) this;
	}
	
}
