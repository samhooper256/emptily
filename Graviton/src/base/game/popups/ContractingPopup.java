package base.game.popups;

import javafx.animation.*;
import javafx.util.Duration;

public class ContractingPopup extends EndingPopup {

	protected static final Duration OPENING_CONTRACT_DURATION = Duration.millis(400);
	
	private final ScaleTransition openingExpand = new ScaleTransition(OPENING_CONTRACT_DURATION, this);
	
	public ContractingPopup(double width, double height) {
		super(width, height);
		initOpeningExpand();
	}
	
	private void initOpeningExpand() {
		openingExpand.setInterpolator(Interpolator.EASE_IN);
		openingExpand.setFromX(3);
		openingExpand.setFromY(3);
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
