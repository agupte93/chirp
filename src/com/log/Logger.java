package com.log;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Logger {
	public static void printInfo(String data) {
		Calendar cal = Calendar.getInstance();
    	cal.getTime();
    	SimpleDateFormat sdf = new SimpleDateFormat("YYYY MMM d HH:mm:ss");
		System.out.println("["+sdf.format(cal.getTime())+"] INFO: "+data);
	}
	
	public static void printErr(String data) {
		Calendar cal = Calendar.getInstance();
    	cal.getTime();
    	SimpleDateFormat sdf = new SimpleDateFormat("YYYY MMM d HH:mm:ss");
		System.out.println("["+sdf.format(cal.getTime())+"] ERROR: "+data);
	}
}
