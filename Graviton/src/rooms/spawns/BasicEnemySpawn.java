package rooms.spawns;

import base.BasicEnemy;
import base.Enemy;

public record BasicEnemySpawn(double relX, double relY) implements EnemySpawn {

	@Override
	public Enemy get() {
		return new BasicEnemy();
	}

}
