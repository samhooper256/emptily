package enemies;

import base.game.content.Intersections;
import javafx.geometry.Point2D;

final class Movement {

	private Movement() {
		
	}
	
	public static void tickTowards(Enemy enemy, Point2D enemyCenter, Point2D dest, double maxVelocity,
			long nsSinceLastFrame) {
		double sec = nsSinceLastFrame / 1e9;
		double xdist = dest.getX() - enemyCenter.getX();
		double ydist = dest.getY() - enemyCenter.getY();
		double angle = Math.atan2(ydist, xdist);
		enemy.setxvel(maxVelocity * Math.cos(angle));
		enemy.setyvel(maxVelocity * Math.sin(angle));
		double oldX = enemy.x(), oldY = enemy.y();
		boolean canX = true, canY = true;
		enemy.setLayoutX(oldX + enemy.xvel() * sec);
		if(Intersections.intersectsAnyPlatformsOrDoors(enemy.asNode()))
			canX = false;
		enemy.setLayoutX(oldX);
		enemy.setLayoutY(enemy.y() + enemy.yvel() * sec);
		if(Intersections.intersectsAnyPlatformsOrDoors(enemy.asNode()))
			canY = false;
		enemy.setLayoutY(oldY);
		if(!canX && !canY) {
			enemy.setxvel(0);
			enemy.setyvel(0);
		}
		else if(canX && !canY) {
			enemy.setxvel(enemy.xvel() > 0 ? maxVelocity : -maxVelocity);
			enemy.setyvel(0);
		}
		else if(!canX && canY) {
			enemy.setxvel(0);
			enemy.setyvel(enemy.yvel() > 0 ? maxVelocity : -maxVelocity);
		}
		enemy.setLayoutX(enemy.x() + enemy.xvel() * sec);
		enemy.setLayoutY(enemy.y() + enemy.yvel() * sec);
	}
	
}
