package utils;

import java.util.*;

public final class Colls {

	private Colls() {
		
	}
	
	public static <T> T any(Collection<? extends T> c) {
		if(c.isEmpty())
			throw new IllegalArgumentException("Collection is empty");
		return anyNonEmpty(c);
	}
	
	public static <T> T any(Collection<? extends T> c, T emptyValue) {
		if(c.isEmpty())
			return emptyValue;
		return anyNonEmpty(c);
	}

	private static <T> T anyNonEmpty(Collection<? extends T> c) {
		if(c instanceof List<?>)
			return ((List<? extends T>) c).get(0);
		else
			return c.iterator().next();
	}
	
}
