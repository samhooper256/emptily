package fxutils;

import javafx.geometry.Point2D;
import javafx.scene.shape.Line;

public final class Lines {

	private Lines() {
		
	}
	
	public static Line between(Point2D a, Point2D b) {
		return new Line(a.getX(), a.getY(), b.getX(), b.getY());
	}
	
}
