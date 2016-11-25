package cn.imeixi.java.ImgHandler;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import ucar.ma2.Array;

public class Base64Test {
	public static void main(String[] args) {

		String imgFile = "img.jpg";
		String Base64Str = GetImageStr(imgFile);
//		System.out.println(Base64Str);
		GenerateImage(Base64Str);

	}

	// 图片转化成Base64字符串
	public static String GetImageStr(String imgFile) {

		// 将图片转化为字节数组字符串，并对其进行Base64编码处理
		// String imgFile = "img.jpg"; //待处理的图片
		InputStream in = null;
		byte[] data = null;
		byte[] tmp = new byte[1024000];

		// 读取图片字节数组
		try {
			in = new FileInputStream(imgFile);
			data = new byte[in.available()];
			// data = new byte[1024];
			in.read(data);
			System.out.println(Arrays.toString(data));
			
			in.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		// 对字节组Base64编码
		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encode(copyBytes(data,tmp));

	}

	// Base64字符串转成图片
	public static boolean GenerateImage(String imgStr) {
		
		
		
//		for(int i = 0; i < tmp.length; i++){
//			tmp[i] = -128; // byte的赋值范围是 -128~127
//		}
		
		
		

		// 对字节数组字符串进行Base64解码并生成图片
		if (imgStr == null) // 图片数据为空
			return false;

		BASE64Decoder decoder = new BASE64Decoder();

		try {

			//base64解码
			byte[] b = decoder.decodeBuffer(imgStr);
			System.out.println(Arrays.toString(b));

			for (int i = 0; i < b.length; i++) {
				if (b[i] < 0) {
					//调整异常数据
					b[i] += 256;
				}
			}
			
			//生成jpeg图片
			String imgFileCopy = "imgCopy.jpg";
			OutputStream oStream = new FileOutputStream(imgFileCopy);
			oStream.write(b);
			oStream.flush();
			oStream.close();
			return true;
			

		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

	}
	
	public static byte[] copyBytes(byte[] b1,byte[] b2){
		byte[] b3 = new byte[b1.length + b2.length];
		System.arraycopy(b1, 0, b3, 0, b1.length);
		System.arraycopy(b2, 0, b3, b1.length, b2.length);
		return b3;
	}
	

}
