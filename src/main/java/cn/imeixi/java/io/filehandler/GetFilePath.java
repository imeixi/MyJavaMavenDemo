package cn.imeixi.java.io.filehandler;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

public class GetFilePath {
	
	public String getClassPath() throws URISyntaxException {
		String path = "";
		path = new File(getClass().getClassLoader().getResource("").toString()).getPath();
		return path;
	}
	
	
	
	
	public static void main(String[] args) throws URISyntaxException, IOException {
		
		//取程序运行时的目录路径。即程序在那里双击或BAT文件所在的路径
		System.out.println("1、 " + new GetFilePath().getClassPath());
		
		//第一种  
		//获取当前类的所在工程路径; 
		File f = new File(GetFilePath.class.getResource("/").getPath()); 
		System.out.println("2、 " + f); 
		
		//如果不加“/”   
		//获取当前类的绝对路径；
		File f1 = new File(GetFilePath.class.getResource("").getPath()); 
		System.out.println("3、 " + f1); 
		
		//第二种：
		//获取当前类的所在工程路径;
		File directory = new File("");//参数为空 
		String courseFile = directory.getCanonicalPath() ; 
		System.out.println("4、 " + courseFile);
		
		//第三种
		//获取当前工程src目录下employee.txt文件的路径 【适用JAVA工程】
		URL xmlpath = GetFilePath.class.getClassLoader().getResource("employee.txt"); 
		System.out.println("5、 " + xmlpath); 
		
//		Path path = Paths.get("employee.txt");
//		System.out.println("5、 " + path);
		
		//第四种：
		//获取当前工程目录路径
		System.out.println("6、 " + System.getProperty("user.dir"));
		
		//第五种： 
		//获取当前工程路径
		System.out.println("7、 " + System.getProperty("java.class.path")); 
		
		
		//sevlet中  在Servlet中，"/"代表Web应用的跟目录。
	}

}
