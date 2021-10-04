package rooms.gaps;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public sealed interface DoorGapCollection<T extends DoorGap> extends Iterable<T>
	permits ArbitraryDoorGapCollection, HorizontalGapCollection, VerticalGapCollection {
	
	/** The given {@link Collection} is defensively copied; changes to the given collection will not be reflected in
	 * this {@link DoorGapCollection} or vice versa.*/
	static DoorGapCollection<DoorGap> fromArbitraryGaps(Collection<DoorGap> gaps) {
		return new ArbitraryDoorGapCollection(new ArrayList<>(gaps));
	}
	
	@Override
	Iterator<T> iterator();
	
	int size();
	
	default boolean isEmpty() {
		return size() == 0;
	}
	
	boolean remove(DoorGap gap);
	
	default DoorGap[] toArray() {
		DoorGap[] arr = new DoorGap[size()];
		int i = 0;
		for(DoorGap g : this)
			arr[i++] = g;
		return arr;
	}
	
}
