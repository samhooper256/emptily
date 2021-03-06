package base.game;

import java.util.*;

import base.*;
import base.game.content.MainContent;
import enemies.Enemy;
import floors.*;
import javafx.geometry.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import rooms.*;

public class MainPane extends StackPane implements DelayUpdatable {

	private final MainContent content;
	private final Set<RoomInfo> clearedRooms;
	
	private volatile boolean isFighting; //not sure if volatile is needed
	private int levelIndex;
	
	MainPane() {
		content = new MainContent();
		
		clearedRooms = new HashSet<>();
		isFighting = false;
		
		getChildren().add(content);
	}
	
	private void startLevel(int levelIndex) {
		clearedRooms.clear();
		this.levelIndex = levelIndex;
		content().startLevel(levelIndex);
		clearedRooms.add(content().currentRoom());
	}
	
	public void startNextLevel() {
		if(levelIndex < Floor.count() - 1) {
			Main.outerScene().hidePopup();
			startLevel(levelIndex + 1);
		}
	}
	
	public void reset() {
		Main.healthBar().reset();
		startLevel(0);
	}

	public void keyPressed(KeyCode code) {
		content().keyPressed(code);
	}
	
	public void leftClicked(Point2D point) {
		content().leftClicked(point);
	}
	
	@Override
	public void update(long nsSinceLastFrame) {
		RoomInfo oldRoom = content().currentRoom();
		content().player().update(nsSinceLastFrame);
		content().updateCurrentRoom();
		RoomInfo newRoom = content().currentRoom();
		if(oldRoom == null && newRoom != null) {
			if(!isCleared(newRoom)) {
				startFighting();
			}
		}
		else if(newRoom != null) {
			if(isFighting && !requiredEnemiesExist())
				stopFighting(newRoom);
		}
		content().update(nsSinceLastFrame);
	}
	
	public boolean requiredEnemiesExist() {
		for(Enemy e : content().enemies())
			if(e.isRequired())
				return true;
		return false;
	}
	
	public Collection<Platform> platforms() {
		return content().platforms();
	}
	
	public MainContent content() {
		return content;
	}
	
	private void startFighting() {
		isFighting = true;
		content().lockSpawnAndFill();
	}

	/** Assumes the given room is locked. Marks the given room as {@link #clearedRooms cleared}. */
	public void stopFighting(RoomInfo ri) {
		isFighting = false;
		Main.scene().stoppedFighting();
		markCleared(ri);
		content().killAllEnemies();
		content().unlock(ri);
		content().showAdjacentHallways(ri);
		if(!anyRoomsLeft())
			levelComplete();
	}
	
	private void markCleared(RoomInfo ri) {
		clearedRooms.add(ri);
	}
	
	public boolean isCleared(RoomInfo ri) {
		return clearedRooms.contains(ri);
	}
	
	public boolean anyRoomsLeft() {
		return clearedRooms.size() < content().floorPlan().roomCount();
	}
	
	private void levelComplete() {
		Main.scene().levelComplete(levelIndex);
	}
	
	public int level() {
		return levelIndex + 1;
	}
	
	public boolean isFighting() {
		return isFighting;
	}

}
