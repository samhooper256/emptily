package rooms;

public record RectangleLayout(double x, double y, double width, double height) {
	
	public double ulx() {
		return x;
	}
	
	public double uly() {
		return y;
	}
	
	public double urx() {
		return x + width;
	}
	
	public double ury() {
		return y;
	}
	
	public double llx() {
		return x;
	}
	
	public double lly() {
		return y + height;
	}
	
	public double lrx() {
		return x + width;
	}
	
	public double lry() {
		return y + height;
	}
	
}
