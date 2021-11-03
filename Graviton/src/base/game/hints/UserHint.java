package base.game.hints;

import fxutils.Nodes;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public abstract class UserHint extends StackPane {
	
	private Pane over;
	
	public UserHint() {
		super();
		setVisible(false);
	}
	
	
	public void animateOn(Pane p, double layoutX, double layoutY) {
		over = p;
		Nodes.setLayout(this, layoutX, layoutY);
		setVisible(true);
		over.getChildren().add(this);
	}
	
	public void animateOff() {
		if(isVisible()) {
			over.getChildren().remove(this);
			over = null;
			setVisible(false);
		}
	}
	
}
