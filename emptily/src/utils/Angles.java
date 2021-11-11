package utils;

public final class Angles {

	private static final double TWOPI = 2 * Math.PI;

	private Angles() {
		
	}
	
	/** If {@code angrad} is positive, returns {@code angrad}. If {@code angrad} is negative, adds {@code 2 * Math.PI}
	 * until it is positive.*/
	public static double make0to2PI(double angrad) {
		while(angrad < 0)
			angrad += TWOPI;
		while(angrad > TWOPI)
			angrad -= TWOPI;
		return angrad;
	}
	
	public static double normalize(double angrad) {
		while(angrad > Math.PI)
			angrad -= TWOPI;
		while(angrad < -Math.PI)
			angrad += TWOPI;
		return angrad;
	}
	
	public static double upWrapDist2PI(double start, double dest) {
		if(dest >= start)
			return dest - start;
		return TWOPI - start + dest;
	}
	
	public static double downWrapDist2PI(double start, double dest) {
		return upWrapDist2PI(dest, start);
	}
	
	public static double makeCloser(double start, double dest, double maxJump) {
		double dist = minDist(start, dest);
		if(dist <= maxJump)
			return dest;
		double up = make0to2PI(start + maxJump), down = make0to2PI(start - maxJump);
		return minDist(up, dest) < minDist(down, dest) ? up : down;
	}
	
	public static double minDist(double start, double dest) {
		start = make0to2PI(start);
		dest = make0to2PI(dest);
		double up = upWrapDist2PI(start, dest);
		double down = downWrapDist2PI(start, dest);
		return Math.min(up, down);
	}
	
}