package com.neya.love.finder.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class StringUtil {

	/**
	 * 
	 * @param text
	 *            to convert in MD5
	 * @return MD5 checksum of text
	 */
	@SuppressWarnings("finally")
	public static String md5(String text) {
		byte[] convertedText = null;
		try {
			byte[] textToConvert = text.getBytes("UTF-8");
			MessageDigest md = MessageDigest.getInstance("MD5");
			convertedText = md.digest(textToConvert);

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} finally {
			if (convertedText.length == 0)
				return null;

			return new String(convertedText);
		}
	}
}
