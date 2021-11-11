package rooms.spawns;

import enemies.Spike;

public final class SpikeSpawn extends AbstractEnemySpawn<Spike> {
	
	public SpikeSpawn(double relX, double relY) {
		super(Spike::new, relX, relY);
	}
	
	public static SpikeSpawn centered(double centerX, double centerY) {
		return new SpikeSpawn(centerX - Spike.DEFAULT_SIZE / 2, centerY - Spike.DEFAULT_SIZE / 2);
	}
	
}
