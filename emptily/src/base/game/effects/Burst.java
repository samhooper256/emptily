package base.game.effects;

import javafx.scene.layout.Pane;
import javafx.util.Duration;

public abstract class Burst extends Pane {

	public abstract void startAnimation();
	
	/** Ends this animation (and hides this burst) if it is in progress. Otherwise, does nothing.*/
	public abstract void finishAnimation();
	
	public abstract Duration duration();
	
}
