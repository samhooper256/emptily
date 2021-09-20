package base;

import javafx.scene.image.ImageView;

public class HealthIcon extends ImageView {

	public HealthIcon() {
		super(Main.FULL_HEALTH);
	}
	
	public void fill() {
		setImage(Main.FULL_HEALTH);
	}
	
	public void empty() {
		setImage(Main.EMPTY_HEALTH);
	}
	
}
