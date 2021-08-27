package base;

import java.util.*;

import fxutils.Nodes;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class MainPane extends StackPane {

	private final Pane pane;
	private final Player player;
	
	private final Collection<Platform> platforms;
	
	MainPane() {
		
		pane = new Pane();
		player = new Player();
		
		Platform platform2 = new Platform(120, 120);
		Platform top = new Platform(400, 20);
		Platform left = new Platform(20, 400);
		Platform right = new Platform(20, 400);
		Platform bottom = new Platform(400, 20);
		
		Nodes.setLayout(top, 0, 0);
		Nodes.setLayout(left, 0, 0);
		Nodes.setLayout(right, 400, 0);
		Nodes.setLayout(bottom, 0, 300);
		
		Nodes.setLayout(platform2, 200, 100);
		
		platforms = new ArrayList<>();
		Collections.addAll(platforms, platform2, top, right, bottom, left);
		
		pane.getChildren().addAll(player);
		for(Platform p : platforms)
			pane.getChildren().add(p);
		
		player.setLayoutX(100);
		player.setLayoutY(100);
		
		getChildren().addAll(pane);
	}

	 public void keyPressed(KeyCode code) {
		 if(KeyInput.isModeCode(code))
			 player.setMode(KeyInput.modeFor(code));
		 if(code == KeyCode.SPACE) {
			 update(1_000_000_000);
		 }
	}
	
	public void update(long nsSinceLastFrame) {
		player.update(nsSinceLastFrame);
	}

	public Player player() {
		return player;
	}
	
	public Collection<Platform> platforms() {
		return platforms;
	}
	
}
