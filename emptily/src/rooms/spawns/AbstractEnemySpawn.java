package rooms.spawns;

import java.util.Objects;
import java.util.function.Supplier;

import enemies.*;

abstract class AbstractEnemySpawn<T extends Enemy> implements EnemySpawn {

	private final double relX, relY;
	private final Supplier<T> supplier;
	
	public AbstractEnemySpawn(Supplier<T> supplier, double relX, double relY) {
		this.supplier = supplier;
		this.relX = relX;
		this.relY = relY;
	}
	
	@Override
	public Enemy get() {
		return supplier.get();
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
		AbstractEnemySpawn<?> other = (AbstractEnemySpawn<?>) obj;
		return Double.doubleToLongBits(relX) == Double.doubleToLongBits(other.relX)
				&& Double.doubleToLongBits(relY) == Double.doubleToLongBits(other.relY);
	}
	
}
