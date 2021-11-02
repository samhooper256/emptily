package floors;

import java.util.Objects;

import hallways.HallwayInfo;
import rooms.RoomInfo;

public final class HallwayJoin {

	private final RoomInfo a, b;
	private final HallwayInfo info;

	public HallwayJoin(RoomInfo a, HallwayInfo info, RoomInfo b) {
		this.a = a;
		this.info = info;
		this.b = b;
	}
	
	
	@Override
	public int hashCode() {
		return Objects.hash(a, b, info);
	}


	@Override
	public boolean equals(Object obj) {
		if(this == obj)
			return true;
		if(obj == null || getClass() != obj.getClass())
			return false;
		HallwayJoin other = (HallwayJoin) obj;
		return Objects.equals(a, other.a) && Objects.equals(b, other.b) && Objects.equals(info, other.info);
	}


	RoomInfo other(RoomInfo ri) {
		if(ri.equals(a))
			return b;
		if(ri.equals(b))
			return a;
		throw new IllegalArgumentException("The given RoomInfo is not an end of the hallway");
	}
	
	public RoomInfo a() {
		return a;
	}
	
	public HallwayInfo info() {
		return info;
	}
	
	public RoomInfo b() {
		return b;
	}
	
}
