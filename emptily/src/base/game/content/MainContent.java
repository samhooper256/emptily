package base.game.content;

import static base.game.content.Utils.*;

import java.util.*;
import java.util.stream.Stream;

import base.*;
import base.game.*;
import base.game.effects.*;
import enemies.*;
import floors.*;
import hallways.*;
import javafx.geometry.*;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.shape.*;
import rooms.*;
import rooms.gaps.*;
import rooms.spawns.EnemySpawn;

public class MainContent extends Pane implements DelayUpdatable {

	/** The room that fully encloses the player, or {@code null} if there is no such room. */
	private RoomInfo currentRoom;
	private FloorPlan floorPlan;
	
	private final Player player;
	private final HintManager hintManager;
	private final Collection<Platform> platforms;
	private final Set<Enemy> enemies;
	private final Map<RoomInfo, Collection<Door>> doorMap;
	private final Map<RoomInfo, Collection<Platform>> roomMap; //every key in this map is a shown room.
	private final Set<HallwayInfo> shownHallways;
	private final Set<Node> removeRequests, addRequests;
	private final List<Runnable> endPulseRequests;
	
	public MainContent() {
		player = new Player();
		hintManager = new HintManager(this);
		platforms = new ArrayList<>();
		enemies = new HashSet<>();
		doorMap = new HashMap<>();
		roomMap = new HashMap<>();
		shownHallways = new HashSet<>();
		removeRequests = new HashSet<>();
		addRequests = new HashSet<>();
		endPulseRequests = new ArrayList<>();
	}
	
	public void startLevel(int levelIndex) {
		clearAll();
		Floor floor = Floor.ORDER.get(levelIndex);
		floorPlan = new FloorPlanBuilder(floor.layouts(), floor.suggestedRoomCount(), 180).build();
		currentRoom = floorPlan.startingRoom();
		showRoom(currentRoom, false);
		showAdjacentHallways(currentRoom);
		for(RoomInfo ri : floorPlan.rooms())
			if(ri != currentRoom)
				showRoom(ri, true);
		buildDoors();
		getChildren().add(player);
		getChildren().addAll(player.introNodes());
		player.resetTo(currentRoom.tlx() + currentRoom.layout().playerSpawnX() - player.width() / 2,
				currentRoom.tly() + currentRoom.layout().playerSpawnY() - player.height() / 2);
	}
	
	public void clearAll() {
		getChildren().clear();
		platforms.clear();
		enemies.clear();
		doorMap.clear();
		roomMap.clear();
		shownHallways.clear();
		addRequests.clear();
		removeRequests.clear();
	}
	
	public void addPlatforms(Collection<? extends Platform> platforms) {
		addPlatforms(platforms, false);
	}
	
	public void addPlatforms(Collection<? extends Platform> platforms, boolean bordered) {
		for(Platform p : platforms)
			p.setBordered(bordered);
		getChildren().addAll(platforms);
		this.platforms.addAll(platforms);
	}

	public Collection<Platform> platforms() {
		return platforms;
	}
	
	public void showRoom(RoomInfo info, boolean bordered) {
		if(!shownRooms().contains(info)) {
			Collection<Platform> platforms = showLayoutAtCoordinates(info.layout(), info.tlx(), info.tly(), bordered);
			roomMap.put(info, platforms);
		}
	}
	
	private Collection<Platform> showLayoutAtCoordinates(RoomLayout layout, double tlx, double tly, boolean bordered) {
		Collection<Platform> roomPlatforms = Utils.getRoomPlatforms(layout, tlx, tly);
		addPlatforms(roomPlatforms, bordered);
		return roomPlatforms;
	}
	
	public void showAdjacentHallways(RoomInfo ri) {
		for(HallwayInfo exit : floorPlan.exits(ri))
			showHallway(exit);
	}
	
	private void showHallway(HallwayInfo hi) {
		if(shownHallways.contains(hi))
			return;
		shownHallways.add(hi);
		addPlatforms(Utils.getHallwayPlatforms(hi, true), false);
	}
	
	private void buildDoors() {
		for(RoomInfo ri : floorPlan.rooms()) {
			Collection<Door> doors = new ArrayList<>();
			for(HorizontalGap dg : ri.layout().topGaps())
				doors.add(createTopDoor(ri, dg));
			for(HorizontalGap dg : ri.layout().bottomGaps())
				doors.add(createBottomDoor(ri, dg));
			for(VerticalGap dg : ri.layout().leftGaps())
				doors.add(createLeftDoor(ri, dg));
			for(VerticalGap dg : ri.layout().rightGaps())
				doors.add(createRightDoor(ri, dg));
			for(Door d : doors)
				getChildren().add((Node) d);
			doorMap.put(ri, doors);
		}
	}
	
