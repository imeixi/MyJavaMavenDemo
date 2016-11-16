package cn.imeixi.jmeter.javarequest;

public class Hello {
	public String sayHello(){
		return "hello";
	}
	
	public String sayHelloToPerson(String s){
		if(s == null || s.equals("")){
			s = "nobadoy";
		}
		
		return (new StringBuilder()).append("Hello ").append("s").toString();
	}
	
	public int sum(int a, int b){
		return a + b;
	}
}

