package enemies.paths;

import base.Main;
import enemies.*;

/** A {@link PathManager} that moves no closer if the {@link Enemy} is within a given distance of the player. */
public class DistancePathManager extends PathManager {

	private final double minDist;
	
	public DistancePathManager(Enemy enemy, double cornerDist, double minPlayerDistance) {
		super(enemy, cornerDist);
		this.minDist = minPlayerDistance;
	}

	@Override
	public void actionIfInSight(long nsSinceLastFrame, boolean couldSeePlayerBeforeThisUpdate) {
		if(distanceToPlayer() > minDist) {
			discardPath();
			Movement.tickTowards(enemy, Main.content().player().center(), enemy.maxVelocity(), nsSinceLastFrame);
		}
	}
	
	public double minDist() {
		return minDist;
	}
	
}
