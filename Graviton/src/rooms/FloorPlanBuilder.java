package rooms;

import java.util.*;

import javafx.geometry.Side;
import utils.RNG;

public final class FloorPlanBuilder {

	private final List<RoomLayout> layoutChoices;
	private final int desiredRoomCount;
	private final double hallwayLength;
	
	public FloorPlanBuilder(List<RoomLayout> layoutChoices, int desiredRoomCount, double hallwayLength) {
		this.layoutChoices = layoutChoices;
		this.desiredRoomCount = desiredRoomCount;
		this.hallwayLength = hallwayLength;
	}

	private Queue<RoomInfo> q;
	private Map<RoomInfo, Set<DoorGap>> usedGaps;
	private Set<HallwayInfo> hallways;
	
	void build() {
		q = new ArrayDeque<>();
		usedGaps = new HashMap<>();
		hallways = new HashSet<>();
		RoomLayout start = getRandomLayout();
		for(int i = 0; i < desiredRoomCount; i++)
			addRoom();
	}
	
	private void addRoom() {
		while(!q.isEmpty()) {
			RoomInfo i = q.remove();
			//ASSUMPTIONS: i still has at least one unused gap.
			RoomLayout oroom = getRandomLayout();
			Set<DoorGap> usedGaps = usedGaps(i);
			for(DoorGap unusedGap : i.layout().gaps()) {
				if(usedGaps.contains(unusedGap))
					continue;
				if(unusedGap.side() == Side.TOP) {
					
				}
				else if(unusedGap.side() == Side.BOTTOM) {
					
				}
				else if(unusedGap.side() == Side.LEFT) {
					VerticalGap igap = (VerticalGap) unusedGap;
					for(VerticalGap ogap : i.layout().rightGaps()) {
						double tlx = i.tlx() - hallwayLength - oroom.exteriorWidth();
						double tly = (i.tly() + i.layout().borderThickness() + igap.topDist()); //TODO finish this statement
					}
				}
				else if(unusedGap.side() == Side.RIGHT) {
					
				}
				
			}
		}
	}
	
	private boolean canPlaceRoom(RoomLayout layout, double tlx, double tly) {
		double trx = tlx + layout.exteriorWidth();
		double lly = tly + layout.exteriorHeight();
		for(HallwayInfo hi : hallways) {
			if(/*top:*/hi.lineIntersects(tlx, tly, trx, tly) || /*bottom:*/hi.lineIntersects(tlx, lly, trx, lly) ||
				/*left:*/hi.lineIntersects(tlx, tly, tlx, lly) || /*right:*/hi.lineIntersects(trx, tly, trx, lly))
				return false;
		}
		for(RoomInfo ri : usedGaps.keySet()) {
			if(/*top:*/ri.lineIntersects(tlx, tly, trx, tly) || /*bottom:*/ri.lineIntersects(tlx, lly, trx, lly) ||
				/*left:*/ri.lineIntersects(tlx, tly, tlx, lly) || /*right:*/ri.lineIntersects(trx, tly, trx, lly))
				return false;
		}
		return true;
	}
	
	private Set<DoorGap> usedGaps(RoomInfo info) {
		if(!usedGaps.containsKey(info))
			usedGaps.put(info, new HashSet<>());
		return usedGaps.get(info);
	}
	
	private RoomLayout getRandomLayout() {
		return layoutChoices.get(RNG.intExclusive(0, layoutChoices.size()));
	}
	
	private boolean allGapsUsed(RoomInfo info) {
		if(!usedGaps.containsKey(info))
			return false;
		return usedGaps.get(info).size() == info.layout().gapCount();
	}
	
	private double hallwayLength() {
		return hallwayLength;
	}
	
}
