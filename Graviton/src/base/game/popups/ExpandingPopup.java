package base.game.popups;

import javafx.animation.ScaleTransition;
import javafx.util.Duration;

public class ExpandingPopup extends EndingPopup {

	private static final Duration OPENING_EXPAND_DURATION = Duration.millis(400);
	private final ScaleTransition openingExpand = new ScaleTransition(OPENING_EXPAND_DURATION, this);
	
	public ExpandingPopup(double width, double height) {
		super(width, height);
		initOpeningExpand();
	}
	
	private void initOpeningExpand() {
		openingExpand.setFromX(0);
		openingExpand.setFromY(0);
		openingExpand.setToX(1);
		openingExpand.setToY(1);
	}
	
	@Override
	public void startOpeningAnimation() {
		this.setScaleX(0);
		this.setScaleY(0);
		this.setVisible(true);
		openingExpand.playFromStart();
	}
	
}
