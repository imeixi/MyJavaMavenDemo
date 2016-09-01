package cn.imeixi.java.Date;

import java.util.Date;


public class Time {
	public Long getCurrentTime(){
		return System.currentTimeMillis();
	}
	
	public Date getDate(){
		Date date = new Date();
		return date;
	}

}

