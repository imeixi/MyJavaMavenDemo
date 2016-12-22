package cn.imeixi.java.properties;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Properties;

public class PropertiesReflectTest {
	public static void main(String[] args) {
		Properties properties = new Properties();
		properties.put("name", "aaa");
		properties.put("passwd", "bbb");
		
		InputStream is = null;
		OutputStream os = null;
		
		//写入属性文件
		try {
			os = new FileOutputStream("user.properties");
			properties.store(os, "this is comment info");   //  第二个string是注释信息
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
				try {
				if (!(os == null))
					os.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		
	}	

}
