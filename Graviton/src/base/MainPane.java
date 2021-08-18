package base;

import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class MainPane extends StackPane {

	private final VBox vBox;
	private final Label label;
	
	private final Pane pane;
	private final MovingBlock block;
	
	MainPane() {
		label = new Label("Time:");
		vBox = new VBox(label);
		
		pane = new Pane();
		block = new MovingBlock();
		
		pane.getChildren().add(block);
		block.setLayoutX(100);
		block.setLayoutX(20);
		block.setWidth(10);
		block.setHeight(10);
		
		
		getChildren().addAll(/*vBox,*/ pane);
	}

	 void keyPressed(KeyCode code) {
		switch(code) {
			case W -> block.setAccel(0, -10);
			case A -> block.setAccel(-10, 0);
			case S -> block.setAccel(0, 10);
			case D -> block.setAccel(10, 0);
			default -> { return; }
		}
	}
	
	void update(long nsSinceLastFrame) {
		label.setText(String.format("Time: %fs", nsSinceLastFrame / 1e9));
		block.update(nsSinceLastFrame);
	}
	
}
