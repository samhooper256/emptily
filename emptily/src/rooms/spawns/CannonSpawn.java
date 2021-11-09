package rooms.spawns;

import enemies.Cannon;

public class CannonSpawn extends AbstractEnemySpawn<Cannon> {

	public CannonSpawn(double relX, double relY) {
		super(Cannon::new, relX, relY);
	}

	public static CannonSpawn centered(double centerX, double centerY) {
		return new CannonSpawn(centerX - Cannon.OUTER_SIZE / 2, centerY - Cannon.OUTER_SIZE / 2);
	}
	
}
