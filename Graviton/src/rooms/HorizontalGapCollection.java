package rooms;

import java.util.List;

public sealed interface HorizontalGapCollection extends DoorGapCollection<HorizontalGap>
		permits HorizontalGapCollectionImpl {

	/** Returns a {@link List} containing the same elements as this {@link HorizontalGapCollection}, sorted in the
	 * given {@link WallDirection}. The given direction must be {@link WallDirection#LEFT_TO_RIGHT left to right} or
	 * {@link WallDirection#RIGHT_TO_LEFT right to left}. */
	List<HorizontalGap> sorted(WallDirection direction);
	
}
