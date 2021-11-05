package enemies;

import base.*;
import base.game.Player;
import base.game.content.Intersections;
import javafx.geometry.Point2D;
import rooms.*;

final class PathManager implements DelayUpdatable {

	private static final long MIN_TIME_ON_PATH = 200_000_000;
	private static final double POINT_PATH_THRESHOLD = 0.8;
	
	private final Enemy enemy;
	private final double cornerDist;
	
	private PointPath path = null;
	private int pathIndex = 0;
	private boolean canSeePlayer;
	private long nsSinceLastPath = 0;
	private RoomLayout layout;
	private RoomInfo info;
	
	public PathManager(Enemy enemy, double CORNER_DIST) {
		this.enemy = enemy;
		canSeePlayer = false;
		this.cornerDist = CORNER_DIST;
	}
	
	private boolean canSeePlayer() {
		Point2D eCenter = enemy.center();
		Player player = Main.content().player();
		return layout.intervisible(eCenter.getX() - info.tlx(), eCenter.getY() - info.tly(),
				player.centerX() - info.tlx(), player.centerY() - info.tly());
	}
	
	@Override
	public void update(long nsSinceLastFrame) {
		nsSinceLastPath += nsSinceLastFrame;
		info = Main.content().currentRoom();
		layout = info.layout();
		Player player = Main.content().player();
		boolean oldCanSeePlayer = canSeePlayer;
		boolean newCanSeePlayer = canSeePlayer();
		canSeePlayer = newCanSeePlayer;
		if(Intersections.intersectsPlayer(enemy)) {
			discardPath();
			return;
		}
		if(newCanSeePlayer) {
			if(mustStayOnPath()) {
				runPath(nsSinceLastFrame);
			}
			else {
				discardPath();
				Movement.tickTowards(enemy, enemy.center(), player.center(), enemy.maxVelocity(), nsSinceLastFrame);
			}
		}
		else {
			boolean lostSightOfPlayer = oldCanSeePlayer;
			if(!hasPath() || (!mustStayOnPath() && (lostSightOfPlayer || isAtEndOfPath())))
				createNewPath();
			runPath(nsSinceLastFrame);
		}
	}
	
	private boolean mustStayOnPath() {
		return hasPath() && nsSinceLastPath < MIN_TIME_ON_PATH;
	}

	private void runPath(long nsSinceLastFrame) {
		while(pathIndex < path.size() && enemy.center().distance(path.get(pathIndex)) <= POINT_PATH_THRESHOLD)
			pathIndex++;
		if(isOnUnfinishedPath()) {
			Point2D atIndex = path.get(pathIndex);
			Movement.tickTowards(enemy, enemy.center(), atIndex, enemy.maxVelocity(), nsSinceLastFrame);
			if(enemy.center().distance(atIndex) <= POINT_PATH_THRESHOLD)
				pathIndex++;
		}
	}

	private void createNewPath() {
		VisibilityGraph graph = info.visibilityGraph(cornerDist);
		path = graph.path(enemy.center().subtract(info.tlx(), info.tly()),
				Main.content().player().center().subtract(info.tlx(), info.tly())).shifted(info.tlx(), info.tly());
		pathIndex = 0;
		nsSinceLastPath = 0;
	}
	
	private void discardPath() {
		path = null;
	}

	private boolean isAtEndOfPath() {
		return hasPath() && pathIndex == path.size();
	}

	public boolean isOnUnfinishedPath() {
		return hasPath() && !isAtEndOfPath();
	}
	
	private boolean hasPath() {
		return path != null;
	}
	
}
