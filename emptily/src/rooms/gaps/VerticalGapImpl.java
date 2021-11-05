package rooms.gaps;

import javafx.geometry.Side;

final class VerticalGapImpl implements VerticalGap {
	
	private final Side side;
	private final double topDist, bottomDist;
	
	VerticalGapImpl(Side side, double topDist, double bottomDist) {
		if(side.isHorizontal())
			throw new IllegalArgumentException(String.format("Invalid side: %s", side));
		this.side = side;
		this.topDist = topDist;
		this.bottomDist = bottomDist;
	}

	@Override
	public double topDist() {
		return topDist;
	}

	@Override
	public double bottomDist() {
		return bottomDist;
	}

	@Override
	public Side side() {
		return side;
	}
	
}
