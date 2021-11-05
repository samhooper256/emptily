package fxutils;

import javafx.geometry.Point2D;
import javafx.scene.shape.Line;

public final class Lines {

	private Lines() {
		
	}
	
	public static Line between(Point2D a, Point2D b) {
		return new Line(a.getX(), a.getY(), b.getX(), b.getY());
	}
	
	/**
	 * The body of this method is equivalent to:
	 * <pre><code>
	 * return new Line(a.getX() + shiftX, a.getY() + shiftY, b.getX() + shiftX, b.getY() + shiftY);
	 * </code></pre>
	 * */
	public static Line between(Point2D a, Point2D b, double shiftX, double shiftY) {
		return new Line(a.getX() + shiftX, a.getY() + shiftY, b.getX() + shiftX, b.getY() + shiftY);
	}
	
}
