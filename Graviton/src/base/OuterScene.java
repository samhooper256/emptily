package base;

import base.game.*;
import base.game.popups.*;
import floors.Floor;
import javafx.scene.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;

public class OuterScene extends Scene implements DelayUpdatable {

	private static final double DEFAULT_WIDTH = 640, DEFAULT_HEIGHT = DEFAULT_WIDTH * 9 / 16;
	
	private final StackPane outerPane;
	private final MainScene mainScene;
	private final MainMenu mainMenu;
	private final HealthBar healthBar;
	private final LevelCompletePopup lcp;
	private final DeathPopup deathPopup;
	private final YouWinPopup youWinPopup;
	
	public OuterScene() {
		this(new StackPane(), DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}
	
	private OuterScene(StackPane root, double width, double height) {
		super(root, width, height);
		this.outerPane = root;
		this.mainScene = new MainScene();
		this.mainMenu = new MainMenu();
		this.healthBar = new HealthBar();
		this.lcp = new LevelCompletePopup();
		this.deathPopup = new DeathPopup();
		this.youWinPopup = new YouWinPopup();
		setOnKeyPressed(this::keyPressed);
		outerPane.getChildren().add(mainMenu);
		mainScene.widthProperty().bind(widthProperty());
		mainScene.heightProperty().bind(heightProperty());
	}

	private void keyPressed(KeyEvent ke) {
		if(isShowingMainScene())
			mainScene.keyPressed(ke);
	}
	
	public MainScene mainScene() {
		return mainScene;
	}

	@Override
	public void update(long nsSinceLastFrame) {
		if(isShowingMainScene() && !isShowingPopup())
			mainScene().update(nsSinceLastFrame);
	}
	
	public HealthBar healthBar() {
		return healthBar;
	}

	/** Called whenever a level is complete.
	 * @param levelIndex the index of the level that was just completed. */
	public void levelComplete(int levelIndex) {
		if(levelIndex == Floor.count() - 1)
			showPopup(youWinPopup);
		else
			showPopup(lcp);
	}
	
	public void die() {
		showPopup(deathPopup);
	}

	public void replay() {
		Main.pane().reset();
		hidePopup();
	}
	
	/** Hides the currently showing popup, if there is one. */
	public void hidePopup() {
		if(isShowingPopup()) {
			EndingPopup popup = (EndingPopup) outerPane.getChildren().get(2);
			outerPane.getChildren().remove(2);
			popup.setVisible(false);
		}
	}
	
	public boolean isShowingMainMenu() {
		return outerPane.getChildren().get(0) == mainMenu;
	}

	/** Returns {@code true} if the game is currently being shown; that is, if the {@link MainMenu} is not showing. */
	public boolean isShowingMainScene() {
		return !isShowingMainMenu();
	}
	
	public void startGame() {
		replay();
		switchToMainScene();
	}
	
	private void switchToMainScene() {
		outerPane.getChildren().clear();
		outerPane.getChildren().add(mainScene);
		outerPane.getChildren().add(healthBar);
	}
	
	public void switchToMainMenu() {
		outerPane.getChildren().clear();
		outerPane.getChildren().add(mainMenu);
	}
	
	private void showPopup(EndingPopup popup) {
		popup.startOpeningAnimation();
		outerPane.getChildren().add(popup);
	}
	
	public boolean isShowingPopup() {
		return outerPane.getChildren().size() >= 3;
	}
	
}
