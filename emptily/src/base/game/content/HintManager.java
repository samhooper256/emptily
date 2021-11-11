package base.game.content;

import base.*;
import base.game.*;
import base.game.hints.*;
import javafx.scene.input.KeyCode;

final class HintManager implements DelayUpdatable {
	
	private static final long MOVEMENT_RULES_DELAY = 1_000_000_000L * 5; //5 seconds
	
	private final MainContent content;
	private final MovementRulesHint mrh;
	
	private boolean canShowMovementRules;
	private long timeSinceLastMoved;
	
	HintManager(MainContent content) {
		this.content = content;
		timeSinceLastMoved = 0;
		canShowMovementRules = true;
		mrh = new MovementRulesHint();
	}
	
	@Override
	public void update(long nsSinceLastFrame) {
		if(canShowMovementRules) {
			timeSinceLastMoved += nsSinceLastFrame;
			if(timeSinceLastMoved > MOVEMENT_RULES_DELAY) {
				content.requestEnd(makeRequest(mrh));
				canShowMovementRules = false;
			}
		}
	}
	
	private Runnable makeRequest(UserHint h) {
		return () -> {
			Player p = content.player();
			h.animateOn(content, p.centerX() - h.getMaxWidth() / 2, p.y() + 2 * p.height());
		};
	}
	
	void keyPressed(KeyCode code) {
		if(KeyInput.isModeCode(code)) {
			canShowMovementRules = false;
			mrh.animateOff();
		}
	}
	
	void reset() {
		canShowMovementRules = true;
	}
	
}
