package floors;

import java.util.*;

import hallways.HallwayInfo;
import javafx.geometry.Side;
import rooms.RoomInfo;
import rooms.RoomLayout;
import rooms.gaps.DoorGap;
import rooms.gaps.DoorGapCollection;
import rooms.gaps.HorizontalGap;
import rooms.gaps.VerticalGap;
import utils.*;

public final class FloorPlanBuilder {

	private static final double DEFAULT_HALLWAY_WALL_WIDTH = 10;
	
	private static Iterable<DoorGap> randomOrder(DoorGapCollection<DoorGap> gaps) {
		ArrayList<DoorGap> a = new ArrayList<>(gaps.size());
		for(DoorGap g : gaps)
			a.add(g);
		Collections.shuffle(a);
		return a;
	}
	
	private final List<RoomLayout> layoutChoices;
	private final Set<RoomLayout> usedLayoutsThisPass;
	private final int desiredRoomCount;
	private final double hallwayLength, hallwayWallWidth;
	
	public FloorPlanBuilder(List<RoomLayout> layoutChoices, int desiredRoomCount, double hallwayLength) {
		this(layoutChoices, desiredRoomCount, hallwayLength, DEFAULT_HALLWAY_WALL_WIDTH);
	}
	
	public FloorPlanBuilder(List<RoomLayout> layoutChoices, int desiredRoomCount, double hallwayLength, 
			double hallwayWallWidth) {
		this.layoutChoices = layoutChoices;
		usedLayoutsThisPass = new HashSet<>();
		this.desiredRoomCount = desiredRoomCount;
		this.hallwayLength = hallwayLength;
		this.hallwayWallWidth = hallwayWallWidth;
	}

	private Queue<RoomInfo> q;
	private Map<RoomInfo, Set<DoorGap>> usedGaps;
	private Map<RoomInfo, Set<HallwayJoin>> joinMap;
	private List<HallwayInfo> hallways;
	
	public FloorPlan build() {
		q = new ArrayDeque<>();
		usedGaps = new HashMap<>();
		hallways = new ArrayList<>();
		joinMap = new HashMap<>();
		RoomLayout startLayout = getCopyOfRandomLayout();
		RoomInfo startInfo = RoomInfo.re(startLayout, 0, 0);
		q.add(startInfo);
		usedGaps.put(startInfo, new HashSet<>());
		for(int i = 0; i < desiredRoomCount - 1; i++)
			addRoom();
//		System.out.printf("usedGaps: %s%n", usedGaps);
		for(Map.Entry<RoomInfo, Set<DoorGap>> e : usedGaps.entrySet()) {
//			System.out.printf("\te=%s%n", e);
			Set<DoorGap> used = e.getValue();
			RoomInfo r = e.getKey();
//			System.out.printf("\troom gaps: %s%n", r.layout().gaps());
			r.layout().removeGapsIf(g -> !used.contains(g));
//			System.out.printf("\troom gaps remaining: %s%n%n", r.layout().gaps());
		}
		
		return new FloorPlanImpl(startInfo, usedGaps.keySet(), hallways, joinMap);
	}
	
	private void addRoom() {
		System.out.printf("[enter] addRoom, used=%s%n", usedLayoutsThisPass);
		while(!q.isEmpty()) {
			RoomInfo i = q.peek();
			RoomLayout ilayout = i.layout();
			//ASSUMPTIONS: i still has at least one unused gap.
			for(RoomLayout olayoutUncopied : getRandomLayoutPermuation()) {
				RoomLayout olayout = RoomLayout.copyOf(olayoutUncopied);
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
			}
			q.remove();
		}
		System.out.printf("%nEXITING ADDROOM WITHOUT ADDING A ROOM.%n%n");
	}
	
