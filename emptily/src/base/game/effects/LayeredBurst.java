package base.game.effects;

import javafx.animation.*;
import javafx.util.Duration;

public class LayeredBurst extends Burst {

	private final Timeline timeline;
	private final Burst[] bursts;
	private final Duration[] durations;
	
	public LayeredBurst(Burst[] bursts, Duration[] durations) {
		timeline = new Timeline();
		this.bursts = bursts;
		this.durations = durations;
		for(int i = 0; i < bursts.length; i++) {
			Burst burst = bursts[i];
			timeline.getKeyFrames().add(new KeyFrame(durations[i], eh -> burst.startAnimation()));
		}
		timeline.getKeyFrames().add(new KeyFrame(duration(), eh -> finishAnimation()));
		
		getChildren().addAll(bursts);
	}

	@Override
	public void startAnimation() {
		timeline.playFromStart();
	}
	
	@Override
	public void finishAnimation() {
		timeline.stop();
		for(Burst b : bursts)
			b.finishAnimation();
	}

	@Override
	public Duration duration() {
		return durations[bursts.length - 1].add(bursts[bursts.length - 1].duration());
	}
	
}
