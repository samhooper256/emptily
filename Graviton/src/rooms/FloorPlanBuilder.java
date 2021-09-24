package rooms;

import java.util.*;
import java.util.stream.Collectors;

import javafx.geometry.Side;
import utils.*;

public final class FloorPlanBuilder {

	private static Iterable<DoorGap> randomOrder(DoorGapCollection<DoorGap> gaps) {
		ArrayList<DoorGap> a = new ArrayList<>(gaps.size());
		for(DoorGap g : gaps)
			a.add(g);
		Collections.shuffle(a);
		return a;
	}
	
	private final List<RoomLayout> layoutChoices;
	private final int desiredRoomCount;
	private final double hallwayLength, hallwayWallWidth;
	
	public FloorPlanBuilder(List<RoomLayout> layoutChoices, int desiredRoomCount, double hallwayLength, 
			double hallwayWallWidth) {
		this.layoutChoices = layoutChoices;
		this.desiredRoomCount = desiredRoomCount;
		this.hallwayLength = hallwayLength;
		this.hallwayWallWidth = hallwayWallWidth;
	}

	private Queue<RoomInfo> q;
	private Map<RoomInfo, Set<DoorGap>> usedGaps;
	private Set<HallwayInfo> hallways;
	
	public FloorPlan build() {
		q = new ArrayDeque<>();
		usedGaps = new HashMap<>();
		hallways = new HashSet<>();
		RoomLayout startLayout = getRandomLayout();
		RoomInfo startInfo = RoomInfo.re(startLayout, 0, 0);
		q.add(startInfo);
		usedGaps.put(startInfo, new HashSet<>());
		for(int i = 0; i < desiredRoomCount - 1; i++)
			addRoom();
		return FloorPlan.of(startInfo, usedGaps.keySet(), Collections.emptySet()); //TODO no empty set
	}
	
	private void addRoom() {
//		System.out.printf("[enter] addRoom(), allRooms: %s%n", formattedInfos(usedGaps.keySet()));
		while(!q.isEmpty()) {
//			System.out.printf("\tenter while%n");
			printq();
			RoomInfo i = q.peek();
//			System.out.printf("\ti=(%.1f, %.1f)%n", i.tlx() / 480, i.tly() / 480);
			RoomLayout ilayout = i.layout();
			//ASSUMPTIONS: i still has at least one unused gap.
			RoomLayout olayout = getRandomLayout();
			Set<DoorGap> usedGaps = usedGaps(i);
			for(DoorGap unusedGap : randomOrder(i.layout().gaps())) {
				if(usedGaps.contains(unusedGap))
					continue;
				if(unusedGap.isHorizontal()) {
					HorizontalGap igap = (HorizontalGap) unusedGap;
					if(unusedGap.side() == Side.TOP) {
						for(HorizontalGap ogap : olayout.bottomGaps()) {
							if(igap.sizeIn(ilayout) != ogap.sizeIn(olayout))
								continue;
							double tlx = i.tlx() + ilayout.borderThickness() + igap.leftDist() - ogap.leftDist() -
									olayout.borderThickness();
							double tly = i.tly() - hallwayLength - olayout.exteriorHeight();
							if(tryPlace(i, igap, olayout, ogap, tlx, tly))
								return;
						}
					}
					else if(unusedGap.side() == Side.BOTTOM) {
						for(HorizontalGap ogap : olayout.topGaps()) {
							if(igap.sizeIn(ilayout) != ogap.sizeIn(olayout))
								continue;
							double tlx = i.tlx() + ilayout.borderThickness() + igap.leftDist() - ogap.leftDist() -
									olayout.borderThickness();
							double tly = i.tly() + ilayout.exteriorHeight() + hallwayLength;
							if(tryPlace(i, igap, olayout, ogap, tlx, tly))
								return;
						}
					}
				}
				else {
					VerticalGap igap = (VerticalGap) unusedGap;
					if(unusedGap.side() == Side.LEFT) {
						for(VerticalGap ogap : olayout.rightGaps()) {
							if(igap.sizeIn(ilayout) != ogap.sizeIn(olayout))
								continue;
							double tlx = i.tlx() - hallwayLength - olayout.exteriorWidth();
							double tly = (i.tly() + ilayout.borderThickness() + igap.topDist()) -
									(ogap.topDist() + olayout.borderThickness());
							if(tryPlace(i, igap, olayout, ogap, tlx, tly))
								return;
						}
					}
					else if(unusedGap.side() == Side.RIGHT) {
						for(VerticalGap ogap : olayout.leftGaps()) {
							if(igap.sizeIn(ilayout) != ogap.sizeIn(olayout))
								continue;
							double tlx = i.tlx() + ilayout.exteriorWidth() + hallwayLength;
							double tly = (i.tly() + i.layout().borderThickness() + igap.topDist()) -
									(ogap.topDist() + olayout.borderThickness());
							if(tryPlace(i, igap, olayout, ogap, tlx, tly))
								return;
						}
					}
				}
			}
			q.remove();
		}
	}

	private void printq() {
		System.out.printf("\tq=%s%n", formattedInfos(q));
	}
	
	private String formattedInfos(Collection<? extends RoomInfo> q) {
		return q.stream().map(ri -> String.format("(%.1f, %.1f)", ri.tlx() / 480, ri.tly() / 480))
				.collect(Collectors.joining(", "));
	}
	private boolean tryPlace(RoomInfo i, DoorGap igap, RoomLayout olayout, DoorGap ogap, double tlx, double tly) {
		if(canPlaceRoom(olayout, tlx, tly)) {
			RoomInfo o = RoomInfo.re(olayout, tlx, tly);
//			System.out.printf("\t\tplacing (%.1f, %.1f)%n", o.tlx() / 480, o.tly() / 480);
			usedGaps(o).add(ogap); //adds o to usedGaps map if not already present.
			usedGaps(i).add(igap);
			if(!allGapsUsed(o))
				q.add(o);
			if(allGapsUsed(i))
				q.remove();
			return true;
		}
		return false;
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
			if(/*main diagonal:*/ri.lineIntersects(tlx, tly, trx, lly) || /*top:*/ri.lineIntersects(tlx, tly, trx, tly)
				|| /*bottom:*/ri.lineIntersects(tlx, lly, trx, lly) || /*left:*/ri.lineIntersects(tlx, tly, tlx, lly)
				|| /*right:*/ri.lineIntersects(trx, tly, trx, lly))
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
	
	private int gapsRemaining(RoomInfo info) {
		return info.layout().gapCount() - usedGaps(info).size();
	}
	
	private boolean allGapsUsed(RoomInfo info) {
		return gapsRemaining(info) == 0;
	}
	
}