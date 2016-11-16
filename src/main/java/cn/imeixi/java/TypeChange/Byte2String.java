package cn.imeixi.java.TypeChange;

public class Byte2String {

	public static void main(String[] args) throws Exception {

		String str = "中文的测试";
		byte[] buff = str.getBytes();
		StringBuffer sbuf = new StringBuffer();
		for (int i = 0; i < buff.length; i++) {
			sbuf.append(buff[i]);
		}
		System.out.println(sbuf.toString());

		String tmp = byte2String(buff);
		System.out.println(tmp);
		System.out.println(new String(String2byte(tmp)));
	}

	public static String byte2String(byte[] buff) {
		StringBuffer sbuf = new StringBuffer();
		for (int i = 0; i < buff.length; i++) {
			int tmp = buff[i] & 0XFF;
			String str = Integer.toHexString(tmp);
			if (str.length() == 1) {
				sbuf.append("0" + str);
			} else {
				sbuf.append(str);
			}

		}
		return sbuf.toString();
	}

	public static byte[] String2byte(String str) {
		byte[] result = new byte[str.length() / 2];
		int index = 0;
		for (int i = 0; i < str.length(); i += 2) {
			result[index++] = (byte) Integer.parseInt(str.substring(i, i + 2),
					16);
		}
		return result;
	}
}

