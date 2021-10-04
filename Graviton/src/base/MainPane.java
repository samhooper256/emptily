package base;

import java.util.*;

import floors.FloorPlan;
import floors.FloorPlanBuilder;
import hallways.HallwayInfo;
import hallways.HallwayLayout;
import javafx.collections.ObservableList;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import rooms.*;
import rooms.gaps.HorizontalGap;
import rooms.gaps.HorizontalGapCollection;
import rooms.gaps.VerticalGap;
import rooms.gaps.VerticalGapCollection;

public class MainPane extends StackPane implements DelayUpdatable {

	private final Pane content;
	private final Player player;
	private final Collection<Platform> platforms;
	private final Set<Enemy> enemies;
	private final Set<Node> removeRequests;
	private final Map<RoomInfo, Collection<Door>> doorMap;
	
	/** The room that fully encloses the player, or {@code null} if there is no such room. */
	private RoomInfo currentRoom;
	private FloorPlan floorPlan;
	
	MainPane() {
		
		content = new Pane();
		player = new Player();
		
		platforms = new ArrayList<>();
		enemies = new HashSet<>();
		
		floorPlan = new FloorPlanBuilder(RoomLayout.all(), 25, 80).build();
		currentRoom = floorPlan.startingRoom();
		
		for(RoomInfo ri : floorPlan.rooms())
			displayRoom(ri);
		
		for(HallwayInfo hi : floorPlan.hallways())
			displayHallway(hi);
		
		doorMap = new HashMap<>();
		initDoors();
		
		removeRequests = new HashSet<>();
		
		content.getChildren().addAll(player);
		
		player.setLayoutX(50);
		player.setLayoutY(300);
		
		getChildren().addAll(content);
		
		for(RoomInfo ri : floorPlan.rooms()) {
			double t = ri.layout().borderThickness();
			Rectangle r = new Rectangle(ri.tlx() + t, ri.tly() + t, ri.layout().interiorWidth(), ri.layout().interiorHeight());
			r.setFill(Color.rgb(0x87, 0xB5, 0xFF, 0.5));
			content.getChildren().add(r);
		}
		
	}
	
	public void displayRoom(RoomInfo info) {
		displayRoom(info.layout(), info.tlx(), info.tly());
	}
	
	public void displayRoom(RoomLayout layout, double tlx, double tly) {
		double t = layout.borderThickness(), iw = layout.interiorWidth(), ih = layout.interiorHeight();
		displayHorizontalSide(iw, t, tlx, tly, layout.topGaps());
		displayHorizontalSide(iw, t, tlx, tly + t + ih, layout.bottomGaps());
		displayVerticalSide(ih, t, tlx, tly, layout.leftGaps());
		displayVerticalSide(ih, t, tlx + t + iw, tly, layout.rightGaps());
		for(RectangleLayout r : layout.interiorRectsUnmodifiable())
			addPlatforms(new Platform(tlx + r.x(), tly + r.y(), r.width(), r.height()));
	}
	
	private void displayHorizontalSide(double iw, double t, double x0, double y,
			HorizontalGapCollection gcoll) {
		double x = x0;
		//assuming 1+ gaps:
		List<HorizontalGap> hgaps = gcoll.sorted(WallDirection.LEFT_TO_RIGHT);
		for(int i = 0; i < hgaps.size(); i++) {
			HorizontalGap gap = hgaps.get(i);
			Platform p = Platform.fromCorners(x, y, x0 + t + gap.leftDist(), y + t);
			addPlatform(p);
			x = x0 + t + iw - gap.rightDist();
		}
		//draw last section, after the last gap:
		addPlatform(Platform.fromCorners(x, y, x0 + iw + 2 * t, y + t));
	}
	
	private void displayVerticalSide(double ih, double t, double x, double y0, VerticalGapCollection vcoll) {
		double y = y0;
		List<VerticalGap> vgaps = vcoll.sorted(WallDirection.TOP_TO_BOTTOM);
		for(int i = 0; i < vgaps.size(); i++) {
			VerticalGap gap = vgaps.get(i);
			Platform p = Platform.fromCorners(x, y, x + t, y0 + t + gap.topDist());
			addPlatform(p);
			y = y0 + t + ih - gap.bottomDist();
		}
		addPlatform(Platform.fromCorners(x, y, x + t, y0 + 2 * t + ih));
	}
	
