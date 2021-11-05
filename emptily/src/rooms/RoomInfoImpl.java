package rooms;

import java.util.*;

public final class RoomInfoImpl implements RoomInfo {
	
	private final RoomLayout layout;
	private final double tlx, tly;
	private final HashMap<Double, VisibilityGraph> vmap;
	
	public RoomInfoImpl(RoomLayout layout, double tlx, double tly, HashMap<Double, VisibilityGraph> vmap) {
		this.layout = layout;
		this.tlx = tlx;
		this.tly = tly;
		this.vmap = vmap;
	}
	
	public RoomInfoImpl(RoomLayout layout, double tlx, double tly) {
		this(layout, tlx, tly, new HashMap<>());
	}
	
	@Override
	public VisibilityGraph visibilityGraph(double cornerDist) {
		if(vmap.containsKey(cornerDist))
			return vmap.get(cornerDist);
		VisibilityGraph graph = VisibilityGraph.forRoom(layout(), cornerDist);
		vmap.put(cornerDist, graph);
		return graph;
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof RoomInfo && tlx() == ((RoomInfo) obj).tlx() && tly() == ((RoomInfo) obj).tly();
	}

	@Override
	public int hashCode() {
		return Arrays.hashCode(new double[] {tlx(), tly()});
	}

	@Override
	public RoomLayout layout() {
		return layout;
	}

	@Override
	public double tlx() {
		return tlx;
	}

	@Override
	public double tly() {
		return tly;
	}
	
}
