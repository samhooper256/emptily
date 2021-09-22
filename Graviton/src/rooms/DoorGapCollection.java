package rooms;

import java.util.Iterator;

public sealed interface DoorGapCollection<T extends DoorGap> extends Iterable<T>
	permits HorizontalGapCollection, VerticalGapCollection {

	@Override
	Iterator<T> iterator();
	
	int size();
	
	default boolean isEmpty() {
		return size() == 0;
	}
	
}
