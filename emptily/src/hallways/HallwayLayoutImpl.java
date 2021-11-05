package hallways;

import rooms.RectangleLayout;

final class HallwayLayoutImpl implements HallwayLayout {

	private final boolean vertical;
	private final double girth, length, wallWidth;
	private final RectangleLayout wall1, wall2;
	
	HallwayLayoutImpl(double girth, double length, double wallWidth, boolean vertical) {
		this.girth = girth;
		this.length = length;
		this.wallWidth = wallWidth;
		this.vertical = vertical;
		if(vertical) {
			wall1 = RectangleLayout.of(0, 0, wallWidth, length);
			wall2 = RectangleLayout.of(wallWidth + girth, 0, wallWidth, length);
		}
		else {
			wall1 = RectangleLayout.of(0, 0, length, wallWidth);
			wall2 = RectangleLayout.of(0, wallWidth + girth, length, wallWidth);
		}
	}
	
	@Override
	public double girth() {
		return girth;
	}

	@Override
	public double wallWidth() {
		return wallWidth;
	}

	@Override
	public boolean isVertical() {
		return vertical;
	}

	@Override
	public boolean isHorizonal() {
		return !vertical;
	}

	@Override
	public double length() {
		return length;
	}

	@Override
	public RectangleLayout wall1() {
		return wall1;
	}

	@Override
	public RectangleLayout wall2() {
		return wall2;
	}
	
	@Override
	public double width() {
		return isVertical() ? exteriorGirth() : length();
	}
	
	@Override
	public double height() {
		return isVertical() ? length() : exteriorGirth();
	}
	
}
