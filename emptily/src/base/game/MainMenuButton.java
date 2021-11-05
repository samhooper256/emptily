package base.game;

import base.Main;
import fxutils.Images;
import javafx.scene.control.*;
import javafx.scene.image.*;

public class MainMenuButton extends Button {

	private static final Image IMAGE = Images.get("home.png", 100, 100, true, true);

	public MainMenuButton(double height) {
		setMinHeight(height);
		setMaxHeight(height);
		setGraphic(new ImageView(IMAGE));
		setOnAction(eh -> Main.outerScene().switchToMainMenu());
		setContentDisplay(ContentDisplay.CENTER);
	}
	
	
	
}
