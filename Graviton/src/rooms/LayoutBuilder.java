package rooms;

import java.util.*;

import rooms.gaps.DoorGap;
import rooms.spawns.*;

public final class LayoutBuilder {

	private double width, height, playerSpawnX, playerSpawnY;
	private RectangleLayout[] rects;
	private DoorGap[] gaps;
	private List<EnemySpawn> spawns;
	
	public LayoutBuilder() {
		
	}
	
	public LayoutBuilder setWidth(double width) {
		this.width = width;
		return this;
	}
	
	public LayoutBuilder setHeight(double height) {
		this.height = height;
		return this;
	}
	
	public LayoutBuilder setSize(double width, double height) {
		setWidth(width);
		setHeight(height);
		return this;
	}
	
	public LayoutBuilder setRects(RectangleLayout... rects) {
		this.rects = rects;
		return this;
	}
	
	public LayoutBuilder setGaps(DoorGap... gaps) {
		this.gaps = gaps;
		return this;
	}
	
	public LayoutBuilder setPlayerSpawn(double x, double y) {
		playerSpawnX = x;
		playerSpawnY = y;
		return this;
	}
	
	public LayoutBuilder addBasicSpawn(double relX, double relY) {
		spawnList().add(new BasicEnemySpawn(relX, relY));
		return this;
	}
	
	public LayoutBuilder addBasicSpawnCentered(double centerX, double centerY) {
		spawnList().add(BasicEnemySpawn.centered(centerX, centerY));
		return this;
	}
	
	private List<EnemySpawn> spawnList() {
		if(spawns == null)
			spawns = new ArrayList<>();
		return spawns;
	}
	
	public RoomLayout build() {
		if(width <= 0 || height <= 0)
			throw new IllegalStateException("Cannot build room with non-positive width or height.");
		if(gaps == null || gaps.length == 0)
			throw new IllegalStateException("DoorGaps have not been configured properly. "
					+ "There must be at least one gap.");
		if(playerSpawnX == 0 || playerSpawnY == 0)
			throw new IllegalStateException("Player spawn not set");
		if(rects == null)
			rects = new RectangleLayout[0];
		if(spawns == null)
			spawns = Collections.emptyList();
		return new RoomLayoutImpl(width, height, playerSpawnX, playerSpawnY, rects, gaps, spawns);
	}
	
}
