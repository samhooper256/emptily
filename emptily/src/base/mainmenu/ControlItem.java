package base.mainmenu;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.*;
import javafx.scene.layout.VBox;

public class ControlItem extends VBox {

	private static final String LABEL_CSS = "control-item-label";
	
	public ControlItem(Image image, String text) {
		super(10);
		setAlignment(Pos.CENTER);
		Label label = new Label(text);
		label.getStyleClass().add(LABEL_CSS);
		getChildren().addAll(new ImageView(image), label);
	}
}
