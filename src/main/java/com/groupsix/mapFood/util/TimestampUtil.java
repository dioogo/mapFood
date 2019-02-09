package com.groupsix.mapFood.util;

import java.sql.Timestamp;
import java.util.Calendar;

public class TimestampUtil {

	public static Timestamp addSeconds(Long sec) {
		long now = System.currentTimeMillis();
		
		Timestamp original = new Timestamp(now);
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(original.getTime());
        cal.add(Calendar.SECOND, sec.intValue());
        return new Timestamp(cal.getTime().getTime());
	}
}