	private void displayHallway(HallwayInfo hi) {
		HallwayLayout hl = hi.layout();
		double tlx = hi.tlx(), tly = hi.tly();
		if(hl.isVertical()) {
			addPlatform(new Platform(tlx, tly, hl.wallWidth(), hl.length()));
			addPlatform(new Platform(tlx + hl.wallWidth() + hl.girth(), tly, hl.wallWidth(), hl.length()));
		}
		else {
			addPlatform(new Platform(tlx, tly, hl.length(), hl.wallWidth()));
			addPlatform(new Platform(tlx, tly + hl.wallWidth() + hl.girth(), hl.length(), hl.wallWidth()));
		}
	}	
	
	private void initDoors() {
		for(RoomInfo ri : rooms()) {
			Collection<Door> doors = new ArrayList<>();
			for(HorizontalGap dg : ri.layout().topGaps())
				doors.add(createTopDoor(ri, dg));
			for(HorizontalGap dg : ri.layout().bottomGaps())
				doors.add(createBottomDoor(ri, dg));
			for(VerticalGap dg : ri.layout().leftGaps())
				doors.add(createLeftDoor(ri, dg));
			for(VerticalGap dg : ri.layout().rightGaps())
				doors.add(createRightDoor(ri, dg));
			content.getChildren().addAll(doors);
			doorMap.put(ri, doors);
		}
	}
	
	private Door createTopDoor(RoomInfo ri, HorizontalGap dg) {
		return new Door(ri.tlx() + ri.layout().borderThickness() + dg.leftDist(), ri.tly(), dg.sizeIn(ri),
				ri.layout().borderThickness());
	}
	
	private Door createBottomDoor(RoomInfo ri, HorizontalGap dg) {
		return new Door(ri.tlx() + ri.layout().borderThickness() + dg.leftDist(), ri.tly() +
				ri.layout().borderThickness() + ri.layout().interiorHeight(), dg.sizeIn(ri),
				ri.layout().borderThickness());
	}
	
	private Door createLeftDoor(RoomInfo ri, VerticalGap dg) {
		return new Door(ri.tlx(), ri.tly() + ri.layout().borderThickness() + dg.topDist(),
				ri.layout().borderThickness(), dg.sizeIn(ri));
	}
	
	private Door createRightDoor(RoomInfo ri, VerticalGap dg) {
		return new Door(ri.tlx() + ri.layout().borderThickness() + ri.layout().interiorWidth(),
				ri.tly() + ri.layout().borderThickness() + dg.topDist(),
				ri.layout().borderThickness(), dg.sizeIn(ri));
	}
	
	private void addEnemies(Enemy... enemies) {
		for(Enemy e : enemies)
			addEnemy(e);
	}
	
	private void addEnemy(Enemy e) {
		content.getChildren().addAll(e.asNode());
		enemies.add(e);
	}
	
	private void removeAllPlatforms() {
		content.getChildren().removeAll(platforms);
	}
	
	private void addPlatform(Platform platform) {
		content.getChildren().add(platform);
		this.platforms.add(platform);
	}
	
	private void addPlatforms(Platform... platforms) {
		content.getChildren().addAll(platforms);
		Collections.addAll(this.platforms, platforms);
	}	
	
	public void keyPressed(KeyCode code) {
		if(KeyInput.isModeCode(code))
			player.setMode(KeyInput.modeFor(code));
	}
	
	public void leftClicked(Point2D point) {
		Point2D playerCenter = player.center();
		double xdist = point.getX() - playerCenter.getX();
		double ydist = point.getY() - playerCenter.getY();
		double angdeg = Math.toDegrees(Math.atan2(ydist, xdist));
		content.getChildren().add(new LineBullet(playerCenter.getX(), playerCenter.getY(), angdeg));
	}
	
	@Override
	public void update(long nsSinceLastFrame) {
		player.update(nsSinceLastFrame);
		
		RoomInfo oldRoom = currentRoom;
		currentRoom = getRoomEnclosingPlayer();
		if(oldRoom == null && currentRoom != null)
			lockCurrentRoom();
		if(oldRoom != null && currentRoom == null)
			unlock(oldRoom);
		
		for(Node n : content.getChildren())
			if(n != player && n instanceof DelayUpdatable du)
				du.update(nsSinceLastFrame);
		
		ObservableList<Node> contentChildren = content().getChildren();
		for(Node n : removeRequests) {
			boolean result = contentChildren.remove(n);
			if(result && n instanceof Enemy)
				enemies.remove(n);
		}
		removeRequests.clear();
		
	}

