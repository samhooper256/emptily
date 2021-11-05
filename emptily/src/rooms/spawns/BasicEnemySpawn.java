package rooms.spawns;

import java.util.Objects;

import enemies.BasicEnemy;
import enemies.Enemy;

public final class BasicEnemySpawn implements EnemySpawn {

	private final double relX, relY;
	
	public BasicEnemySpawn(double relX, double relY) {
		this.relX = relX;
		this.relY = relY;
	}
	
	public static BasicEnemySpawn centered(double centerX, double centerY) {
		return new BasicEnemySpawn(centerX - BasicEnemy.SIZE / 2, centerY - BasicEnemy.SIZE / 2);
	}
	
	@Override
	public Enemy get() {
		return new BasicEnemy();
	}

	@Override
	public double relX() {
		return relX;
	}

	@Override
	public double relY() {
		return relY;
	}

	@Override
	public int hashCode() {
		return Objects.hash(relX, relY);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		BasicEnemySpawn other = (BasicEnemySpawn) obj;
		return Double.doubleToLongBits(relX) == Double.doubleToLongBits(other.relX)
				&& Double.doubleToLongBits(relY) == Double.doubleToLongBits(other.relY);
	}
	
}
