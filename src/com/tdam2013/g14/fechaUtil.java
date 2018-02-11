package com.tdam2013.g14;

import java.util.Calendar;

public final class fechaUtil {

	public static long getFechaActual()
	{
		return Calendar.getInstance().getTimeInMillis() / 1000;
	}
	
	
}
