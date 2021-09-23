package rooms;

public interface RoomInfo {
	
	public static RoomInfo re(RoomLayout layout, double tlx, double tly) {
		return new RoomInfoImpl(layout, tlx, tly);
	}
	
	RoomLayout layout();
	
	VisibilityGraph visibilityGraph(double cornerDist);
	
	double tlx();
	
	double tly();
	
	/** Assumes that the given coordinates are in the same coordinate space as {@link #tlx()} and {@link #tly()}.*/
	default boolean lineIntersects(double x1, double y1, double x2, double y2) {
		return layout().lineIntersects(x1 - tlx(), y1 - tly(), x2 - tlx(), y2 - tly());
	}
	
}
