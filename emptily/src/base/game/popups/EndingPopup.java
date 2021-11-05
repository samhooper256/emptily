package base.game.popups;

import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;

public abstract class EndingPopup extends StackPane {

	public EndingPopup(double width, double height) {
		setVisible(false);
		setMaxWidth(width);
		setMinWidth(width);
		setMaxHeight(height);
		setMinHeight(height);
		StackPane.setAlignment(this, Pos.CENTER);
	}
	
	public void startOpeningAnimation() {
		setVisible(true);
	}
	
}
