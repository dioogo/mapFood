package com.groupsix.mapFood.util;

import java.sql.Timestamp;
import java.util.Calendar;

import org.springframework.stereotype.Component;

@Component
public class TimestampUtil {

	private static final long TEN_MINUTES = 600L;

	public Timestamp addSeconds(Long sec, Timestamp time) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(time.getTime());
        cal.add(Calendar.SECOND, sec.intValue());
        return new Timestamp(cal.getTime().getTime());
	}
	
	public Timestamp addSecondsFromNow(Long sec) {
		
		long now = System.currentTimeMillis();
		Timestamp time = new Timestamp(now);
		
        return addSeconds(sec, time);
	}
	
	public Timestamp addTenMinutesFromNow() {
        return addSecondsFromNow(TEN_MINUTES);
	}
}
