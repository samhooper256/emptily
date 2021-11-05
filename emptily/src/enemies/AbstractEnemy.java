package enemies;

import base.Main;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

abstract class AbstractEnemy extends StackPane implements HittableEnemy {

	protected double health;
	
	protected AbstractEnemy(double startingHealth) {
		this.health = startingHealth;
	}
	
	@Override
	public double health() {
		return health;
	}
	
	@Override
	public void takeHit(double damage) {
		health -= damage;
		if(health <= 0)
			onDeath();
	}
	
	/** An action to be run when this {@link Enemy} dies. */
	protected void onDeath() {
		Main.content().requestRemove(this);
		Main.content().addBurst(new CircleBurst(Color.RED, 20), x(), y());
	}
}
