package base;

import fxutils.*;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class Platform extends StackPane {
	
	public static Platform fromCorners(double ulx, double uly, double lrx, double lry) {
		return new Platform(ulx, uly, lrx - ulx, lry - uly);
	}
	
	private final double width, height;
	
	public Platform(double x, double y, double width, double height) {
		this(width, height);
		Nodes.setLayout(this, x, y);
	}
	
	public Platform(double width, double height) {
		setBackground(Backgrounds.of(Color.GREEN));
		this.width = width;
		this.height = height;
		setMinWidth(width);
		setMinHeight(height);
	}
	
}
