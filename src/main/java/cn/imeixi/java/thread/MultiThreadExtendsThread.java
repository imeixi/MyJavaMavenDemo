package cn.imeixi.java.thread;

import javax.swing.plaf.synth.SynthSeparatorUI;

/**
 *  
   * @Name: MultiThreadExtendsThread.java
   * @Description: 通过继承Thread类实现多线程
   * @Author: ZhengAH（作者）
   * @Version: V1.00 （版本号）
   * @Create Date: 2016年11月2日下午5:58:24
 */
public class MultiThreadExtendsThread{
	
	public static void main(String[] args) {
		Threads threads1 = new Threads("A");
		Threads threads2 = new Threads("B");
		
		threads1.start();
		threads2.start();
	}
	

}

class Threads extends Thread{
	
	private String name;
	
	public Threads(String name) {
		this.name = name;
	}
	
	public void run(){
		System.out.println(name + ":Begin the Thread");
		
		for(int i = 0;i < 5;i++){
			System.out.println(name + ":" + i);
		}
		
		try {
			sleep((int)Math.random()*10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println(name + ":End the Thread");
	}
		
}
