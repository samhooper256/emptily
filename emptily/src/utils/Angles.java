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
	
	public static double upWrapDist2PI(double angrad1, double angrad2) {
		if(angrad2 >= angrad1)
			return angrad2 - angrad1;
		return TWOPI - angrad1 + angrad2;
	}
	
	public static double downWrapDist2PI(double angrad1, double angrad2) {
		if(angrad2 <= angrad1)
			return angrad1 - angrad2;
		return angrad1 + TWOPI - angrad2;
	}
	
}