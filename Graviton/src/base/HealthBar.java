package base;

import javafx.geometry.Insets;
import javafx.scene.layout.HBox;

public final class HealthBar extends HBox {
	
	private static final int DEFAULT_HEALTH = 3;
	private static final double SPACING = 8, PADDING = SPACING * 1.5;
	
	private final int maxhp;
	private int hp;
	
	public HealthBar() {
		this(DEFAULT_HEALTH);
	}
	
	public HealthBar(int maxhp) {
		super(SPACING);
		setPadding(new Insets(PADDING));
		this.maxhp = this.hp = maxhp;
		for(int i = 0; i < maxhp; i++)
			getChildren().add(new HealthIcon());
		setMouseTransparent(true);
	}
	
	/** Does nothing if this {@link HealthBar} is {@link #isEmpty() empty}.*/
	public void hit() {
		if(!isEmpty()) {
			hp--;
			icon(hp).empty();
		}
	}
	
	private HealthIcon icon(int index) {
		return (HealthIcon) getChildren().get(index);
	}

	public int maxhp() {
		return maxhp;
	}
	
	public int hp() {
		return hp;
	}
	
	public boolean isEmpty() {
		return hp() == 0;
	}
	
}
