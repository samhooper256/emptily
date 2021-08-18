package base;

import javafx.scene.shape.Rectangle;

public class MovingBlock extends Rectangle {
	
	private double yaccel = 5, xaccel = 0; //in pixels per second, down is positive
	
	private double xvel, yvel, x, y;
	
	public MovingBlock() {
		yvel = 0;
		xvel = 0;
	}
	
	public void update(long nsSinceLastFrame) {
		double sec = nsSinceLastFrame / 1e9;
		updateX(sec);
		updateY(sec);
	}

	private void updateX(double sec) {
		double velChange = xaccel * sec;
		xvel += velChange;
		x += xvel;
		setLayoutX(x);
	}
	
	private void updateY(double sec) {
		double velChange = yaccel * sec;
		yvel += velChange;
		y += yvel;
		setLayoutY(y);
	}
	
	public void setAccel(double xaccel, double yaccel) {
		this.xaccel = xaccel;
		this.yaccel = yaccel;
	}
}
