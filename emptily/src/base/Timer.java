package base;

import javafx.animation.AnimationTimer;

public class Timer extends AnimationTimer {

	private long last = -1;
	
	@Override
	public void handle(long now) {
		long diff = getDiff(now);
		if(diff == 0)
			return;
		
		Main.update(diff);
		
		setLast(now);
	}
	
	private long getDiff(long now) {
		if(last < 0)
			return -1;
		return now - last;
	}
	
	private void setLast(long last) {
		this.last = last;
	}

	public long last() {
		return last;
	}
	
}
