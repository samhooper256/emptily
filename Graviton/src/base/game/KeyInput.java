package base.game;

import javafx.scene.input.KeyCode;

public final class KeyInput {
	
	private static final KeyCode ZOOM_CODE = KeyCode.CONTROL;
	
	private KeyInput() {
		
	}

	private static final KeyCode
			UP_CODE = KeyCode.W,
			RIGHT_CODE = KeyCode.D,
			DOWN_CODE = KeyCode.S,
			LEFT_CODE = KeyCode.A;
	
	public static boolean isModeCode(KeyCode code) {
		return code == UP_CODE || code == RIGHT_CODE || code == DOWN_CODE || code == LEFT_CODE;
	}
	
	public static GravityMode modeFor(KeyCode code) {
		if(code == UP_CODE)
			return GravityMode.UP;
		if(code == RIGHT_CODE)
			return GravityMode.RIGHT;
		if(code == DOWN_CODE)
			return GravityMode.DOWN;
		if(code == LEFT_CODE)
			return GravityMode.LEFT;
		throw new IllegalArgumentException(String.format("The given KeyCode does not map to a "
				+ "GravityMode. code=%s", code));
	}
	
	public static KeyCode zoomCode() {
		return ZOOM_CODE;
	}
	
}
