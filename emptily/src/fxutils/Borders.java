package fxutils;

import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public final class Borders {
	
	private Borders() {}
	
	public static Border of(Color color) {
		return new Border(new BorderStroke(color, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT));
	}
	
}