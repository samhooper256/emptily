package rooms.spawns;

import java.util.function.Supplier;

import enemies.Enemy;

public interface EnemySpawn extends Supplier<Enemy> {

	/** The x-coordinate of the top-left of the enemy immediately after it is spawned, in the local coordinate space of
	 * the room in which the enemy is to be spawned. */
	double relX();
	
	/** The y-coordinate of the top-left of the enemy immediately after it is spawned, in the local coordinate space of
	 * the room in which the enemy is to be spawned. */
	double relY();
	
	/** Returns a newly created {@link Enemy} every time.*/
	@Override
	Enemy get();
	
}
