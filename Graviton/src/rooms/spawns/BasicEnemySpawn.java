package rooms.spawns;

import enemies.BasicEnemy;
import enemies.Enemy;

public record BasicEnemySpawn(double relX, double relY) implements EnemySpawn {

	@Override
	public Enemy get() {
		return new BasicEnemy();
	}

}
