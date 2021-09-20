package rooms;

import java.awt.geom.Line2D;
import java.util.*;

import javafx.geometry.Side;

public class RoomLayoutImpl implements RoomLayout {

	private final double width, height;
	private final HashSet<RectangleLayout> rects;
	private final Map<Side, Set<DoorGap>> gaps;
	
	public RoomLayoutImpl(double width, double height, RectangleLayout[] rects, DoorGap[] gaps) {
		this.width = width;
		this.height = height;
		this.rects = new HashSet<>();
		initRectangles(rects);
		this.gaps = new HashMap<>();
		initGaps(gaps);
	}
	
	public void initRectangles(RectangleLayout... rects) {
		Collections.addAll(this.rects, rects);
	}
	
	private void initGaps(DoorGap[] gapsArr) {
		for(DoorGap gap : gapsArr) {
			if(!gaps.containsKey(gap.side()))
				gaps.put(gap.side(), new HashSet<>());
			gaps.get(gap.side()).add(gap);
		}
		for(Side s : Side.values())
			if(!gaps.containsKey(s))
				gaps.put(s, Collections.emptySet());
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
	
	@Override
	public boolean intervisible(double x1, double y1, double x2, double y2) {
		Line2D.Double line = new Line2D.Double(x1, y1, x2, y2);
		for(RectangleLayout rect : rects)
			if(rect.lineIntersects(line))
				return false;
		return true;
	}

	@Override
	public Map<Side, Set<DoorGap>> gaps() {
		return gaps;
	}

}
