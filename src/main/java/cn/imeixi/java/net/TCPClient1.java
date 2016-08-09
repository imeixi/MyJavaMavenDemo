package cn.imeixi.java.net;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;



public class TCPClient1 {
	
	/**
	 * TCP 客户端程序
	 * 服务端使用 nc 模拟： nc -l -p 9090 (循环监听 9090 端口)
	 */

	public void TCPClient1(){
		
		String host = "172.31.53.59";
		host = "localhost";
		int port = 9090;
		Socket socket = null;
		OutputStream os = null;
		try {
			socket = new Socket(InetAddress.getByName(host), port);

			os = socket.getOutputStream();
			for(int i = 0;i < 10;i++){
				os.write("Hello,this is from eclipse Client....\r\n".getBytes());
			}
//			socket.shutdownOutput();
		}  catch (IOException e) {
			e.printStackTrace();
		}finally {
			if(os != null){
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
			if(socket != null){
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		}
		
		
	}
}

