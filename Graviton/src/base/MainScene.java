package base;


import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.input.*;

public class MainScene extends SubScene implements DelayUpdatable {
	
	private final MainPane root;
	private final PerspectiveCamera camera;
	
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
		camera.translateXProperty().bind(pane().player().layoutXProperty().subtract(widthProperty().divide(2)));
		camera.translateYProperty().bind(pane().player().layoutYProperty().subtract(heightProperty().divide(2)));
	}
	
	public MainPane pane() {
		return root;
	}
	
	void keyPressed(KeyEvent ke) {
		root.keyPressed(ke.getCode());
	}
	
	private void mouseEvent(MouseEvent me) {
		Point2D coords = root.sceneToLocal(me.getSceneX(), me.getSceneY());
		if(me.getButton() == MouseButton.PRIMARY)
			pane().leftClicked(new Point2D(coords.getX() + camera.getTranslateX(), coords.getY() + camera.getTranslateY()));
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
	
}
