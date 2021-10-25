package hallways;

import java.util.Arrays;

public record HallwayInfoImpl(HallwayLayout layout, double tlx, double tly) implements HallwayInfo {

	@Override
	public boolean equals(Object obj) {
		return obj instanceof HallwayInfo o && tlx() == o.tlx() && tly() == o.tly();
	}
	
	@Override
	public int hashCode() {
		return Arrays.hashCode(new double[] {tlx, tly});
	}

}
