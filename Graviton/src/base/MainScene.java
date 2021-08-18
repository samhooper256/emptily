package base;

import javafx.scene.Scene;

public class MainScene extends Scene {
	
	private static final double DEFAULT_WIDTH = 640, DEFAULT_HEIGHT = DEFAULT_WIDTH * 9 / 16;
	private final MainPane root;
	
	public MainScene() {
		this(new MainPane(), DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}
	
	private MainScene(MainPane root, double width, double height) {
		super(root, width, height);
		this.root = root;
		this.setOnKeyPressed(eh -> {
			root.keyPressed(eh.getCode());
		});
	}
	
	void update(long nsSinceLastFrame) {
		root.update(nsSinceLastFrame);
	}
	
}
