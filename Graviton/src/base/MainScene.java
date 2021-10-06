package base;


import javafx.animation.Transition;
import javafx.beans.binding.DoubleBinding;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.input.*;
import javafx.util.Duration;
import rooms.RoomInfo;

public class MainScene extends SubScene implements DelayUpdatable {

	private static final Duration TO_PLAYER_DURATION = Duration.millis(1000), TO_ROOM_DURATION = Duration.millis(1000);
	
	private final class ToPlayerAnimation extends Transition {

		private double x, y;
		
		public ToPlayerAnimation() {
			setOnFinished(eh -> {
				camera.translateXProperty().bind(cameraX);
				camera.translateYProperty().bind(cameraY);
			});
			setCycleDuration(TO_PLAYER_DURATION);
		}

		@Override
		protected void interpolate(double frac) {
			camera.setTranslateX(x + frac * (cameraX.get() - x));
			camera.setTranslateY(y + frac * (cameraY.get() - y));
		}
		
		void startFrom(double x, double y) {
			camera.translateXProperty().unbind();
			camera.translateYProperty().unbind();
//			camera.setTranslateX(x);
//			camera.setTranslateY(y);
			this.x = x;
			this.y = y;
			playFromStart();
		}
		
	}
	
	private final class ToRoomAnimation extends Transition {
		
		private double startX, startY, destX, destY;
		
		ToRoomAnimation() {
			setCycleDuration(TO_ROOM_DURATION);
		}
		
		@Override
		protected void interpolate(double frac) {
			camera.setTranslateX(startX + frac * (destX - getWidth() / 2 - startX));
			camera.setTranslateY(startY + frac * (destY - getHeight() / 2 - startY));
		}
		
		void goTo(RoomInfo ri) {
			goTo(cameraX.get(), cameraY.get(),
					ri.tlx() + ri.layout().exteriorWidth() / 2, ri.tly() + ri.layout().exteriorHeight() / 2);
		}
		
		void goTo(double startX, double startY, double destX, double destY) {
			this.startX = startX;
			this.startY = startY;
			this.destX = destX;
			this.destY = destY;
			playFromStart();
		}
		
	}
	private final MainPane root;
	private final PerspectiveCamera camera;
	private final DoubleBinding cameraX, cameraY;
	private final ToPlayerAnimation toPlayer;
	private final ToRoomAnimation toRoom;
	
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
		toPlayer = new ToPlayerAnimation();
		toRoom = new ToRoomAnimation();
		setCamera(camera);
		cameraX = pane().player().layoutXProperty().subtract(widthProperty().divide(2));
		cameraY = pane().player().layoutYProperty().subtract(heightProperty().divide(2));
		camera.translateXProperty().bind(cameraX);
		camera.translateYProperty().bind(cameraY);
		pane().player().layoutXProperty().addListener((l, ov, nv) -> {
//			System.out.printf("x=%f%n", nv);
//			if(Math.abs(nv.doubleValue() - ov.doubleValue()) > 1) {
//				System.out.printf("\tBIG%n");
//			}
		});
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
			pane().leftClicked(new Point2D(coords.getX() + camera.getTranslateX(),
					coords.getY() + camera.getTranslateY()));
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

	void startedFighting(RoomInfo ri) {
		camera.translateXProperty().unbind();
		camera.translateYProperty().unbind();
		toPlayer.stop();
		toRoom.goTo(ri);
	}
	
	void stoppedFighting() {
		toRoom.stop();
		toPlayer.startFrom(camera.getTranslateX(), camera.getTranslateY());
	}
	
}
