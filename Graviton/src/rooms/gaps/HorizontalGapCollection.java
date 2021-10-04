package rooms.gaps;

import java.util.*;

import rooms.WallDirection;

public sealed interface HorizontalGapCollection extends DoorGapCollection<HorizontalGap>
		permits HorizontalGapCollectionImpl {

	/** Assumes {@code baseGaps} is never modified after calling this constructor. */
	static HorizontalGapCollection from(Collection<HorizontalGap> baseGaps) {
		return new HorizontalGapCollectionImpl(baseGaps);
	}
	
	/** Returns a {@link List} containing the same elements as this {@link HorizontalGapCollection}, sorted in the
	 * given {@link WallDirection}. The given direction must be {@link WallDirection#LEFT_TO_RIGHT left to right} or
	 * {@link WallDirection#RIGHT_TO_LEFT right to left}. */
	List<HorizontalGap> sorted(WallDirection direction);
	
}
