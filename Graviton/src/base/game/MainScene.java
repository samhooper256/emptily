package base.game;

import base.DelayUpdatable;
import base.Main;
import javafx.beans.binding.DoubleBinding;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.input.*;

public class MainScene extends SubScene implements DelayUpdatable {
	
	private final MainPane root;
	private final PerspectiveCamera camera;
	private final DoubleBinding cameraX, cameraY;
	
	public MainScene() {
		this(new MainPane(), 0, 0);
	}
	
	private MainScene(MainPane root, double width, double height) {
		super(root, width, height);
		this.root = root;
		setOnKeyPressed(this::keyPressed);
		setOnMouseClicked(this::mouseEvent);
		setOnScroll(this::scrollEvent);
		camera = new PerspectiveCamera();
		setCamera(camera);
		cameraX = pane().content().player().layoutXProperty().subtract(widthProperty().divide(2));
		cameraY = pane().content().player().layoutYProperty().subtract(heightProperty().divide(2));
		camera.translateXProperty().bind(cameraX);
		camera.translateYProperty().bind(cameraY);
	}
	
	public MainPane pane() {
		return root;
	}
	
	public void keyPressed(KeyEvent ke) {
		root.keyPressed(ke.getCode());
	}
	
	private void mouseEvent(MouseEvent me) {
		Point2D fromRoot = root.sceneToLocal(me.getSceneX(), me.getSceneY());
		Point2D rootTranslated = new Point2D(fromRoot.getX() + camera.getTranslateX(), fromRoot.getY() + camera.getTranslateY());
		if(me.getButton() == MouseButton.PRIMARY) {
			pane().leftClicked(rootTranslated);
		}
	}
	
	private void scrollEvent(ScrollEvent se) {
		camera.setTranslateZ(camera.getTranslateZ() + se.getDeltaY());
	}
	
	@Override
	public void update(long nsSinceLastFrame) {
		root.update(nsSinceLastFrame);
	}
	
	public PerspectiveCamera camera() {
		return camera;
	}
	
	void stoppedFighting() {
		
	}

	/** @param levelIndex the index of the level that was just completed. */
	void levelComplete(int levelIndex) {
		Main.outerScene().levelComplete(levelIndex);
	}
	
}
