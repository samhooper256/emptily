package base;

public enum GravityMode {
	UP(0, -1), RIGHT(1, 0), DOWN(0, 1), LEFT(-1, 0);
	
	private static final double ACCELERATION = 5; //pixels per second
	
	private final double xAccel, yAccel;
	
	GravityMode(int xmul, int ymul) {
		xAccel = xmul * ACCELERATION;
		yAccel = ymul * ACCELERATION;
	}
	
	public double xAccel() {
		return xAccel;
	}
	
	public double yAccel() {
		return yAccel;
	}
	
}
