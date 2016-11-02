package cn.imeixi.java.thread;


/**
 *  
   * @Name: MultiThreadExtendsThread.java
   * @Description: 通过继承Thread类实现多线程
   * @Author: ZhengAH（作者）
   * @Version: V1.00 （版本号）
   * @Create Date: 2016年11月2日下午5:58:24
 */
public class MultiThreadImplementRunnable{
	
	public static void main(String[] args) {

		new Thread(new Thread2("A")).start();
		new Thread(new Thread2("B")).start();
	}
	

}

class Thread2 implements Runnable{
	
	private String name;
	
	public Thread2(String name) {
		this.name = name;
	}
	
	public void run(){
		System.out.println(name + ":Begin the Thread");
		
		for(int i = 0;i < 5;i++){
			System.out.println(name + ":" + i);
		}
		
		try {
			Thread.sleep((int)Math.random()*10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println(name + ":End the Thread");
	}
		
}
