package base.mainmenu;

import javafx.animation.Transition;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class TitleCharacter extends Label {

	private static final String CSS = "title-character";
	private static final Duration INTRO_DURATION = Duration.millis(300);
	
	private final Transition intro = new Transition() {
	
		{
			setCycleDuration(INTRO_DURATION);
			setOnFinished(eh -> setTextFill(Color.GREEN));
		}
		
		@Override
		protected void interpolate(double frac) {
			setTextFill(Color.rgb(0, 128, 0, frac));
		}
		
	};
	
	public TitleCharacter(String str) {
		if(str.length() != 1)
			throw new IllegalArgumentException("str.length() != 1");
		setText(str);
		getStyleClass().add(CSS);
		setTextFill(null);
	}
	
	public void animateIn() {
		intro.playFromStart();
	}
	
}
