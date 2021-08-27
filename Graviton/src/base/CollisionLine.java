package base;

import javafx.geometry.Side;
import javafx.scene.shape.Line;

public class CollisionLine extends Line {
	
	private final Side side;
	
	public CollisionLine(Side side, double startX, double startY, double endX, double endY) {
		super(startX, startY, endX, endY);
		this.side = side;
	}
	
	public Side side() {
		return side;
	}
	
}
