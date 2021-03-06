package rooms.gaps;

import java.util.Collection;
import java.util.Iterator;

final class ArbitraryDoorGapCollection implements DoorGapCollection<DoorGap> {

	private final Collection<? extends DoorGap> backing;
	
	ArbitraryDoorGapCollection(Collection<? extends DoorGap> backing) {
		this.backing = backing;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Iterator<DoorGap> iterator() {
		return (Iterator<DoorGap>) backing.iterator();
	}

	@Override
	public int size() {
		return backing.size();
	}

	@Override
	public boolean remove(DoorGap gap) {
		return backing.remove(gap);
	}
	
	@Override
	public String toString() {
		return backing.toString();
	}
	
}
