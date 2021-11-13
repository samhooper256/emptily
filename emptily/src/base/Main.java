package base;

import java.io.InputStream;
import java.util.Optional;

import base.game.*;
import base.game.content.MainContent;
import fxutils.Images;
import javafx.application.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/*
 * TODO ADD MY NAME TO APP
 */
public class Main extends Application {
	
	private static final int
			HEALTH_ICON_SIZE = 32,
			MOVEMENT_KEY_ICON_SIZE = 32,
			CTRL_ICON_WIDTH = 48,
			CTRL_ICON_HEIGHT = 32;
			
	public static final Image
			FULL_HEALTH = Images.get("fullhealth.png", HEALTH_ICON_SIZE, HEALTH_ICON_SIZE, false, true),
			EMPTY_HEALTH = Images.get("emptyhealth.png", HEALTH_ICON_SIZE, HEALTH_ICON_SIZE, false, true),
			W_IMAGE = Images.get("W.png", MOVEMENT_KEY_ICON_SIZE, MOVEMENT_KEY_ICON_SIZE, false, true),
			A_IMAGE = Images.get("A.png", MOVEMENT_KEY_ICON_SIZE, MOVEMENT_KEY_ICON_SIZE, false, true),
			S_IMAGE = Images.get("S.png", MOVEMENT_KEY_ICON_SIZE, MOVEMENT_KEY_ICON_SIZE, false, true),
			D_IMAGE = Images.get("D.png", MOVEMENT_KEY_ICON_SIZE, MOVEMENT_KEY_ICON_SIZE, false, true),
			CLICK_IMAGE = Images.get("click.png", MOVEMENT_KEY_ICON_SIZE, MOVEMENT_KEY_ICON_SIZE, false, true),
			CTRL_IMAGE = Images.get("CTRL.png", CTRL_ICON_WIDTH, CTRL_ICON_HEIGHT, false, true);
	
	static final String RESOURCES_PREFIX = "/resources/";
	
	private static Stage stage;
	private static OuterScene outerScene;
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		stage = primaryStage;
		outerScene = new OuterScene();
		stage.setScene(outerScene);
		Timer timer = new Timer();
		timer.start();
		
		stage.setMaximized(true);
		stage.show();
		
		outerScene().mainMenu().animateIn();
	}
	
	public static void update(long nsSinceLastFrame) {
		outerScene.update(nsSinceLastFrame);
	}

	public static Stage stage() {
		return stage;
	}
	
	public static MainScene scene() {
		return outerScene.mainScene();
	}
	
	public static OuterScene outerScene() {
		return outerScene;
	}
	
	public static MainPane pane() {
		return scene().pane();
	}
	
	public static MainContent content() {
		return pane().content();
	}
	
	public static HealthBar healthBar() {
		return outerScene().healthBar();
	}
	
	/**
	 * Produces an {@link Optional} of the {@link InputStream} for a resource in the "resources" folder.
	 * If the resource could not be located, the returned {@code Optional} will be empty. Otherwise, it
	 * will contain the {@code InputStream}.
	 * @param filename the name of the resource file, including its file extension. Must be in the "resources" folder.
	 * @return an {@link Optional} possibly containing the {@link InputStream}.
	 */
	public static Optional<InputStream> getOptionalResourceStream(String filename) {
		return Optional.ofNullable(Main.class.getResourceAsStream(RESOURCES_PREFIX + filename));
	}
	
	/**
	 * Produces the {@link InputStream} for a resource in the "resources" folder.
	 * @param filename the name of the file, including its file extension. Must be in the "resources" folder.
	 * @return the {@link InputStream} for the resource indicated by the given filename.
	 * @throws IllegalArgumentException if the file does not exist.
	 */
	public static InputStream getResourceStream(String filename) {
		Optional<InputStream> stream = getOptionalResourceStream(filename);
		if(!stream.isPresent())
			throw new IllegalArgumentException("The resource at " + RESOURCES_PREFIX + filename + " does not exist");
		return stream.get();
	}
	
}
