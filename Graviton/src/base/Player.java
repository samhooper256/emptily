package base;

import fxutils.Backgrounds;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

public class Player extends StackPane {
	
	private static final GravityMode DEFAULT_MODE = GravityMode.DOWN;
	private static final double DEFAULT_WIDTH = 20, DEFAULT_HEIGHT = DEFAULT_WIDTH;

	private final Pane innerPane;
	private final Line top, right, bottom, left;
	
	private GravityMode mode;
	private double xvel, yvel, x, y;
	
	public Player() {
		yvel = 0;
		xvel = 0;
		x = 100;
		y = 100;
		mode = DEFAULT_MODE;
		top = new Line(1, 0, DEFAULT_WIDTH - 1, 0);
		right = new Line(DEFAULT_WIDTH - 1, 1, DEFAULT_WIDTH - 1, DEFAULT_HEIGHT - 2);
		bottom = new Line(1, DEFAULT_HEIGHT - 1, DEFAULT_WIDTH - 1, DEFAULT_HEIGHT - 1);
		left = new Line(0, 1, 0, DEFAULT_HEIGHT - 1);
		innerPane = new Pane(top, right, bottom, left);
		getChildren().add(innerPane);
		setMinWidth(DEFAULT_WIDTH);
		setMinHeight(DEFAULT_HEIGHT);
		setBackground(Backgrounds.of(Color.RED));
	}
	
	public void update(long nsSinceLastFrame) {
		double sec = nsSinceLastFrame / 1e9;
		updateX(sec);
		updateY(sec);
	}

	private void updateX(double sec) {
		double velChange = mode.xAccel() * sec;
		xvel += velChange;
		x += xvel;
		setLayoutX(x);
	}
	
	private void updateY(double sec) {
		double velChange = mode.yAccel() * sec;
		yvel += velChange;
		y += yvel;
		setLayoutY(y);
	}
	
	public void setMode(GravityMode mode) {
		this.mode = mode;
	}
	
}
