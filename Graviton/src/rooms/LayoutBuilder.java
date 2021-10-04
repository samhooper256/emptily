package rooms;

import rooms.gaps.DoorGap;

public final class LayoutBuilder {

	private double width, height;
	private RectangleLayout[] rects;
	private DoorGap[] gaps;
	
	public LayoutBuilder() {
		
	}
	
	public LayoutBuilder setWidth(double width) {
		this.width = width;
		return this;
	}
	
	public LayoutBuilder setHeight(double height) {
		this.height = height;
		return this;
	}
	
	public LayoutBuilder setSize(double width, double height) {
		setWidth(width);
		setHeight(height);
		return this;
	}
	
	public LayoutBuilder setRects(RectangleLayout... rects) {
		this.rects = rects;
		return this;
	}
	
	public LayoutBuilder setGaps(DoorGap... gaps) {
		this.gaps = gaps;
		return this;
	}
	
	public RoomLayout build() {
		if(width <= 0 || height <= 0)
			throw new IllegalStateException("Cannot build room with non-positive width or height.");
		if(rects == null)
			rects = new RectangleLayout[0];
		if(gaps == null || gaps.length == 0)
			throw new IllegalStateException("DoorGaps have not been configured properly. "
					+ "There must be at least one gap.");
		return new RoomLayoutImpl(width, height, rects, gaps);
	}
	
}
