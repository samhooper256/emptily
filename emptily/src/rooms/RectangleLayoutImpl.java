package rooms;

import java.awt.geom.Line2D;

public final class RectangleLayoutImpl implements RectangleLayout {

	private final double x, y, width, height;
	
	public RectangleLayoutImpl(double x, double y, double width, double height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	@Override
	public boolean lineIntersects(Line2D.Double line) {
		return line.intersects(x(), y(), width(), height());
	}

	@Override
	public double x() {
		return x;
	}

	@Override
	public double y() {
		return y;
	}

	@Override
	public double width() {
		return width;
	}

	@Override
	public double height() {
		return height;
	}
	
}
