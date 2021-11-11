package enemies;

import base.Main;
import base.game.content.MainContent;
import base.game.effects.*;
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
			die();
	}
	
	/** An action to be run when this {@link Enemy} dies. */
	@Override
	public void die() {
		delete();
		Main.content().addBurst(new SimpleBurst(Color.RED, 20), centerX(), centerY());
	}
	
	/** Requests for this enemy to be removed from {@link MainContent}. */
	protected void delete() {
		Main.content().requestRemove(this);
	}
	
	protected void addBurstCentered(Burst burst) {
		Main.content().addBurst(burst, centerX(), centerY());
	}
	
	
}
