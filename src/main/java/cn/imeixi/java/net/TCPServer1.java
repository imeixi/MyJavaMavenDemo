package cn.imeixi.java.net;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;


public class TCPServer1 {
	
	/**
	 * TCP 服务端 Server
	 * 使用 nc -vv ip port 连接发送数据
	 */

	public void TCPServer1(){
		
		ServerSocket serverSocket = null;
		Socket socket = null;
		InputStream is = null;
		try {
			serverSocket = new ServerSocket(9090);
//		while(true){
				socket = serverSocket.accept();
				is = socket.getInputStream();
				byte[] b = new byte[1024];
				int len = 0;
				while((len = is.read(b)) != -1){
					String str = new String(b, 0, len);
					System.out.println(str);
//				}
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			if(is != null){
				try {
					is.close();
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
			if(serverSocket !=null){
				try {
					serverSocket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		}
		
	}

}

