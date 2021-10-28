module relearn {
	
	requires transitive javafx.graphics;
	requires transitive javafx.controls;
	requires transitive java.desktop;
	requires javafx.base;
	
	exports base;
	exports base.game;
	exports base.game.content;
	exports enemies;
	exports rooms;
	exports rooms.gaps;
	exports rooms.spawns;
	exports hallways;
	exports floors;
	
}