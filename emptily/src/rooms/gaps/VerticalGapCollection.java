package rooms.gaps;

import java.util.*;

import rooms.WallDirection;

public interface VerticalGapCollection extends DoorGapCollection<VerticalGap> /* permits VerticalGapCollectionImpl */ {

	/** Assumes {@code baseGaps} is never modified after calling this constructor. */
	static VerticalGapCollection from(Collection<VerticalGap> baseGaps) {
		return new VerticalGapCollectionImpl(baseGaps);
	}
	
	/** Returns a {@link List} containing the same elements as this {@link VerticalGapCollection}, sorted in the
	 * given {@link WallDirection}. The given direction must be {@link WallDirection#TOP_TO_BOTTOM top to bottom} or
	 * {@link WallDirection#BOTTOM_TO_TOP bottom to top}. */
	List<VerticalGap> sorted(WallDirection direction);
	
}
