package rooms.spawns;

import enemies.Cannon;

public class CannonSpawn extends AbstractEnemySpawn<Cannon> {

	public CannonSpawn(double relX, double relY) {
		super(Cannon::new, relX, relY);
	}

	public static CannonSpawn centered(double centerX, double centerY) {
		return new CannonSpawn(centerX - Cannon.SIZE / 2, centerY - Cannon.SIZE / 2);
	}
	
}
