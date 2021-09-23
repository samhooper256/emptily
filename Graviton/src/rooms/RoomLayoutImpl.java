package rooms;

import java.awt.geom.Line2D;
import java.util.*;

public class RoomLayoutImpl implements RoomLayout {

	private final double width, height;
	private final HashSet<RectangleLayout> rects;
	private HorizontalGapCollection topGaps, bottomGaps;
	private VerticalGapCollection leftGaps, rightGaps;
	private DoorGapCollection<DoorGap> allGaps;
	
	public RoomLayoutImpl(double width, double height, RectangleLayout[] rects, DoorGap[] gaps) {
		this.width = width;
		this.height = height;
		this.rects = new HashSet<>();
		initRectangles(rects);
		initGaps(gaps);
	}
	
	public void initRectangles(RectangleLayout... rects) {
		Collections.addAll(this.rects, rects);
	}
	
	private void initGaps(DoorGap[] gapsArr) {
		Set<HorizontalGap> tgaps = new HashSet<>(), bgaps = new HashSet<>();
		Set<VerticalGap> lgaps = new HashSet<>(), rgaps = new HashSet<>();
		for(DoorGap gap : gapsArr) {
			switch(gap.side()) {
				case TOP -> tgaps.add(gap.asHorizontal());
				case BOTTOM -> bgaps.add(gap.asHorizontal());
				case LEFT -> lgaps.add(gap.asVertical());
				case RIGHT -> rgaps.add(gap.asVertical());
			}
		}
		topGaps = new HorizontalGapCollectionImpl(tgaps);
		bottomGaps = new HorizontalGapCollectionImpl(bgaps);
		leftGaps = new VerticalGapCollectionImpl(lgaps);
		rightGaps = new VerticalGapCollectionImpl(rgaps);
		allGaps = DoorGapCollection.fromArbitraryGaps(Arrays.asList(gapsArr));
	}
	
	@Override
	public double exteriorWidth() {
		return width;
	}

	@Override
	public double exteriorHeight() {
		return height;
	}

	@Override
	public Collection<RectangleLayout> rectsUnmodifiable() {
		return Collections.unmodifiableSet(rects);
	}
	
	@Override
	public boolean lineIntersects(double x1, double y1, double x2, double y2) {
		Line2D.Double line = new Line2D.Double(x1, y1, x2, y2);
		for(RectangleLayout rect : rects)
			if(rect.lineIntersects(line))
				return true;
		return false;
	}
	
	@Override
	public HorizontalGapCollection topGaps() {
		return topGaps;
	}

	@Override
	public HorizontalGapCollection bottomGaps() {
		return bottomGaps;
	}

	@Override
	public VerticalGapCollection leftGaps() {
		return leftGaps;
	}

	@Override
	public VerticalGapCollection rightGaps() {
		return rightGaps;
	}

	@Override
	public DoorGapCollection<DoorGap> gaps() {
		return allGaps;
	}
	
}
