package rooms.spawns;

import enemies.BasicEnemy;

public final class BasicEnemySpawn extends AbstractEnemySpawn<BasicEnemy> {
	
	public BasicEnemySpawn(double relX, double relY) {
		super(BasicEnemy::new, relX, relY);
	}
	
	public static BasicEnemySpawn centered(double centerX, double centerY) {
		return new BasicEnemySpawn(centerX - BasicEnemy.SIZE / 2, centerY - BasicEnemy.SIZE / 2);
	}
	
}
