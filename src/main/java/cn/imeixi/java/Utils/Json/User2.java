package cn.imeixi.java.Utils.Json;

import java.util.Arrays;

/**
 * @Name: User.java
 * @Description: 处理Json的javabean
 * @Author: ZhengAH（作者）
 * @Version: V1.00 （版本号）
 * @Create Date: 2016年8月31日下午4:41:06
 */
public class User2 {
	public enum Gender {
		MALE, FEMALE
	};

	public static class Name {
		private String first, last;

		public String getFirst() {
			return first;
		}

		public String getLast() {
			return last;
		}

		public void setFirst(String s) {
			first = s;
		}

		public void setLast(String s) {
			last = s;
		}
	}

	private Gender gender;
	private Name name;
	private boolean verified;
	private byte[] userImage;

	public Name getName() {
		return name;
	}

	public boolean isVerified() {
		return verified;
	}

	public Gender getGender() {
		return gender;
	}

	public byte[] getUserImage() {
		return userImage;
	}

	public void setName(Name n) {
		name = n;
	}

	public void setVerified(boolean b) {
		verified = b;
	}

	public void setGender(Gender g) {
		gender = g;
	}

	public void setUserImage(byte[] b) {
		userImage = b;
	}

	@Override
	public String toString() {
		return "User [gender=" + gender + ", name=" + name + ", verified=" + verified + ", userImage="
				+ Arrays.toString(userImage) + "]";
	}

}
