package base.game;

public final class VerticalDoor extends AbstractDoor implements Door {
	
	public VerticalDoor(double x, double y, double width, double height) {
		super(x, y, width, height);
		rect.setWidth(width);
	}
	
	@Override
	protected void openInterpolate(double frac) {
		rect.setHeight((1d - frac) * getMinHeight());
	}

	@Override
	protected void closeInterpolate(double frac) {
		rect.setHeight(frac * getMinHeight());
	}

	@Override
	protected void setRelevantRectDimensionTo0() {
		rect.setHeight(0);
	}
	
	
}
