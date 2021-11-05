package base.game.content;

import base.Main;
import base.game.*;
import enemies.*;
import hallways.HallwayInfo;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import rooms.RoomInfo;

public final class Intersections {

	private Intersections() {
		
	}
	
	public static boolean intersectsPlayer(Enemy enemy) {
		Bounds enemyBounds = enemy.getBoundsInParent();
		return enemyBounds.intersects(content().player().getBoundsInParent());
	}
	
	public static boolean intersectsPlayer(HallwayInfo hi) {
		return hi.bounds().intersects(content().player().getBoundsInParent());
	}
	
	/** Returns the {@link HallwayInfo} of the hallway the player currently intersects, or {@code null} if the player
	 * does not intersect any hallway. */
	public static HallwayInfo getHallwayPlayerIntersects() {
		for(HallwayInfo hi : content().floorPlan().hallways())
			if(intersectsPlayer(hi))
				return hi;
		return null;
	}
	
	public static  boolean playerIntersectsHallway() {
		return getHallwayPlayerIntersects() != null;
	}
	
	/** Assumes {@code node} is an immediate child of {@link #content()}. */
	public static boolean intersectsAnyPlatformsOrDoors(Node node) {
		return getPlatformOrDoorIntersecting(node) != null;
	}
	
	/** Assumes {@code node} is an immediate child of {@link #content()}.
	 * Returns {@code null} if no enemy intersected. */
	public static HittableEnemy intersectsHittableEnemy(Node node) {
		Bounds bounds = node.getBoundsInParent();
		for(Enemy n : content().enemies())
			if(n instanceof HittableEnemy)
				if(bounds.intersects(n.getBoundsInParent()))
					return (HittableEnemy) n;
		return null;
	}
	
	public static Enemy getEnemyIntersectingPlayer() {
		for(Enemy e : content().enemies())
			if(intersectsPlayer(e))
				return e;
		return null;
	}
	
	/** Returns the {@link RoomInfo} of the room that <em>completely encloses</em> the player. For a room to completely
	 * enclose the player, both of the following conditions must be true:
	 * <ol>
	 * <li>the player does not {@link #playerIntersectsHallway() intersect a hallway}</li>
	 * <li>The room's interior {@link #getRoomContainingPlayer() contains} the player.</li>
	 * </ol>
	 * Returns {@code null} if no room completely encloses the player. */
	public static RoomInfo getRoomEnclosingPlayer() {
		if(!playerIntersectsHallway())
			return getRoomContainingPlayer();
		return null;
	}
	
	/** Assumes {@code node} is an immediate child of {@link #content()}. */
	public static Node getPlatformOrDoorIntersecting(Node node) {
		Bounds bounds = node.getBoundsInParent();
		for(Platform p : content().platforms())
			if(bounds.intersects(p.getBoundsInParent()))
				return p;
		return (Node) content().doors().filter(d -> d.isClosed() && bounds.intersects(d.getBoundsInParent()))
				.findFirst().orElse(null);
	}
	
	public static boolean interiorContainsPlayer(RoomInfo ri) {
		return ri.interiorBounds().contains(content().player().getBoundsInParent());
	}
	
	/** Returns the {@link RoomInfo} of the room whose {@link RoomInfo#interiorBounds() interior} contains the player,
	 * or {@code null} if no room contains the player. */
	public static RoomInfo getRoomContainingPlayer() {
		for(RoomInfo ri : content().floorPlan().rooms())
			if(interiorContainsPlayer(ri))
				return ri;
		return null;
	}
	
	private static MainContent content() {
		return Main.content();
	}
	
}
