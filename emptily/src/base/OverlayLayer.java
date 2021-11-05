package base;

import javafx.geometry.*;
import javafx.scene.layout.StackPane;

public class OverlayLayer extends StackPane {

	private final NoShootingWhileZoomedOverlay nswzo;
	
	public OverlayLayer() {
		nswzo  = new NoShootingWhileZoomedOverlay();
		setAlignment(Pos.BOTTOM_LEFT);
		setMouseTransparent(true);
		nswzo.setVisible(false);
		getChildren().add(nswzo);
		setPadding(new Insets(12));
	}
	
	/** {@link NoShootingWhileZoomedOverlay}*/
	public void showNSWZO() {
		nswzo.setVisible(true);
	}
	
	/** {@link NoShootingWhileZoomedOverlay}*/
	public void hideNSWZO() {
		nswzo.setVisible(false);
	}
	
}
