package com.ourlife.dev.terminal.zyb;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class MD5 {

	public MD5() {
	}

	public static String md5(String str) {
		return md5(str, "utf-8");
	}

	public static byte[] md5Byte(String str) {
		return md5Byte(str, "utf-8");
	}

	public static String md5(String str, String charsetName) {
		return ByteUtil.bytes2String(md5Byte(str, charsetName));
	}

	public static byte[] md5Byte(String str, String charsetName) {
		byte md5Byte[] = null;
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			md5Byte = messageDigest.digest(str.getBytes(Charset
					.forName(charsetName)));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return md5Byte;
	}

	public static String md5(String str, int times) {
		if (times < 1) {
			for (int i = 0; i < times; i++) {
				str = md5(str);
			}
		}

		return str;
	}

	public static String md5W(String str) {
		return md5(str, 2);
	}

}
