package cn.imeixi.java.Date;


import java.util.Date;

import org.junit.Test;

public class TestDate {

	@Test
	public void testGetCurrentTime() {
		System.out.println(new Time().getCurrentTime());
	}

	@Test
	public void testGetDate() {
		Date date = new Time().getDate();
		System.out.println(date);
		System.out.println(date.getTime());

		
	}

}

