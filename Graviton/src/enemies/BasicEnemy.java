package enemies;

import base.DelayUpdatable;
import base.Main;
import base.game.content.Intersections;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import rooms.PointPath;
import rooms.RoomInfo;
import rooms.RoomLayout;
import rooms.VisibilityGraph;

public class BasicEnemy extends Rectangle implements HittableEnemy, DelayUpdatable {

	public static final double SIZE = 20;
	
	private static final double DEFAULT_HEALTH = 3;
	private static final double CORNER_DIST = SIZE * Math.sqrt(2) / 2 + 1;
	private static final double POINT_PATH_THRESHOLD = 0.8;
	/** in pixels per second. */
	private static final double VELOCITY = 50;
	private static final long MIN_TIME_ON_PATH = 200_000_000;
	private double xvel, yvel, health;
	
	public BasicEnemy() {
		super(SIZE, SIZE);
		setFill(Color.RED);
		health = DEFAULT_HEALTH;
	}

	PointPath path = null;
	int pathIndex = 0;
	RoomInfo info;
	RoomLayout layout;
	Point2D enemy, player;
	boolean canSeePlayer;
	long nsSinceLastPath = 0;
	
	@Override
	public void update(long nsSinceLastFrame) {
		nsSinceLastPath += nsSinceLastFrame;
		info = Main.content().currentRoom();
		layout = info.layout();
		enemy = center();
		player = Main.content().player().center();
		boolean oldCanSeePlayer = canSeePlayer;
		boolean newCanSeePlayer = canSeePlayer();
		canSeePlayer = newCanSeePlayer;
		if(Intersections.intersectsPlayer(this)) {
			discardPath();
			return;
		}
		if(newCanSeePlayer) {
			if(mustStayOnPath()) {
				runPath(nsSinceLastFrame);
			}
			else {
				discardPath();
				tickTowards(player, nsSinceLastFrame);
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
		while(pathIndex < path.size() && center().distance(path.get(pathIndex)) <= POINT_PATH_THRESHOLD)
			pathIndex++;
		if(isOnUnfinishedPath()) {
			Point2D atIndex = path.get(pathIndex);
			tickTowards(atIndex, nsSinceLastFrame);
			if(center().distance(atIndex) <= POINT_PATH_THRESHOLD)
				pathIndex++;
		}
	}

	private boolean canSeePlayer() {
		return layout.intervisible(enemy.getX() - info.tlx(), enemy.getY() - info.tly(),
				player.getX() - info.tlx(), player.getY() - info.tly());
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
	
	private void createNewPath() {
		VisibilityGraph graph = info.visibilityGraph(CORNER_DIST);
		path = graph.path(enemy.subtract(info.tlx(), info.tly()), player.subtract(info.tlx(), info.tly()))
				.shifted(info.tlx(), info.tly());
		pathIndex = 0;
		nsSinceLastPath = 0;
	}
	
	private void tickTowards(Point2D dest, long nsSinceLastFrame) {
		double sec = nsSinceLastFrame / 1e9;
		double xdist = dest.getX() - enemy.getX();
		double ydist = dest.getY() - enemy.getY();
		double angle = Math.atan2(ydist, xdist);
		xvel = VELOCITY * Math.cos(angle);
		yvel = VELOCITY * Math.sin(angle);
		double oldX = x(), oldY = y();
		boolean canX = true, canY = true;
		setLayoutX(oldX + xvel * sec);
		if(Intersections.intersectsAnyPlatformsOrDoors(this))
			canX = false;
		setLayoutX(oldX);
		setLayoutY(y() + yvel * sec);
		if(Intersections.intersectsAnyPlatformsOrDoors(this))
			canY = false;
		setLayoutY(oldY);
		if(!canX && !canY) {
			xvel = 0;
			yvel = 0;
		}
		else if(canX && !canY) {
			xvel = xvel > 0 ? VELOCITY : -VELOCITY;
			yvel = 0;
		}
		else if(!canX && canY) {
			xvel = 0;
			yvel = yvel > 0 ? VELOCITY : -VELOCITY;
		}
		setLayoutX(x() + xvel * sec);
		setLayoutY(y() + yvel * sec);
	}
	
	public Point2D center() {
		return new Point2D(x() + SIZE / 2, y() + SIZE / 2);
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

	private void onDeath() {
		Main.content().requestRemove(this);
		Main.content().addBurst(new CircleBurst(Color.RED, 20), x(), y());
	}
	
}
