package base;

import javafx.geometry.Bounds;
import javafx.scene.Node;

/** All concrete classes that implement {@link Enemy} must be a subclass of {@link Node}. Implementing
 * classes should not override {@link Object#equals(Object) equals} or {@link Object#hashCode() hashCode}. */
public interface Enemy {
	
	Bounds getBoundsInParent();
	
	void setLayoutX(double x);
	
	void setLayoutY(double y);
	
	default void setLocation(double x, double y) {
		setLayoutX(x);
		setLayoutY(y);
	}
	
	double getLayoutX();
	
	double getLayoutY();
	
	default double x() {
		return getLayoutX();
	}
	
	default double y() {
		return getLayoutY();
	}
	
	default Node asNode() {
		return (Node) this;
	}
	
}
