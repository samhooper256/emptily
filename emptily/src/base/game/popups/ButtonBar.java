package base.game.popups;

import base.game.MainMenuButton;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class ButtonBar extends HBox {

	private static final double DEFAULT_HEIGHT = 120;
	
	private final double height;
	private final MainMenuButton home;
	
	public ButtonBar(double height, Button... buttons) {
		super(10);
		this.height = height;
		setMinHeight(height);
		setMaxHeight(height);
		this.setAlignment(Pos.CENTER);
		home = new MainMenuButton(height);
		getChildren().add(home);
		addButtons(buttons);
	}
	
	public ButtonBar(double height) {
		this(DEFAULT_HEIGHT, new Button[0]);
	}
	
	public ButtonBar(Button... buttons) {
		this(DEFAULT_HEIGHT, buttons);
	}
	
	public ButtonBar() {
		this(DEFAULT_HEIGHT);
	}
	
	public MainMenuButton home() {
		return home;
	}
	
	public void addButton(Button button) {
		button.setMinHeight(height);
		button.setMaxHeight(height);
		getChildren().add(button);
	}
	
	public void addButtons(Button... buttons) {
		for(Button b : buttons)
			addButton(b);
	}
	
}
