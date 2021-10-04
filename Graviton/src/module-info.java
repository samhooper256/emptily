module relearn {
	
	requires transitive javafx.graphics;
	requires transitive javafx.controls;
	requires transitive java.desktop;
	
	exports base;
	exports rooms;
	exports rooms.gaps;
	exports rooms.spawns;
	exports hallways;
	
}