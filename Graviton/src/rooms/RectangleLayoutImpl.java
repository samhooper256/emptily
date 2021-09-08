package rooms;

import java.awt.geom.Line2D;

public record RectangleLayoutImpl(double x, double y, double width, double height)
		implements RectangleLayout {

	@Override
	public boolean lineIntersects(Line2D.Double line) {
		return line.intersects(x(), y(), width(), height());
	}
	
}
