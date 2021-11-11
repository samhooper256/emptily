package enemies.paths;

import base.Main;
import enemies.*;

public final class DistanceSightTimedPathManager extends DistancePathManager {

	private final long minSightTime;
	
	private long sightTime;
	
	public DistanceSightTimedPathManager(Enemy enemy, double cornerDist, double minPlayerDistance, long minSightTime) {
		super(enemy, cornerDist, minPlayerDistance);
		this.minSightTime = minSightTime;
	}
	
	@Override
	public void actionIfInSight(long nsSinceLastFrame, boolean couldSeePlayerBeforeThisUpdate) {
		sightTime += nsSinceLastFrame;
		if(sightTime >= minSightTime && distanceToPlayer() > minDist()) {
			discardPath();
			Movement.tickTowards(enemy, Main.content().player().center(), enemy.maxVelocity(), nsSinceLastFrame);
		}
		else {
			defaultActionIfOutOfSight(nsSinceLastFrame, couldSeePlayerBeforeThisUpdate);
		}
	}

	@Override
	public void actionIfOutOfSight(long nsSinceLastFrame, boolean couldSeePlayerBeforeThisUpdate) {
		sightTime = 0;
		super.actionIfOutOfSight(nsSinceLastFrame, couldSeePlayerBeforeThisUpdate);
	}
	
	/** in nanos. */
	public long sightTime() {
		return sightTime;
	}
	
}
