package cn.imeixi.java.net;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class WhoAmI {
	public static void main(String[] args) throws UnknownHostException {
		if(args.length != 1){
			System.err.println("Usage:WhoAmI Machine Name");
			System.exit(1);
		}
		InetAddress i = InetAddress.getByName(args[0]);
		System.out.println(i);
	}
	
}

