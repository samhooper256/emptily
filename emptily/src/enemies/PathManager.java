package enemies;

import base.*;
import base.game.Player;
import base.game.content.Intersections;
import javafx.geometry.Point2D;
import rooms.*;

abstract class PathManager implements DelayUpdatable {

	private static final long MIN_TIME_ON_PATH = 200_000_000;
	private static final double POINT_PATH_THRESHOLD = 0.8;
	
	protected final Enemy enemy;
	
	private final double cornerDist;
	
	private PointPath path = null;
	private int pathIndex = 0;
	private boolean canSeePlayer;
	private long nsSinceLastPath = 0;
	private RoomLayout layout;
	private RoomInfo info;
	
	public PathManager(Enemy enemy, double cornerDist) {
		this.enemy = enemy;
		canSeePlayer = false;
		this.cornerDist = cornerDist;
	}
	
	protected boolean canSeePlayer() {
		Point2D eCenter = enemy.center();
		Player player = Main.content().player();
		return layout.intervisible(eCenter.getX() - info.tlx(), eCenter.getY() - info.tly(),
				player.centerX() - info.tlx(), player.centerY() - info.tly());
	}
	
	/** Returns {@code true} iff, after the last {@link #update(long)} call, the {@link Enemy} could see the player. */
	public boolean couldSeePlayerAfterLastUpdate() {
		return canSeePlayer;
	}
	
	@Override
	public void update(long nsSinceLastFrame) {
		nsSinceLastPath += nsSinceLastFrame;
		info = Main.content().currentRoom();
		layout = info.layout();
		boolean oldCanSeePlayer = canSeePlayer;
		boolean newCanSeePlayer = canSeePlayer();
		canSeePlayer = newCanSeePlayer;
		if(Intersections.intersectsPlayer(enemy)) {
			discardPath();
		}
		else if(newCanSeePlayer) {
			actionIfInSight(nsSinceLastFrame);
		}
		else {
			boolean lostSightOfPlayer = oldCanSeePlayer;
			if(!hasPath() || (!onPathAndCannotRefresh() && (lostSightOfPlayer || isAtEndOfPath())))
				createNewPath();
			runPath(nsSinceLastFrame);
		}
	}

	/** The action to be run if the player is in sight. */
	public abstract void actionIfInSight(long nsSinceLastFrame);
	
	protected boolean onPathAndCannotRefresh() {
		return hasPath() && nsSinceLastPath < MIN_TIME_ON_PATH;
	}

	protected void runPath(long nsSinceLastFrame) {
		while(pathIndex < path.size() && enemy.center().distance(path.get(pathIndex)) <= POINT_PATH_THRESHOLD)
			pathIndex++;
		if(isOnUnfinishedPath()) {
			Point2D atIndex = path.get(pathIndex);
			Movement.tickTowards(enemy, atIndex, enemy.maxVelocity(), nsSinceLastFrame);
			if(enemy.center().distance(atIndex) <= POINT_PATH_THRESHOLD)
				pathIndex++;
		}
	}

	protected void createNewPath() {
		VisibilityGraph graph = info.visibilityGraph(cornerDist);
		path = graph.path(enemy.center().subtract(info.tlx(), info.tly()),
				Main.content().player().center().subtract(info.tlx(), info.tly())).shifted(info.tlx(), info.tly());
		pathIndex = 0;
		nsSinceLastPath = 0;
	}
	
	protected void discardPath() {
		path = null;
	}

	protected boolean isAtEndOfPath() {
		return hasPath() && pathIndex == path.size();
	}

	public boolean isOnUnfinishedPath() {
		return hasPath() && !isAtEndOfPath();
	}
	
	public boolean hasPath() {
		return path != null;
	}
	
	protected double distanceToPlayer() {
		return enemy.center().distance(Main.content().player().center());
	}
	
	/** Returns the angle, in radians, of the {@link Enemy Enemy's} velocity vector after the most recent
	 * {@link #update(long)} call.*/
	public double angradOfVelocity() {
		return Math.atan2(enemy.yvel(), enemy.xvel());
	}
	
	public double angradToPlayer() {
//		return Math.atan2(enemy.centerY() - Main.content().player().centerY(),
//				enemy.centerX() - Main.content().player().centerX());
		return Math.atan2(Main.content().player().centerY() - enemy.centerY() ,
				Main.content().player().centerX() - enemy.centerX());
	}
}
