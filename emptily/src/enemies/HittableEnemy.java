package enemies;

public interface HittableEnemy extends Enemy {
	
	double health();
	
	default void takeHit(double damage) {
		//no-op by default
	}
	
}
