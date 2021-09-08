package rooms;

import java.awt.geom.Line2D;
import java.util.*;

public class RoomLayoutImpl implements RoomLayout {

	private final double width, height;
	private final HashSet<RectangleLayout> rects;
	
	public RoomLayoutImpl(double width, double height) {
		this.width = width;
		this.height = height;
		rects = new HashSet<>();
	}
	
	public RoomLayoutImpl(double width, double height, RectangleLayout... rects) {
		this(width, height);
		addRectangles(rects);
	}
	
	@Override
	public double width() {
		return width;
	}

	@Override
	public double height() {
		return height;
	}

	@Override
	public Collection<RectangleLayout> rectsUnmodifiable() {
		return Collections.unmodifiableSet(rects);
	}
	
	public void addRectangles(RectangleLayout... rects) {
		Collections.addAll(this.rects, rects);
	}

	@Override
	public boolean intervisible(double x1, double y1, double x2, double y2) {
		Line2D.Double line = new Line2D.Double(x1, y1, x2, y2);
		for(RectangleLayout rect : rects)
			if(rect.lineIntersects(line))
				return false;
		return true;
	}

}
