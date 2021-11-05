package base;

import javafx.scene.control.Label;

public class NoShootingWhileZoomedOverlay extends Label {

	private static final String CSS = "nswz-overlay";
	
	public NoShootingWhileZoomedOverlay() {
		super("You cannot shoot\nwhile zoomed out");
		setWrapText(true);
		getStyleClass().add(CSS);
	}
	
}
