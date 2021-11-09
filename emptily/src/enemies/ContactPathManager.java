package enemies;

import base.Main;

/** A {@link PathManager} that moves the {@link Enemy} toward the player until the enemy is in contact with the player. */
final class ContactPathManager extends PathManager {

	public ContactPathManager(Enemy enemy, double cornerDist) {
		super(enemy, cornerDist);
	}

	@Override
	public void actionIfInSight(long nsSinceLastFrame) {
		if(onPathAndCannotRefresh()) {
			runPath(nsSinceLastFrame);
		}
		else {
			discardPath();
			Movement.tickTowards(enemy, Main.content().player().center(), enemy.maxVelocity(), nsSinceLastFrame);
		}
	}
	
}
