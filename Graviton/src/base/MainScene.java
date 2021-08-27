package base;

import javafx.scene.*;
import javafx.scene.input.KeyEvent;

public class MainScene extends Scene {
	
	private static final double DEFAULT_WIDTH = 640, DEFAULT_HEIGHT = DEFAULT_WIDTH * 9 / 16;
	
	private final MainPane root;
	private final Camera camera;
	
	public MainScene() {
		this(new MainPane(), DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}
	
	private MainScene(MainPane root, double width, double height) {
		super(root, width, height);
		this.root = root;
		setOnKeyPressed(this::keyEvent);
		camera = new PerspectiveCamera();
		setCamera(camera);
		camera.translateXProperty().bind(pane().player().layoutXProperty().subtract(widthProperty().divide(2)));
		camera.translateYProperty().bind(pane().player().layoutYProperty().subtract(heightProperty().divide(2)));
	}
	
	public MainPane pane() {
		return root;
	}
	
	private void keyEvent(KeyEvent eh) {
		root.keyPressed(eh.getCode());
	}
	
	public void update(long nsSinceLastFrame) {
		root.update(nsSinceLastFrame);
	}
	
}
