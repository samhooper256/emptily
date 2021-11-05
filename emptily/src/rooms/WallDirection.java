package rooms;

public enum WallDirection {
	
	LEFT_TO_RIGHT, RIGHT_TO_LEFT, TOP_TO_BOTTOM, BOTTOM_TO_TOP;
	
	/** Returns {@code true} for left to right or right to left. */
	public boolean isHorizontal() {
		return this == LEFT_TO_RIGHT || this == RIGHT_TO_LEFT;
	}
	
	/** Returns {@code true} for top to bottom or bottom to top. */
	public boolean isVertical() {
		return !isHorizontal();
	}
	
}
