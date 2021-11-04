package base.mainmenu;

import base.Main;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class ControlsBox extends HBox {

	public ControlsBox() {
		super(8);
		setAlignment(Pos.CENTER);
		getChildren().add(new ImageView(Main.W_IMAGE));
	}
	
}
