package com.duguang.baseanimation.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 生成MD5
 * @author Administrator
 *
 */
public class Md5Utils {
	public static String encode(String password) {
		try {
			MessageDigest digest = MessageDigest.getInstance("MD5");
			byte[] result = digest.digest(password.getBytes());
			StringBuilder sb = new StringBuilder();
			for (byte b : result) {
				int number = b & 0xff ; 
				String hex = Integer.toHexString(number);
				if (hex.length() == 1) {
					sb.append("0");
				}
				sb.append(hex);
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return "";
			// can't reach
		}
	}
}
