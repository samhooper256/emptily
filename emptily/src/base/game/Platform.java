package base.game;

import fxutils.*;
import javafx.animation.Transition;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class Platform extends StackPane {
	
	private static final Border BORDER = Borders.of(Color.GREEN);
	private static final Background BACKGROUND = Backgrounds.of(Color.GREEN);
	
	public static Platform fromCorners(double ulx, double uly, double lrx, double lry) {
		return new Platform(ulx, uly, lrx - ulx, lry - uly);
	}
	
	private final double width, height;
	private final Transition fadeIn = new Transition() {
	
		{
			setCycleDuration(Duration.millis(500));
			setOnFinished(eh -> {
				setBackground(BACKGROUND);
				setBorder(BORDER);
			});
		}
		
		@Override
		protected void interpolate(double frac) {
			setBorder(Borders.of(Color.rgb(0, 128, 0, 1 - frac)));
			setBackground(Backgrounds.of(Color.rgb(0, 128, 0, frac)));
		}
		
	};
	
	
	public Platform(double x, double y, double width, double height) {
		this(x, y, width, height, false);
	}
	
	public Platform(double x, double y, double width, double height, boolean bordered) {
		this(width, height);
		Nodes.setLayout(this, x, y);
		setBorderdWithoutAnimating(bordered);
	}

	public Platform(double width, double height) {
		setBackground(BACKGROUND);
		this.width = width;
		this.height = height;
		setMinWidth(width);
		setMinHeight(height);
	}
	
	public double width() {
		return width;
	}
	
	public double height() {
		return height;
	}
	
	public void setBordered(boolean bordered) {
		if(bordered)
			setBorderdWithoutAnimating(true);
		else
			fadeIn.playFromStart();
	}
	
	private void setBorderdWithoutAnimating(boolean bordered) {
		if(bordered) {
			setBackground(null);
			setBorder(BORDER);
		}
		else {
			setBackground(BACKGROUND);
			setBorder(null);
		}	
	}
	
	
}