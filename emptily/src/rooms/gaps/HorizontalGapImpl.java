package rooms.gaps;

import javafx.geometry.Side;

final class HorizontalGapImpl implements HorizontalGap {
	
	private final Side side;
	private final double leftDist, rightDist;
	
	public HorizontalGapImpl(Side side, double leftDist, double rightDist) {
		if(side.isVertical())
			throw new IllegalArgumentException(String.format("Invalid side: %s", side));
		this.side = side;
		this.leftDist = leftDist;
		this.rightDist = rightDist;
	}
	
	@Override
	public Side side() {
		return side;
	}
	
	@Override
	public double leftDist() {
		return leftDist;
	}
	
	@Override
	public double rightDist() {
		return rightDist;
	}
	
}