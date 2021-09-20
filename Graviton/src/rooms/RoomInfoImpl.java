package rooms;

import java.util.*;

import javafx.geometry.Side;

public record RoomInfoImpl(RoomLayout layout, double tlx, double tly,
		HashMap<Double, VisibilityGraph> vmap,
		Map<Side, Set<DoorGap>> gaps) implements RoomInfo {

	private static Map<Side, Set<DoorGap>> noGaps() {
		Set<DoorGap> set = Collections.emptySet();
		return Map.of(Side.TOP, set, Side.RIGHT, set, Side.BOTTOM, set, Side.LEFT, set);
	}
	
	private static Map<Side, Set<DoorGap>> emptyGaps() {
		return Map.of(Side.TOP, new HashSet<>(), Side.RIGHT, new HashSet<>(),
				Side.BOTTOM, new HashSet<>(), Side.LEFT, new HashSet<>());
	}
	
	public RoomInfoImpl(RoomLayout layout, double tlx, double tly) {
		this(layout, tlx, tly, new HashMap<>());
	}
	
	public RoomInfoImpl(RoomLayout layout, double tlx, double tly, HashMap<Double, VisibilityGraph> vmap) {
		this(layout, tlx, tly, vmap, noGaps());
	}
	
	public RoomInfoImpl(RoomLayout layout, double tlx, double tly, DoorGap... gaps) {
		this(layout, tlx, tly, new HashMap<>(), emptyGaps());
		for(DoorGap gap : gaps)
			gaps().get(gap.side()).add(gap);
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
