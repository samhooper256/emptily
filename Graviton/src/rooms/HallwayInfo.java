package rooms;

public interface HallwayInfo {

	HallwayLayout layout();
	
	double tlx();
	
	double tly();
	
	default boolean lineIntersects(double x1, double y1, double x2, double y2) {
		return layout().lineIntersects(x1 - tlx(), y1 - tly(), x2 - tlx(), y2 - tly());
	}
	
}
