package rooms;

import java.util.*;

public record RoomInfoImpl(RoomLayout layout, double tlx, double tly,
		HashMap<Double, VisibilityGraph> vmap) implements RoomInfo {
	
	public RoomInfoImpl(RoomLayout layout, double tlx, double tly) {
		this(layout, tlx, tly, new HashMap<>());
	}
	
	@Override
	public VisibilityGraph visibilityGraph(double cornerDist) {
		if(vmap().containsKey(cornerDist))
			return vmap().get(cornerDist);
		VisibilityGraph graph = VisibilityGraph.forRoom(layout(), cornerDist);
		vmap().put(cornerDist, graph);
		return graph;
	}
	
}
