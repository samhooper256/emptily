package rooms;

public interface RoomInfo {
	
	public static RoomInfo re(RoomLayout layout, double tlx, double tly) {
		return new RoomInfoImpl(layout, tlx, tly);
	}
	
	RoomLayout layout();
	
	VisibilityGraph visibilityGraph(double cornerDist);
	
	double tlx();
	
	double tly();
	
}
