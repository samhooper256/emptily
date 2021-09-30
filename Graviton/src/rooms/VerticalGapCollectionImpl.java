package rooms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public final class VerticalGapCollectionImpl implements VerticalGapCollection {

	private final Collection<VerticalGap> baseGaps;
	private final List<VerticalGap> sortedTB, sortedBT;
	
	/** Assumes {@code baseGaps} is never modified after calling this constructor. */
	public VerticalGapCollectionImpl(Collection<VerticalGap> baseGaps) {
		this.baseGaps = baseGaps;
		sortedTB = new ArrayList<>(baseGaps);
		Collections.sort(sortedTB, Comparator.comparingDouble(VerticalGap::topDist));
		sortedBT = new ArrayList<>(baseGaps);
		Collections.sort(sortedBT, Comparator.comparingDouble(VerticalGap::bottomDist));
	}
	
	@Override
	public Iterator<VerticalGap> iterator() {
		return baseGaps.iterator();
	}

	@Override
	public int size() {
		return baseGaps.size();
	}

	@Override
	public List<VerticalGap> sorted(WallDirection direction) {
		return switch(direction) {
			case TOP_TO_BOTTOM -> Collections.unmodifiableList(sortedTB);
			case BOTTOM_TO_TOP -> Collections.unmodifiableList(sortedBT);
			default -> throw new IllegalArgumentException(String.format("Invalid direction: %s", direction));
		};
	}

	@Override
	public boolean remove(DoorGap gap) {
		boolean result = baseGaps.remove(gap);
		sortedTB.remove(gap);
		sortedBT.remove(gap);
		return result;
	}
	
	@Override
	public String toString() {
		return baseGaps.toString();
	}
	
}
