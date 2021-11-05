package base.mainmenu;

import base.Main;
import javafx.animation.*;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

public class ControlsBox extends HBox {

	private static final Duration INTRO_DURATION = Duration.millis(600); 
	
	private final Timeline intro;
	
	public ControlsBox() {
		super(40);
		setAlignment(Pos.CENTER);
		ControlItem up = new ControlItem(Main.W_IMAGE, "Up");
		ControlItem left = new ControlItem(Main.A_IMAGE, "Left");
		ControlItem down = new ControlItem(Main.S_IMAGE, "Down");
		ControlItem right = new ControlItem(Main.D_IMAGE, "Right");
		ControlItem shoot = new ControlItem(Main.CLICK_IMAGE, "Shoot");
		ControlItem zoom = new ControlItem(Main.CTRL_IMAGE, "Zoom");
		ControlItem[] items = {up, left, down, right, shoot, zoom};
		getChildren().addAll(up, left, down, right, shoot, zoom);
		intro = new Timeline();
		Duration div = INTRO_DURATION.divide(items.length + 1);
		for(int i = 0; i < items.length; i++) {
			items[i].setOpacity(0);
			Duration start = div.multiply(i);
			Duration end = start.add(div.multiply(2));
			intro.getKeyFrames().add(new KeyFrame(start, new KeyValue(items[i].opacityProperty(), 0),
					new KeyValue(items[i].translateYProperty(), 10, Interpolator.EASE_OUT)));
			intro.getKeyFrames().add(new KeyFrame(end, new KeyValue(items[i].opacityProperty(), 1),
					new KeyValue(items[i].translateYProperty(), 0)));
		}
	}

	public void animateIn() {
		intro.playFromStart();
	}
	
	public void reset() {
		for(Node child : getChildren())
			child.setOpacity(0);
	}
	
}
