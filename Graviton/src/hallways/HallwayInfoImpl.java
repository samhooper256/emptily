package hallways;

import java.util.Arrays;

public final class HallwayInfoImpl implements HallwayInfo {

	private final HallwayLayout layout;
	private final double tlx, tly;
	
	public HallwayInfoImpl(HallwayLayout layout, double tlx, double tly) {
		this.layout = layout;
		this.tlx = tlx;
		this.tly = tly;
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj instanceof HallwayInfo && tlx() == ((HallwayInfo) obj).tlx() && tly() == ((HallwayInfo) obj).tly();
	}
	
	@Override
	public int hashCode() {
		return Arrays.hashCode(new double[] {tlx, tly});
	}

	@Override
	public HallwayLayout layout() {
		return layout;
	}

	@Override
	public double tlx() {
		return tlx;
	}

	@Override
	public double tly() {
		return tly;
	}
	
}
