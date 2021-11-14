package base.mainmenu;

import javafx.animation.*;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class MainMenu extends StackPane {

	private final VBox vBox, buttonBox;
	private final Timeline intro;
	private final Title title;
	private final PlayButton play;
	private final ControlsButton controls;
	private final AuthorLabel author;
	
	private boolean animatedIn;
	
	public MainMenu() {
		vBox = new VBox();
		vBox.setAlignment(Pos.CENTER);
		
		title = new Title();
		play = new PlayButton();
		controls = new ControlsButton();
		author = new AuthorLabel();
		
		buttonBox = new VBox(12, play, controls);
		buttonBox.setAlignment(Pos.TOP_CENTER);
		vBox.getChildren().addAll(title, buttonBox, author);
		
		getChildren().add(vBox);
		
		intro = new Timeline();
		intro.getKeyFrames().add(new KeyFrame(Duration.ZERO, eh -> title.animateIn()));
		intro.getKeyFrames().add(new KeyFrame(Duration.millis(700), eh -> play.animateIn()));
		intro.getKeyFrames().add(new KeyFrame(Duration.millis(950), eh -> controls.animateIn()));
		intro.getKeyFrames().add(new KeyFrame(Duration.millis(1200), eh -> author.animateIn()));
		animatedIn = false;
	}
	
	public void animateIn() {
		if(controls.isExpanded())
			controls.reset();
		title.animateIn();
		if(!animatedIn) {
			intro.playFromStart();
			animatedIn = true;			
		}
	}
	
	public Title title() {
		return title;
	}
	
}