	public Player player() {
		return player;
	}
	
	public Collection<Platform> platforms() {
		return platforms;
	}
	
	public Pane content() {
		return content;
	}
	
	/** Assumes {@code node} is an immediate child of {@link #content()}. */
	public boolean intersectsAnyPlatforms(Node node) {
		Bounds bounds = node.getBoundsInParent();
		for(Platform p : platforms)
			if(bounds.intersects(p.getBoundsInParent()))
				return true;
		return false;
	}
	
	/** Assumes {@code node} is an immediate child of {@link #content()}.
	 * Returns {@code null} if no enemy intersected. */
	public HittableEnemy intersectsHittableEnemy(Node node) {
		Bounds bounds = node.getBoundsInParent();
		for(Node n : content().getChildren())
			if(n instanceof HittableEnemy e)
				if(bounds.intersects(e.getBoundsInParent()))
					return e;
		return null;
	}
	
	public Enemy getEnemyIntersectingPlayer() {
		for(Enemy e : enemies)
			if(intersectsPlayer(e))
				return e;
		return null;
	}
	
	public boolean intersectsPlayer(HallwayInfo hi) {
		return hi.bounds().intersects(player.getBoundsInParent());
	}
	
	public boolean interiorContainsPlayer(RoomInfo ri) {
		return ri.interiorBounds().contains(player.getBoundsInParent());
	}
	
	public boolean intersectsPlayer(Enemy enemy) {
		Bounds enemyBounds = enemy.getBoundsInParent();
		return enemyBounds.intersects(player.getBoundsInParent());
	}
	
	/** Requests to removes the given {@link Node} from the {@link #content()}'s children at the
	 * end of this {@link #update(long) update} pulse.*/
	public boolean requestRemove(Node node) {
		return removeRequests.add(node);
	}

	/** Assumes that the given coordinates are IN THE COORDINATE SPACE OF THIS MAINPANE.*/
	public boolean intervisible(Point2D a, Point2D b) {
		Line l = new Line(a.getX(), a.getY(), b.getX(), b.getY());
		l.setVisible(false);
		content.getChildren().add(l);
		for(Platform p : platforms()) {
			Bounds bip = l.getBoundsInParent();
			if(bip.intersects(p.getBoundsInParent())) {
				content.getChildren().remove(l);
				return false;
			}
		}
		content.getChildren().remove(l);
		return true;
	}
	
	public boolean playerIntersectsHallway() {
		return getHallwayPlayerIntersects() != null;
	}
	
	/** Returns the {@link HallwayInfo} of the hallway the player currently intersects, or {@code null} if the player
	 * does not intersect any hallway. */
	public HallwayInfo getHallwayPlayerIntersects() {
		for(HallwayInfo hi : hallways())
			if(intersectsPlayer(hi))
				return hi;
		return null;
	}
	
	/** Returns the {@link RoomInfo} of the room whose {@link RoomInfo#interiorBounds() interior} contains the player,
	 * or {@code null} if no room contains the player. */
	public RoomInfo getRoomContainingPlayer() {
		for(RoomInfo ri : rooms())
			if(interiorContainsPlayer(ri))
				return ri;
		return null;
	}

	/** Returns the {@link RoomInfo} of the room that <em>completely encloses</em> the player. For a room to completely
	 * enclose the player, both of the following conditions must be true:
	 * <ol>
	 * <li>the player does not {@link #playerIntersectsHallway() intersect a hallway}</li>
	 * <li>The room's interior {@link #getRoomContainingPlayer() contains} the player.</li>
	 * </ol>
	 * Returns {@code null} if no room completely encloses the player. */
	public RoomInfo getRoomEnclosingPlayer() {
		if(!playerIntersectsHallway())
			return getRoomContainingPlayer();
		return null;
	}
	
	private void lockCurrentRoom() {
		for(Door d : doorMap.get(currentRoom))
			d.appear();
	}
	
	/** Assumes the given room is locked. */
	public void unlock(RoomInfo ri) {
		
	}
	
	public RoomInfo roomInfo() {
		return currentRoom;
	}
	
	public FloorPlan floorPlan() {
		return floorPlan;
	}
	
	private Collection<HallwayInfo> hallways() {
		return floorPlan().hallways();
	}
	
	private Collection<RoomInfo> rooms() {
		return floorPlan().rooms();
	}
	
}
