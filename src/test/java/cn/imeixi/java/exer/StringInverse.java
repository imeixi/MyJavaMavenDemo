package cn.imeixi.java.exer;

import java.io.CharArrayWriter;
import java.util.Arrays;

/**
 * java编程，写一个方法，将一个指定的字符串倒序输出
 * 
 * @Name: StringInverse.java
 * @Description:
 * @Author: ZhengAH（作者）
 * @Version: V1.00 （版本号）
 * @Create Date: 2016年11月10日下午2:50:17
 */
public class StringInverse {

	public static void main(String[] args) {
		String str = "This is the real world!";
		String str1 = "abcdefg";
		System.out.println(str);
		System.out.println(str1);
//		System.out.println(new StringInverse().inverseString(str));
//		System.out.println(new StringInverse().revertString(str1));
//		System.out.println(new StringInverse().revertString(str));
//		System.out.println(new StringInverse().revertString1(str1));
//		System.out.println(new StringInverse().revertString1(str));
//		System.out.println(new StringInverse().revertString3(str1));
//		System.out.println(new StringInverse().revertString3(str));
		new StringInverse().revertString2(str1);
		System.out.println();
		new StringInverse().revertString2(str);
	}

	public String inverseString(String str) {
		String string = "";
		String[] strs = str.split(" ");
		for (int i = strs.length - 1; i >= 0; i--) {
			string += strs[i] + " ";
		}
		return string;
	}

	public String revertString(String str) {
		if (str == null) {
			return null;
		}
		char[] chars = str.toCharArray();
		char temp;
		System.out.println(chars.length);
		for (int i = 0; i < chars.length / 2 + 1; i++) {
			temp = chars[i];
			chars[i] = chars[chars.length - 1 - i];
			chars[chars.length - 1 - i] = temp;
		}
		String result1 = new String(chars);
		String result2 = String.valueOf(chars);
		return "result1 = \"" + result1 + "\" result2 = \"" + result2 + "\"";
	}

	public String revertString1(String str) {
		if (str == null) {
			return null;
		}
		char[] chars = str.toCharArray();
		String resultString = "";
		for (int i = chars.length - 1; i >= 0; i--) {
			resultString += chars[i];
		}
		return resultString;
	}

	public void revertString2(String str) {
//		String resultString = "";

		if (str.length() == 1) {
			System.out.print(str);
		} else {
			String subString1 = str.substring(0, str.length() - 1);
			String subString2 = str.substring(str.length() - 1);

//			resultString += subString2;
			System.out.print(subString2);
			revertString2(subString1);
		}
//		return resultString;
	}

	public String revertString3(String str) {

		StringBuffer stringBuffer = new StringBuffer(str);

		return stringBuffer.reverse().toString();

	}

}
