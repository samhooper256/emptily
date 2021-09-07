package utils;

import java.util.Random;

public final class RNG {

	private RNG() {
		
	}
	
	private static final Random RANDOM = new Random();
	
	public static int intExclusive(int low, int high) {
		return RANDOM.nextInt(high - low) + low;
	}
	
}
