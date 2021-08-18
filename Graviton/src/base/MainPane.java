package base;

import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class MainPane extends StackPane {

	private final Pane pane;
	private final Player player;
	
	MainPane() {
		
		pane = new Pane();
		player = new Player();
		
		pane.getChildren().add(player);
		player.setLayoutX(100);
		player.setLayoutY(100);
		
		getChildren().addAll(pane);
	}

	 public void keyPressed(KeyCode code) {
		 if(KeyInput.isModeCode(code))
			 player.setMode(KeyInput.modeFor(code));
	}
	
	public void update(long nsSinceLastFrame) {
		player.update(nsSinceLastFrame);
	}
	
}
