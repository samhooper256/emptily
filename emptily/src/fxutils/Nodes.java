package fxutils;

import javafx.scene.Node;
import javafx.scene.layout.Region;

public final class Nodes {
	
	private Nodes() {
		
	}
	
	public static void setLayout(Node node, double x, double y) {
		node.setLayoutX(x);
		node.setLayoutY(y);
	}
	
	public static void fixSize(Region r, double size) {
		fixSize(r, size, size);
	}
	
	public static void fixSize(Region r, double width, double height) {
		r.setMinWidth(width);
		r.setMaxWidth(width);
		r.setMinHeight(height);
		r.setMaxHeight(height);
	}
	
}
