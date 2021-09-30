package base;

import java.awt.geom.Line2D;
import java.awt.geom.Line2D.Double;

import rooms.RectangleLayout;

public class Testin {
	
	public static void main(String[] args) {
		Line2D.Double line = new Line2D.Double(0, 0, 10, 10);
		System.out.println(line.intersects(0, 0, 10, 10));
		
		line = new Line2D.Double(-1, -1, 11, 11);
		System.out.println(line.intersects(0, 0, 10, 10));
		
		line = new Line2D.Double(1, 1, 9, 9);
		System.out.println(line.intersects(0, 0, 10, 10));
		
	}

}
