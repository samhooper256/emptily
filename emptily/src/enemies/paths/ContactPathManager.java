package enemies.paths;

import base.Main;
import enemies.*;

/** A {@link PathManager} that moves the {@link Enemy} toward the player until the enemy is in contact with the player. */
public final class ContactPathManager extends PathManager {

	public ContactPathManager(Enemy enemy, double cornerDist) {
		super(enemy, cornerDist);
	}

	@Override
	public void actionIfInSight(long nsSinceLastFrame, boolean couldSeePlayerBeforeThisUpdate) {
		if(onPathAndMustStayOnPath()) {
			runPath(nsSinceLastFrame);
		}
		else {
			discardPath();
			Movement.tickTowards(enemy, Main.content().player().center(), enemy.maxVelocity(), nsSinceLastFrame);
		}
	}
	
}
