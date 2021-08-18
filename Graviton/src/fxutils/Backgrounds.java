package fxutils;

import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;

public final class Backgrounds {

	private Backgrounds() {
		
	}
	
	public static Background of(Paint paint) {
		return new Background(new BackgroundFill(paint, CornerRadii.EMPTY, Insets.EMPTY));
	}
	
	public static Background of(Image image, double width, double height) {
		return new Background(new BackgroundImage(
				image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
				new BackgroundSize(width, height, false, false, true, true)));
	}
	
}