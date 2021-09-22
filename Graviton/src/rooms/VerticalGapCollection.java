package rooms;

import java.util.List;

public sealed interface VerticalGapCollection extends DoorGapCollection<VerticalGap> permits VerticalGapCollectionImpl {

	/** Returns a {@link List} containing the same elements as this {@link VerticalGapCollection}, sorted in the
	 * given {@link WallDirection}. The given direction must be {@link WallDirection#TOP_TO_BOTTOM top to bottom} or
	 * {@link WallDirection#BOTTOM_TO_TOP bottom to top}. */
	List<VerticalGap> sorted(WallDirection direction);
	
}
