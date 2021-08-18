package base;

import javafx.application.*;
import javafx.stage.Stage;

public class Main extends Application {
	
	private static Stage stage;
	private static MainScene scene;
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		stage = primaryStage;
		scene = new MainScene();
		stage.setScene(scene);
		
		Timer timer = new Timer();
		timer.start();
		
		stage.show();
	}
	
	public static void update(long nsSinceLastFrame) {
		scene.update(nsSinceLastFrame);
	}

	public static Stage stage() {
		return stage;
	}
	
	public static MainScene scene() {
		return scene;
	}
	
}
