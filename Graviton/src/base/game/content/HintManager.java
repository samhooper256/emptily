package base.game.content;

import base.*;
import base.game.*;
import base.game.hints.*;
import javafx.scene.input.KeyCode;

final class HintManager implements DelayUpdatable {
	
	private static final long
			MOVEMENT_RULES_DELAY = 1_000_000_000L * 5, //5 seconds
			SHOOTING_HINT_DELAY = MOVEMENT_RULES_DELAY; 
	
	private final MainContent content;
	private final MovementRulesHint mrh;
	private final ShootingHint shootingHint;
	
	private boolean canShowMovementRules, hasShotYet;
	private long timeSinceLastMoved, timeSinceStartedFighting;
	
	HintManager(MainContent content) {
		this.content = content;
		timeSinceLastMoved = 0;
		canShowMovementRules = true;
		hasShotYet = false;
		mrh = new MovementRulesHint();
		shootingHint = new ShootingHint();
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
		if(Main.pane().isFighting() && !hasShotYet) {
			timeSinceStartedFighting += nsSinceLastFrame;
			if(timeSinceStartedFighting > SHOOTING_HINT_DELAY) {
				content.requestEnd(makeRequest(shootingHint));
				hasShotYet = true;
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
	
	void leftClicked() {
		hasShotYet = true;
		shootingHint.animateOff();
	}
	
	void reset() {
		canShowMovementRules = true;
	}
}
