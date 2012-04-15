package com.neya.love.finder.utils.net;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Date {

	public static final String DATE_FORMAT_NOW = "MM-dd";
	
	public static String now() {
	    Calendar cal = Calendar.getInstance();
	    SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
	    return sdf.format(cal.getTime());
	  }
}
