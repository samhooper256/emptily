package base;

import javafx.scene.shape.Circle;

public class LineBullet extends Circle implements DelayUpdatable {
	
	private static double VELOCITY = 300;
	
	//in pixels per second
	private double xvel, yvel;
	
	public LineBullet(double startX, double startY, double angdeg) {
		super(4);
		setX(startX);
		setY(startY);
		double angrad = Math.toRadians(angdeg);
		xvel = Math.cos(angrad) * VELOCITY;
		yvel = Math.sin(angrad) * VELOCITY;
		
	}

	private void setX(double x) {
		setLayoutX(x);
	}

	private void setY(double y) {
		setLayoutY(y);
	}
	
	public double x() {
		return getLayoutX();
	}
	
	public double y() {
		return getLayoutY();
	}

	@Override
	public void update(long nsSinceLastFrame) {
		double sec = nsSinceLastFrame / 1e9;
		setLayoutX(x() + sec * xvel);
		setLayoutY(y() + sec * yvel);
	}
	
}
