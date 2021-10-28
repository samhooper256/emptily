package rooms.spawns;

import enemies.BasicEnemy;
import enemies.Enemy;

public record BasicEnemySpawn(double relX, double relY) implements EnemySpawn {

	public static BasicEnemySpawn centered(double centerX, double centerY) {
		return new BasicEnemySpawn(centerX - BasicEnemy.SIZE / 2, centerY - BasicEnemy.SIZE / 2);
	}
	
	@Override
	public Enemy get() {
		return new BasicEnemy();
	}

}
