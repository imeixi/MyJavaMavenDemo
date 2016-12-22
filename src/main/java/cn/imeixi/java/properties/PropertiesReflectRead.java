package cn.imeixi.java.properties;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;

/**
 * @author zhengaihua
 *
 */

public class PropertiesReflectRead {
	public static void main(String[] args) {
		Properties properties = new Properties();
		InputStream is = null;
		//读取属性文件
		try {
			is = ClassLoader.getSystemResourceAsStream("user.properties");
			properties.load(is);
			
			printProperties(properties);

			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			if(!(is == null))
				try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		
		
		
		
	}

	private static void printProperties(Properties properties) {
		
		Enumeration<?> en = properties.propertyNames();
		while(en.hasMoreElements()){
			System.out.println(en.nextElement());
		}
		
	}
	
}
