package cn.imeixi.java.properties;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

public class PropertiesTest {

	public static void main(String[] args) {
		// generate a new properties content
		Properties properties = new Properties();
		generateProperties(properties);

		// create properties file
		File propertiesFile = createPropertiesFile();

		// save properties to file
		saveProperties2File(propertiesFile, properties);

		// load/read properties from file
		loadPropertiesFromFile(propertiesFile, properties);

		// create properties file
		File propertiesXmlFile = createPropertiesXmlFile();

		// save properties to file
		saveProperties2XmlFile(propertiesXmlFile, properties);

		// load/read properties from file
		loadPropertiesFromXmlFile(propertiesXmlFile, properties);

	}

	private static void loadPropertiesFromFile(File propertiesFile,
			Properties properties) {
		InputStream is = null;
		try {
			is = new FileInputStream(propertiesFile);
			properties.load(is);

			// 遍历properties属性值：方法一
			// use Enumeration to visit the properties 获取所有Key值
			System.out.println("\n" + "Enumeration:porpertiesNames" + "");
			Enumeration<?> enumeration = properties.propertyNames();
			while (enumeration.hasMoreElements()) {
				String key = (String) enumeration.nextElement();
				String value = properties.getProperty(key);
				System.out.println(key + "=" + value);
			}

			// 遍历properties属性值：方法二
			// use Enumeration to visit the properties 获取所有Value 值
			System.out.println("\n" + "Enumeration:elements");
			Enumeration<Object> enum1 = properties.elements();
			while (enum1.hasMoreElements()) {
				String value = (String) enum1.nextElement();
				System.out.println(value);
			}
			// 遍历properties属性值：方法三
			// use stringPropertyNames() to visit the properties
			System.out.println("\n" + "Set:stringPropertyNames() " + "");
			Set<String> keySetNew = properties.stringPropertyNames();
			Iterator<String> iterator1 = keySetNew.iterator();
			while (iterator1.hasNext()) {
				String key = (String) iterator1.next();
				String value = properties.getProperty(key);
				System.out.println(key + "=" + value);
			}

			// 遍历properties属性值：方法四
			// use KeySet to visit the properties
			System.out.println("\n" + "KeySet()" + "");
			Set<Object> keySet = properties.keySet();
			Iterator<Object> iterator = keySet.iterator();
			while (iterator.hasNext()) {
				String key = (String) iterator.next();
				String value = properties.getProperty(key);
				System.out.println(key + "=" + value);
			}

			// 遍历properties属性值：方法五
			// use entrySet to visit the properties
			System.out.println("\n" + "entrySet()" + "\n");
			Set<Entry<Object, Object>> entrySet = properties.entrySet();
			Iterator<Entry<Object, Object>> entryIter = entrySet.iterator();
			while (entryIter.hasNext()) {
				Entry<Object, Object> entry = entryIter.next();
				Object key = entry.getKey();
				Object value = entry.getValue();
				System.out.println(key + "=" + value);
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if ((!(is == null)))
					is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static void loadPropertiesFromXmlFile(File propertiesXmlFile,
			Properties properties) {
		InputStream is = null;
		try {
			is = new FileInputStream(propertiesXmlFile);
			properties.loadFromXML(is);

			System.out.println("LoadFromXml");

			// 遍历properties属性值：方法一
			// use Enumeration to visit the properties
			System.out.println("\n" + "Enumeration:porpertiesNames" + "");
			Enumeration<?> enumeration = properties.propertyNames();
			while (enumeration.hasMoreElements()) {
				String key = (String) enumeration.nextElement();
				String value = properties.getProperty(key);
				System.out.println(key + "=" + value);
			}

			// 遍历properties属性值：方法二
			// use KeySet to visit the properties
			System.out.println("\n" + "KeySet()" + "");
			Set<Object> keySet = properties.keySet();
			Iterator<Object> iterator = keySet.iterator();
			while (iterator.hasNext()) {
				String key = (String) iterator.next();
				String value = properties.getProperty(key);
				System.out.println(key + "=" + value);
			}

			// 遍历properties属性值：方法三
			// use stringPropertyNames() to visit the properties
			System.out.println("\n " + " stringPropertyNames() " + "");
			Set<String> keySetNew = properties.stringPropertyNames();
			Iterator<String> iterator1 = keySetNew.iterator();
			while (iterator1.hasNext()) {
				String key = (String) iterator1.next();
				String value = properties.getProperty(key);
				System.out.println(key + "=" + value);
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if ((!(is == null)))
					is.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private static void saveProperties2File(File propertiesFile,
			Properties properties) {
		OutputStream os = null;
		try {
			os = new FileOutputStream(propertiesFile);
			properties.store(os, "properties write test");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if ((!(os == null)))
					os.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	private static void saveProperties2XmlFile(File propertiesXmlFile,
			Properties properties) {
		OutputStream os = null;
		try {
			os = new FileOutputStream(propertiesXmlFile);
			properties.storeToXML(os, "properties write test");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if ((!(os == null)))
					os.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	private static File createPropertiesFile() {
		File propertiesDir = null;
		File propertiesFile = null;
		try {
			String userDir = System.getProperty("user.dir");
			propertiesDir = new File(userDir + File.separator + "config");
			System.out.println(propertiesDir);
			if (!(propertiesDir.exists())) {
				propertiesDir.mkdirs();
			}
			propertiesFile = new File(propertiesDir + File.separator
					+ "properties.property");
			if (!(propertiesFile.exists())) {
				propertiesFile.createNewFile();
			}
			System.out.println(propertiesFile);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return propertiesFile;
	}

	private static File createPropertiesXmlFile() {
		File propertiesDir = null;
		File propertiesXmlFile = null;
		try {
			String userDir = System.getProperty("user.dir");
			propertiesDir = new File(userDir + File.separator + "config");
			System.out.println(propertiesDir);
			if (!(propertiesDir.exists())) {
				propertiesDir.mkdirs();
			}
			propertiesXmlFile = new File(propertiesDir + File.separator
					+ "properties.xml");
			if (!(propertiesXmlFile.exists())) {
				propertiesXmlFile.createNewFile();
			}
			System.out.println(propertiesXmlFile);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return propertiesXmlFile;
	}

	private static void generateProperties(Properties properties) {
		properties.setProperty("name", "imeixi");
		properties.setProperty("url", "www.imeixi.cn");
		properties.setProperty("expires", "2027年5月1日");
		properties.setProperty("type", "private");
	}
}
