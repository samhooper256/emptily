package rooms.gaps;

import java.util.*;

import rooms.WallDirection;

final class HorizontalGapCollectionImpl implements HorizontalGapCollection {

	private final Collection<HorizontalGap> baseGaps;
	private final List<HorizontalGap> sortedLR, sortedRL;
	
	/** Assumes {@code baseGaps} is never modified after calling this constructor. */
	public HorizontalGapCollectionImpl(Collection<HorizontalGap> baseGaps) {
		this.baseGaps = baseGaps;
		sortedLR = new ArrayList<>(baseGaps);
		Collections.sort(sortedLR, Comparator.comparingDouble(HorizontalGap::leftDist));
		sortedRL = new ArrayList<>(baseGaps);
		Collections.sort(sortedRL, Comparator.comparingDouble(HorizontalGap::rightDist));
	}
	
	@Override
	public Iterator<HorizontalGap> iterator() {
		return baseGaps.iterator();
	}

	@Override
	public int size() {
		return baseGaps.size();
	}

	@Override
	public List<HorizontalGap> sorted(WallDirection direction) {
		switch(direction) {
			case LEFT_TO_RIGHT: return Collections.unmodifiableList(sortedLR);
			case RIGHT_TO_LEFT: return Collections.unmodifiableList(sortedRL);
			default: throw new IllegalArgumentException(String.format("Invalid direction: %s", direction));
		}
	}

	@Override
	public boolean remove(DoorGap gap) {
		boolean result = baseGaps.remove(gap);
		sortedLR.remove(gap);
		sortedRL.remove(gap);
		return result;
	}

	@Override
	public String toString() {
		return baseGaps.toString();
	}
	
}
