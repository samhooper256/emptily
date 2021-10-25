package fxutils;

import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;

public final class Backgrounds {

	private Backgrounds() {
		
	}
	
	public static Background of(Paint paint) {
		return new Background(new BackgroundFill(paint, CornerRadii.EMPTY, Insets.EMPTY));
	}
	
	public static Background rounded(Paint paint, double radius) {
		return new Background(new BackgroundFill(paint, new CornerRadii(radius), Insets.EMPTY));
	}
	
}