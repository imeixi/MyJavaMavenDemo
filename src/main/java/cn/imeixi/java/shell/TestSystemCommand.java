package cn.imeixi.java.shell;

import java.io.InputStreamReader;
import java.io.LineNumberReader;

public class TestSystemCommand {
	public static void main(String[] args) {
//		String[] cmds = {"sh", "-c", "jmeter -n -t /Users/perfermance/JmeterTest/script/saveOperInfo_ok.jmx -l /Users/perfermance/JmeterTest/results/jtl/saveOperInfo_ok_20161526.jtl"};
//		String[] cmds = {"sh", "-c", "ls ~"};
		String[] cmds = {"sh", "-c", "/Users/perfermance/git/PerformanceAutoTest/jmeter.sh"};
//		String[] cmds = {"sh", "-c", "pwd"};
		
		TestSystemCommand ts = new TestSystemCommand();
		String msg = ts.runCommand(cmds, 1);
		System.out.println("^^^^^" + msg);
	}
	
	
	
	/**
	 * 执行shell命令
	 *String[] cmd = { "sh", "-c", "lsmod |grep linuxVmux" }或者
	 *String[] cmd = { "sh", "-c", "./load_driver.sh" }
	 *int tp = 1 返回执行结果  非1 返回命令执行后的输出
	 */
	public String runCommand(String[] cmds,int tp){
		StringBuffer buf = new StringBuffer(1000);
		String rt="-1";
		try {
			Process pos = Runtime.getRuntime().exec(cmds);
			System.out.println("00000000s");
			pos.waitFor();
		/*	if(tp==1){
				if(pos.exitValue()==1){
					rt="1";
				}
			}else{
		*/		
				System.out.println("%%%%" + pos);
				InputStreamReader ir = new InputStreamReader(pos.getInputStream());
				System.out.println("####" + ir);
				LineNumberReader input = new LineNumberReader(ir);
				String ln="";
				while ((ln =input.readLine()) != null) {
//					buf.append(ln+"<br>");
					System.out.println("****" + ln);
				}
				rt = buf.toString();
				input.close();
				ir.close();
	//		}
		}catch (java.io.IOException e) {
			rt=e.toString();
		}catch (Exception e) {
			rt=e.toString();
		}
		return rt;
	}
}