	/** o is the new room.*/
	private boolean tryPlace(RoomInfo i, DoorGap igap, RoomLayout olayout, DoorGap ogap, double tlx, double tly) {
		if(canPlaceRoom(olayout, tlx, tly)) {
			usedLayoutsThisPass.add(olayout);
			RoomInfo o = RoomInfo.re(olayout, tlx, tly);
//			System.out.printf("\t\tplacing (%.1f, %.1f)%n", o.tlx() / 480, o.tly() / 480);
			usedGaps(o).add(ogap); //adds o to usedGaps map if not already present.
			usedGaps(i).add(igap);
			if(!allGapsUsed(o))
				q.add(o);
			if(allGapsUsed(i))
				q.remove();
			HallwayInfo hi = makeHallway(i, igap, o, ogap);
			addJoin(i, hi, o);
			hallways.add(hi);
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
	
	private HallwayInfo makeHallway(RoomInfo from, DoorGap fromGap, RoomInfo to, DoorGap toGap) {
		if(fromGap.isHorizontal()) {
			RoomInfo topRoom;
			HorizontalGap topGap;
			if(from.tly() < to.tly()) {
				topGap = (HorizontalGap) fromGap;
				topRoom = from;
			}
			else {
				topGap = (HorizontalGap) toGap;
				topRoom = to;
			}
			double x = topRoom.tlx() + topRoom.layout().borderThickness() + topGap.leftDist() - hallwayWallWidth;
			double y = topRoom.tly() + topRoom.layout().exteriorHeight();
			return HallwayInfo.of(x, y, topGap.sizeIn(topRoom), hallwayLength, hallwayWallWidth, true);
		}
		else {
			RoomInfo leftRoom;
			VerticalGap leftGap;
			if(from.tlx() < to.tlx()) {
				leftGap = (VerticalGap) fromGap;
				leftRoom = from;
			}
			else {
				leftGap = (VerticalGap) toGap;
				leftRoom = to;
			}
			double x = leftRoom.tlx() + leftRoom.layout().exteriorWidth();
			double y = leftRoom.tly() + leftRoom.layout().borderThickness() + leftGap.topDist() - hallwayWallWidth;
			return HallwayInfo.of(x, y, leftGap.sizeIn(leftRoom), hallwayLength, hallwayWallWidth, false);
		}
	}
	
	private void addJoin(RoomInfo a, HallwayInfo hi, RoomInfo b) {
		HallwayJoin hj = new HallwayJoin(a, hi, b);
		if(!joinMap.containsKey(a))
			joinMap.put(a, new HashSet<>());
		if(!joinMap.containsKey(b))
			joinMap.put(b, new HashSet<>());
		joinMap.get(a).add(hj);
		joinMap.get(b).add(hj);
	}
	
	private Set<DoorGap> usedGaps(RoomInfo info) {
		if(!usedGaps.containsKey(info))
			usedGaps.put(info, new HashSet<>());
		return usedGaps.get(info);
	}
	
	private List<RoomLayout> getRandomLayoutPermuation() {
		if(usedLayoutsThisPass.size() == layoutChoices.size()) {
			usedLayoutsThisPass.clear();
			List<RoomLayout> perm = new ArrayList<>(layoutChoices);
			Collections.shuffle(perm);
			return perm;
		}
		List<RoomLayout> perm = new ArrayList<>(layoutChoices.size());
		for(RoomLayout rl : layoutChoices)
			if(!usedLayoutsThisPass.contains(rl))
				perm.add(rl);
		Collections.shuffle(perm);
		perm.addAll(usedLayoutsThisPass);
		return perm;
	}
	
	private RoomLayout getCopyOfRandomLayout() {
		//If we don't make a copy of it, then when we remove the gaps later, it will remove those gaps
		//from all rooms sharing that layout - not good!
		return RoomLayout.copyOf(layoutChoices.get(RNG.intExclusive(0, layoutChoices.size())));
	}
	
	private int gapsRemaining(RoomInfo info) {
		return info.layout().gapCount() - usedGaps(info).size();
	}
	
	private boolean allGapsUsed(RoomInfo info) {
		return gapsRemaining(info) == 0;
	}
	
}
