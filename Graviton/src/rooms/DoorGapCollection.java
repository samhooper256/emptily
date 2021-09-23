package rooms;

import java.util.Collection;
import java.util.Iterator;

public sealed interface DoorGapCollection<T extends DoorGap> extends Iterable<T>
	permits ArbitraryDoorGapCollection, HorizontalGapCollection, VerticalGapCollection {
	
	/** Assumes the given {@link Collection} is never modified after it is passed to this method. */
	static DoorGapCollection<DoorGap> fromArbitraryGaps(Collection<DoorGap> gaps) {
		return new ArbitraryDoorGapCollection(gaps);
	}
	
	@Override
	Iterator<T> iterator();
	
	int size();
	
	default boolean isEmpty() {
		return size() == 0;
	}
	
}
