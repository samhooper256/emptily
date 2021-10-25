package base.game.popups;

import base.Main;
import javafx.animation.FadeTransition;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.*;

public class YouWinPopup extends ContractingPopup {

	private static final double YOU_WIN_FONT_SIZE = 72;
	private static final double MAIN_MENU_FONT_SIZE = 36;
	private static final String UNHOVERED_BUTTON_STYLE = "-fx-background-color: mediumpurple; -fx-background-radius: 12";
	private static final String HOVERED_BUTTON_STYLE = "-fx-background-color: #a584e8; -fx-background-radius: 12";
	
	private final VBox vBox;
	private final Label youWin;
	private final Button mainMenu;
	private final FadeTransition fadeIn = new FadeTransition(OPENING_CONTRACT_DURATION.multiply(0.75), this);
	
	public YouWinPopup() {
		super(400, 300);
		
		youWin = new Label("You Win!");
		youWin.setFont(Font.font("Courier New", FontWeight.BOLD, YOU_WIN_FONT_SIZE));
		youWin.setStyle("-fx-stroke: black; -fx-stroke-width: 2;");
		youWin.setTextFill(Color.GOLD);
		
		mainMenu = new Button("Main Menu");
		mainMenu.setFont(Font.font("Courier New", FontWeight.BOLD, MAIN_MENU_FONT_SIZE));
		mainMenu.setOnAction(eh -> mainMenuAction());
		mainMenu.setStyle(UNHOVERED_BUTTON_STYLE);
		mainMenu.hoverProperty().addListener((obj, ov, isHovered) -> {
			if(isHovered)
				mainMenu.setStyle(HOVERED_BUTTON_STYLE);
			else
				mainMenu.setStyle(UNHOVERED_BUTTON_STYLE);
		});
		
		initFadeIn();
		
		vBox = new VBox(10, youWin, mainMenu);
		vBox.setAlignment(Pos.CENTER);
		
		getChildren().add(vBox);
	}
	
	private void initFadeIn() {
		fadeIn.setFromValue(0);
		fadeIn.setToValue(1);
	}

	@Override
	public void startOpeningAnimation() {
		super.startOpeningAnimation();
		fadeIn.playFromStart();
	}
	
	private void mainMenuAction() {
		Main.outerScene().switchToMainMenu();
	}
	
}
