package rooms;

import java.awt.geom.Line2D;
import java.util.*;
import java.util.function.Predicate;

import rooms.gaps.*;

final class RoomLayoutImpl implements RoomLayout {

	private final double width, height;
	private final HashSet<RectangleLayout> interiorRects;
	private HorizontalGapCollection topGaps, bottomGaps;
	private VerticalGapCollection leftGaps, rightGaps;
	private DoorGapCollection<DoorGap> allGaps;
	private final RectangleLayout top, bottom, left, right;
	
	public RoomLayoutImpl(double width, double height, RectangleLayout[] interiorRects, DoorGap[] gaps) {
		this.width = width;
		this.height = height;
		this.interiorRects = new HashSet<>();
		top = RectangleLayout.of(0, 0, width, borderThickness());
		bottom = RectangleLayout.of(0, height - borderThickness(), width, borderThickness());
		left = RectangleLayout.of(0, 0, borderThickness(), height);
		right = RectangleLayout.of(width - borderThickness(), 0, borderThickness(), height);
		initInteriorRectangles(interiorRects);
		initGaps(gaps);
	}
	
	public void initInteriorRectangles(RectangleLayout... rects) {
		Collections.addAll(this.interiorRects, rects);
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
		topGaps = HorizontalGapCollection.from(tgaps);
		bottomGaps = HorizontalGapCollection.from(bgaps);
		leftGaps = VerticalGapCollection.from(lgaps);
		rightGaps = VerticalGapCollection.from(rgaps);
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
	public Collection<RectangleLayout> interiorRectsUnmodifiable() {
		return Collections.unmodifiableSet(interiorRects);
	}
	
	@Override
	public boolean lineIntersects(double x1, double y1, double x2, double y2) {
		Line2D.Double line = new Line2D.Double(x1, y1, x2, y2);
		if(top.lineIntersects(line) || bottom.lineIntersects(line) ||
				left.lineIntersects(line) || right.lineIntersects(line))
			return true;
		for(RectangleLayout rect : interiorRects)
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
	
	boolean removeGap(DoorGap gap) {
		return allGaps.remove(gap) && switch(gap.side()) {
			case TOP -> topGaps.remove(gap);
			case BOTTOM -> bottomGaps.remove(gap);
			case LEFT -> leftGaps.remove(gap);
			case RIGHT -> rightGaps.remove(gap);
		};
	}
	
	@Override
	public void removeGapsIf(Predicate<? super DoorGap> predicate) {
		List<DoorGap> toRemove = new ArrayList<>();
		for(DoorGap g : allGaps)
			if(predicate.test(g))
				toRemove.add(g);
		for(DoorGap g : toRemove)
			removeGap(g);
	}

	@Override
	public RoomLayout copy() {
		return new RoomLayoutImpl(width, height, interiorRects.toArray(RectangleLayout[]::new), allGaps.toArray());
	}
	
}
