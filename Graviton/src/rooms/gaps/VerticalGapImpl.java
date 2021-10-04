package rooms.gaps;

import javafx.geometry.Side;

record VerticalGapImpl(Side side, double topDist, double bottomDist) implements VerticalGap {
	
	VerticalGapImpl {
		if(side.isHorizontal())
			throw new IllegalArgumentException(String.format("Invalid side: %s", side));
	}
	
}
