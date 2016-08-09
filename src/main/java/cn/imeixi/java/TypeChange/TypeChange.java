package cn.imeixi.java.TypeChange;

import java.sql.Date;

public class TypeChange {
   public TypeChange() {
   }
   //change the string type to the int type
   public static   int stringToInt(String intstr)
   {
     Integer integer;
     integer = Integer.valueOf(intstr);
     return integer.intValue();
   }
   //change int type to the string type
   public static String intToString(int value)
   {
     Integer integer = new Integer(value);
     return integer.toString();
   }
   //change the string type to the float type
   public static   float stringToFloat(String floatstr)
   {
     Float floatee;
     floatee = Float.valueOf(floatstr);
     return floatee.floatValue();
   }
   //change the float type to the string type
   public static String floatToString(float value)
   {
     Float floatee = new Float(value);
     return floatee.toString();
   }
   //change the string type to the sqlDate type
   public static java.sql.Date stringToDate(String dateStr)
   {
     return   java.sql.Date.valueOf(dateStr);
   }
   //change the sqlDate type to the string type
   public static String dateToString(java.sql.Date datee)
   {
     return datee.toString();
   }

   public static void main(String[] args)
   {
     java.sql.Date day ;
     day = TypeChange.stringToDate("2003-11-3");
     String strday = TypeChange.dateToString(day);
     System.out.println(strday);
   }
   
	public void String2Int() {
		String str = "123";
		Integer i = null;
		// i = Integer.getInteger("12");
		i = Integer.parseInt(str);
		System.out.println("Integer.parseInt(str) = " + i);

		i = Integer.valueOf(str);
		System.out.println("Integer.valueOf(str) = " + i);

		i = Integer.valueOf(str).intValue();
		System.out.println("Integer.valueOf(str) = " + i);
	}
}

/**
 * 区分Integer.getInteger和Integer.valueOf使用方法
 * 
 * Integer类有两个看起来很类似的静态方法，一个是Integer.getInteger(String),另外一个是Integer.valueOf(String）。
 * 如果只看方法名称的话，很容易将这两个方法的功能区分开来，还是让我们来看看Java文档。
 * 
 * Integer.getInteger(String)的功能是根据指定的名称得到系统属性的整数值。第一个参数将被认为是系统属性的名称。系统属性可以通过
 * System.getProperty(java.lang.String)方法访问得到。属性值字符串将被解释成一个整数，
 * 并且以表示这个值的Integer对象形式返回。可能出现的数字格式的详细说明可以在 getProperty 的定义说明里找到。
 * 
 * Integer.valueOf(String)的功能是获取给定的字符串所代表的整数。
 * 
 * 那么就让我来看看是否理解了：
 * 
 * Integer.valueOf(String)方法假设String参数表达的是一个数值，会把该数值String转化成Integer。也就是说，
 * Integer.valueOf("123") 得到一个Integer对象，其值是123。
 * Integer.getInteger(String)方法假设String参数是一个系统属性数值的名称，会读取该系统属性，然后把系统属性的值转换成一个数字。
 * 也就是说， Integer.getInteger("12345") 应该是得到 null（假设没有名为12345的系统属性）。
 * 
 * 
 * 虽然这两个方法的功能是不一样的，但是从方法的名称上并没有明显的区分开来，常常容易让人混淆。
 * 有很多bug就是因为想使用valueOf的功能但是错误的使用了getInteger方法而导致的。
 * 
 * 这是在Java语言中会产生歧义的一个糟糕的例子。另外一个糟糕的例子是Boolean.getBoolean("true")。Boolean.
 * getBoolean(String)的功能和Integer.getInteger(String)是类似的。通常我们都不会有一个名为“true”的系统属性，
 * 因此Boolean.getBoolean("true")通常会返回Boolean.FALSE。这里甚至比Integer.getInteger(String
 * )还要糟糕，还不如返回一个null更容易让人发现错误。
 */
