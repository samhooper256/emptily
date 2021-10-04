package rooms.gaps;

import javafx.geometry.Side;

record HorizontalGapImpl(Side side, double leftDist, double rightDist) implements HorizontalGap {
	
	HorizontalGapImpl {
		if(side.isVertical())
			throw new IllegalArgumentException(String.format("Invalid side: %s", side));
	}
	
}