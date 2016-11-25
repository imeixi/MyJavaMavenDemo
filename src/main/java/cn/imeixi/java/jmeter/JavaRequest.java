package cn.imeixi.java.jmeter;
import java.util.Date;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;

/**
 * Jmeter Java Request
 * 
 * 1、将JMeter的lib目录下的jar文件添加进此工程的Build Path；
 *   可只添加ApacheJmeter_core.jar 和 ApacheJmeter_java.jar 
 * 
 * 1、实现(implements)JavaSamplerClient接口或继承(extends)AbstractJavaSamplerClient，
 * 
 * 2、重写
 *  public Arguments getDefaultParameters();设置可用参数及的默认值；
	public void setupTest(JavaSamplerContext arg0)：每个线程测试前执行一次，做一些初始化工作；
	public SampleResult runTest(JavaSamplerContext arg0)：开始测试，从arg0参数可以获得参数值；
	public void teardownTest(JavaSamplerContext arg0)：测试结束时调用；
 * 
 * 
 * 
 */

public class JavaRequest extends AbstractJavaSamplerClient{

	private String a;
	private String b;
	private long start;
	private long end;
	
	//Holds the result data (shown as Response Data in the Tree display). 
	private String resultData;
	
	
    // 这个方法是用来自定义java方法入参的。
    // params.addArgument("num1","");表示入参名字叫num1，默认值为空。
    //设置可用参数及的默认值；
	public Arguments getDefaultParameters() {
		Arguments params = new Arguments();
		params.addArgument("num1", "");
		params.addArgument("num2", "");
		return params;
	}

	//每个线程测试前执行一次，做一些初始化工作；
	public void setupTest(JavaSamplerContext arg0) {
		start = System.currentTimeMillis();
		System.out.println("start time at: " + start);
	}

	//开始测试，从arg0参数可以获得参数值；
	public SampleResult runTest(JavaSamplerContext arg0) {
		a = arg0.getParameter("num1");
		b = arg0.getParameter("num2");
		
		SampleResult sampleResult = new SampleResult();
		sampleResult.setSampleLabel("Java Request Project");
		
		try {
			
			sampleResult.sampleStart();  //jmeter 开始统计响应时间标记
			
			Hello hello = new Hello();   //以下为 请求实体（可以是HTTP Request，Dubbo，或者直接调用底层方法等等）
			
			resultData = String.valueOf(hello.sum(Integer.parseInt(a), Integer.parseInt(b)));  //调用第三方请求，获取结果
			
			// 通过下面的操作就可以将被测方法的响应输出到Jmeter的察看结果树中的响应数据里面了。
			if(resultData != null && resultData.length() > 0){
				sampleResult.setResponseData("结果是 ：" + resultData, null);         
				sampleResult.setDataType(SampleResult.TEXT);
			}
			
			sampleResult.setSuccessful(true);            //设置响应结果状态
		} catch (Throwable e) {
			sampleResult.setSuccessful(false);            //设置响应结果状态
			e.printStackTrace();
		} finally{
			sampleResult.sampleEnd();                    // jmeter 结束统计响应时间标记
		}
	
		return sampleResult;
	}
	
	//测试结束时调用；
	public void teardownTest(JavaSamplerContext arg0) {
		
		 end = System.currentTimeMillis();
         System.out.println("end time at: " + end);
         System.out.println("The cost is "+(end-start)/1000);
         
	}

	
//	测试Jar
	public static void main(String[] args) {
		Arguments param = new Arguments();
		param.addArgument("num1", "1");
		param.addArgument("num2", "2");
		
		JavaSamplerContext arg0 = new JavaSamplerContext(param);
		JavaRequest jr = new JavaRequest();
		jr.setupTest(arg0);
		jr.runTest(arg0);
		jr.teardownTest(arg0);
	}


}

