package com.neya.love.finder.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class StringUtil {

	/*public static String md5(String text) { // TODO rewrite this method!!!  
		byte[] convertedText = null;
		try {
			byte[] textToConvert = text.getBytes("UTF-8");
			MessageDigest md = MessageDigest.getInstance("MD5");
		    md.update(textToConvert);
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
	}*/
	
	/**
	 * Get MD5 checksum from String
	 * 
	 * @param text
	 *            to convert in MD5
	 * @return MD5 checksum of text
	 *
	 * @author <a href="mailto:yanev93@gmail.com">Nikolay Yanev</a>
	 */
	public static String md5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            String hashtext = number.toString(16);
            // Now we need to zero pad it if you actually want the full 32 chars.
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
