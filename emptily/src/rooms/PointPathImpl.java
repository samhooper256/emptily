package rooms;

import java.util.ArrayList;
import java.util.Collections;

import javafx.geometry.Point2D;

public class PointPathImpl extends ArrayList<Point2D> implements PointPath {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6963944550836502175L;
	
	public PointPathImpl() {
		super();
	}
	
	public PointPathImpl(Point2D... points) {
		super(points.length);
		Collections.addAll(this, points);
	}
	
}
