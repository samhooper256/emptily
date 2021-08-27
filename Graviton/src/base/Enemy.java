package base;

import javafx.geometry.Bounds;

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
	
}
