package cn.imeixi.java.io.filehandler;

import java.io.File;

import org.junit.Test;


public class LoadFile {
	@Test
	public void printFilePath() throws Exception{
		System.out.println(Thread.currentThread().getContextClassLoader().getResource("").toURI().getPath());
		System.out.println(LoadFile.class.getClassLoader().getResource("").toURI().getPath());
		System.out.println(ClassLoader.getSystemResource("").toURI().getPath());
		System.out.println(LoadFile.class.getResource("").toURI().getPath());
		System.out.println(LoadFile.class.getResource("/").toURI().getPath());
//		System.out.println(new File("/").getAbsolutePath().toURI().getPath());
		System.out.println(new File("/").toString());
//		System.out.println(System.getProperty("user.dir").toURI().getPath());
		System.out.println(System.getProperty("user.dir").toString());
		
	}

}
