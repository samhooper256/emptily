package enemies;

import javafx.geometry.*;
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
	
	double xvel();
	
	double yvel();
	
	/** If this enemy does not move, this method throws an {@link UnsupportedOperationException}. */
	void setxvel(double xvel);
	
	/** If this enemy does not move, this method throws an {@link UnsupportedOperationException}. */
	void setyvel(double yvel);
	
	Point2D center();
	
	default double centerX() {
		return center().getX();
	}
	
	default double centerY() {
		return center().getY();
	}
	
	double maxVelocity();
	
	/** Returns {@code true} if this {@link Enemy} must be killed before the room unlocks. {@code true} by default. */
	default boolean isRequired() {
		return true;
	}
	
	void die();
	
}
