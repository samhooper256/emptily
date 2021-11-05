package base.mainmenu;

import javafx.animation.*;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

public class Title extends HBox {

	private static final Duration BETWEEN_CHARACTER_DELAY = Duration.millis(100);
	
	private final Timeline timeline;
	
	public Title() {
		this(get());
	}
	
	private Title(String title) {
		setSpacing(8);
		setAlignment(Pos.CENTER);
		timeline = new Timeline();
		for(int i = 0; i < title.length(); i++) {
			TitleCharacter tc = new TitleCharacter(title.substring(i, i + 1));
			getChildren().add(tc);
			timeline.getKeyFrames().add(new KeyFrame(BETWEEN_CHARACTER_DELAY.multiply(i), eh -> tc.animateIn()));
		}
	}
	
	public static final String get() {
		return "emptily";
	}
	
	public void animateIn() {
		timeline.playFromStart();
	}

	public Timeline timeline() {
		return timeline;
	}
	
}
