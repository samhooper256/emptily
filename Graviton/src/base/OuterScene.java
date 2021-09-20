package base;

import javafx.scene.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;

public class OuterScene extends Scene implements DelayUpdatable {

	private static final double DEFAULT_WIDTH = 640, DEFAULT_HEIGHT = DEFAULT_WIDTH * 9 / 16;
	
	private final StackPane outerPane;
	private final MainScene mainScene;
	private final HealthBar healthBar;
	
	public OuterScene() {
		this(new StackPane(), DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}
	
	private OuterScene(StackPane root, double width, double height) {
		super(root, width, height);
		this.outerPane = root;
		this.mainScene = new MainScene();
		this.healthBar = new HealthBar();
		setOnKeyPressed(this::keyPressed);
		outerPane.getChildren().addAll(mainScene, healthBar);
		mainScene.widthProperty().bind(widthProperty());
		mainScene.heightProperty().bind(heightProperty());
	}

	private void keyPressed(KeyEvent ke) {
		mainScene.keyPressed(ke);
	}
	
	public MainScene mainScene() {
		return mainScene;
	}

	@Override
	public void update(long nsSinceLastFrame) {
		mainScene().update(nsSinceLastFrame);
	}
	
	public HealthBar healthBar() {
		return healthBar;
	}
	
}
