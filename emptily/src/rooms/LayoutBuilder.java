package rooms;

import java.util.*;

import rooms.gaps.DoorGap;
import rooms.spawns.*;
import utils.DoubleBiConsumer;

public final class LayoutBuilder {

	private double width, height, playerSpawnX, playerSpawnY;
	private RectangleLayout[] rects;
	private DoorGap[] gaps;
	private List<EnemySpawn> spawns;
	private String name;
	
	public LayoutBuilder() {
		
	}
	
	public LayoutBuilder setName(String name) {
		this.name = name;
		return this;
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
	
	public LayoutBuilder addBasicEnemy(double relX, double relY) {
		return addSpawn(new BasicEnemySpawn(relX, relY));
	}
	
	public LayoutBuilder addBasicEnemyCentered(double centerX, double centerY) {
		return addSpawn(BasicEnemySpawn.centered(centerX, centerY));
	}
	
	public LayoutBuilder addBasicEnemiesCentered(double... xyPairs) {
		return addSpawnsCentered(this::addBasicEnemyCentered, xyPairs);
	}
	
	public LayoutBuilder addCannon(double relX, double relY) {
		return addSpawn(new CannonSpawn(relX, relY));
	}
	
	public LayoutBuilder addCannonCentered(double centerX, double centerY) {
		return addSpawn(CannonSpawn.centered(centerX, centerY));
	}
	
	public LayoutBuilder addCannonsCentered(double... xyPairs) {
		return addSpawnsCentered(this::addCannonCentered, xyPairs);
	}
	
	public LayoutBuilder addSpikeCentered(double centerX, double centerY) {
		return addSpawn(SpikeSpawn.centered(centerX, centerY));
	}
	
	public LayoutBuilder addSpikesCentered(double... xyPairs) {
		return addSpawnsCentered(this::addSpikeCentered, xyPairs);
	}
	
	public LayoutBuilder addSpawn(EnemySpawn spawn) {
		spawnList().add(spawn);
		return this;
	}
	
	private LayoutBuilder addSpawnsCentered(DoubleBiConsumer consumer, double... xyPairs) {
		if(xyPairs.length % 2 != 0)
			throw new IllegalArgumentException("Array must have even length");
		for(int i = 0; i < xyPairs.length; i += 2)
			consumer.accept(xyPairs[i], xyPairs[i + 1]);
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
		return new RoomLayoutImpl(name, width, height, playerSpawnX, playerSpawnY, rects, gaps, spawns);
	}
	
}
