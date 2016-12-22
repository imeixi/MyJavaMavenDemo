package cn.imeixi.java.io.filehandler;

import java.io.File;

import org.junit.Test;


public class LoadFile {
	@Test
	public void printFilePath() throws Exception{
		System.out.println("1、" + Thread.currentThread().getContextClassLoader().getResource("").toURI().getPath());
		System.out.println("2、" + LoadFile.class.getClassLoader().getResource("").toURI().getPath());
		System.out.println("3、" + ClassLoader.getSystemResource("").toURI().getPath());
		System.out.println("4、" + LoadFile.class.getResource("").toURI().getPath());
		System.out.println("5、" + LoadFile.class.getResource("/").toURI().getPath());
//		System.out.println(new File("/").getAbsolutePath().toURI().getPath());
		System.out.println("6、" + new File("/").toString());
//		System.out.println(System.getProperty("user.dir").toURI().getPath());
		System.out.println("7、" + System.getProperty("user.dir").toString());
		
	}

}
