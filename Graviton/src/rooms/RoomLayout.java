package rooms;

import java.util.Collection;

public interface RoomLayout {
	
	static Collection<RoomLayout> all() {
		return RoomLayoutHelper.all();
	}
	
	static RoomLayout random() {
		return RoomLayoutHelper.random();
	}
	
	static RoomLayout of(double width, double height, RectangleLayout... rects) {
		return new RoomLayoutImpl(width, height, rects);
	}
	
	default double borderThickness() {
		return 10;
	}
	
	double width();
	
	double height();
	
	Collection<RectangleLayout> rectsUnmodifiable();
	
}
