package cn.imeixi.java.io;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TestNioFilesPaths {
	public void printPaths() throws IOException{
		Path path = Paths.get("/etc", "hosts");
		System.out.println(path);
		
		InputStream is = Files.newInputStream(path);
		
		byte[] b = new byte[1024];
		int len = 0;
		while((len = is.read(b)) != -1){
			String str = new String(b, 0, len);
			System.out.println(str);
		}
	}
	
	public static void main(String[] args) throws IOException {
		new TestNioFilesPaths().printPaths();
	}
}

