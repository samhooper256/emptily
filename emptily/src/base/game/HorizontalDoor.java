package base.game;

public final class HorizontalDoor extends AbstractDoor implements Door {
	
	public HorizontalDoor(double x, double y, double width, double height) {
		super(x, y, width, height);
		rect.setHeight(height);
	}

	@Override
	protected void openInterpolate(double frac) {
		rect.setWidth((1d - frac) * getMinWidth());
	}

	@Override
	protected void closeInterpolate(double frac) {
		rect.setWidth(frac * getMinWidth());
	}

	@Override
	protected void setRelevantRectDimensionTo0() {
		rect.setWidth(0);
	}
	
}