	private void addEnemies(Enemy... enemies) {
		for(Enemy e : enemies)
			addEnemy(e);
	}
	
	private void addEnemy(Enemy e) {
		getChildren().add(e.asNode());
		enemies.add(e);
	}
	
	public void addBurst(Burst burst, double x, double y) {
		burst.setLayoutX(x);
		burst.setLayoutY(y);
		addRequests.add(burst);
		burst.startAnimation();
	}
	
	public int enemyCount() {
		return enemies.size();
	}
	
	/** Assumes the given room is locked. */
	public void unlock(RoomInfo ri) {
		for(Door d : doorMap.get(ri))
			d.open();
	}
	
	public RoomInfo currentRoom() {
		return currentRoom;
	}
	
	public void keyPressed(KeyCode code) {
		if(KeyInput.isModeCode(code))
			player.setMode(KeyInput.modeFor(code));
		hintManager.keyPressed(code);
	}
	
	public void leftClicked(Point2D point) {
		Point2D playerCenter = player.center();
		double xdist = point.getX() - playerCenter.getX();
		double ydist = point.getY() - playerCenter.getY();
		double angdeg = Math.toDegrees(Math.atan2(ydist, xdist));
		getChildren().add(new LineBullet(playerCenter.getX(), playerCenter.getY(), angdeg));
	}
	
	@Override
	public void update(long nsSinceLastFrame) {
		//we don't update the player or current room here, since that's done by MainPane.
		for(Node n : getChildren())
			if(n != player && n instanceof DelayUpdatable)
				((DelayUpdatable) n).update(nsSinceLastFrame);

		hintManager.update(nsSinceLastFrame);
		
		for(Node n : removeRequests) {
			boolean result = getChildren().remove(n);
			if(result && n instanceof Enemy)
				enemies.remove((Enemy) n);
		}
		removeRequests.clear();
		
		for(Node n : addRequests) {
			if(n instanceof Enemy)
				addEnemy((Enemy) n);
			else
				getChildren().add(n);
		}
		addRequests.clear();
		
		for(Runnable r : endPulseRequests)
			r.run();
		endPulseRequests.clear();
		
	}
	
	public void updateCurrentRoom() {
		currentRoom = Intersections.getRoomEnclosingPlayer();
	}

	/** Requests to removes the given {@link Node} from the {@link #content()}'s children at the
	 * end of this {@link #update(long) update} pulse.*/
	public boolean requestRemove(Node node) {
		return removeRequests.add(node);
	}

	public boolean requestAdd(Node node) {
		return addRequests.add(node);
	}
	
	/** Performs the given action at the end of the current {@link #update(long)} pulse. These actions are performed after
	 * any {@link #requestAdd(Node) add requests}. */
	public boolean requestEnd(Runnable r) {
		return endPulseRequests.add(r);
	}
	
	public FloorPlan floorPlan() {
		return floorPlan;
	}
	
	/** Assumes that the given coordinates are IN THE COORDINATE SPACE OF THIS MAINPANE.*/
	public boolean intervisible(Point2D a, Point2D b) {
		Line l = new Line(a.getX(), a.getY(), b.getX(), b.getY());
		l.setVisible(false);
		getChildren().add(l);
		for(Platform p : platforms()) {
			Bounds bip = l.getBoundsInParent();
			if(bip.intersects(p.getBoundsInParent())) {
				getChildren().remove(l);
				return false;
			}
		}
		getChildren().remove(l);
		return true;
	}
	
	private void spawnEnemies(RoomInfo ri) {
		RoomLayout l = ri.layout();
		for(EnemySpawn spawn : l.spawns()) {
			Enemy e = spawn.get();
			e.setLayoutX(ri.tlx() + spawn.relX());
			e.setLayoutY(ri.tly() + spawn.relY());
			addEnemies(e);
		}
	}
	
	public Player player() {
		return player;
	}
	
	/** Locks the current room and spawns enemies in it. */
	public void lockSpawnAndFill() {
		for(Door d : doorMap.get(currentRoom))
			d.close();
		spawnEnemies(currentRoom);
		for(Platform p : roomMap.get(currentRoom))
			p.setBordered(false);
	}

	public Collection<Enemy> enemies() {
		return enemies;
	}
	
	public Stream<Door> doors() {
		return doorMap.values().stream().flatMap(Collection::stream);
	}
	
	public Set<RoomInfo> shownRooms() {
		return roomMap.keySet();
	}
	
}
