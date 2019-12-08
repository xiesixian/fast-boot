package com.xiesx.gotv.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

import org.apache.commons.codec.digest.DigestUtils;

public class DigestUtil {
	private final static char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

	public static String byteArrayToHexString(byte[] b) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			resultSb.append(byteToHexString(b[i]));
		}
		return resultSb.toString();
	}

	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n = 256 + n;
		int d1 = n / 16;
		int d2 = n % 16;
		return String.valueOf(hexDigits[d1]) + String.valueOf(hexDigits[d2]);
	}

	/**
	 * 对数据进行指定算法的数据摘要
	 *
	 * @param algorithm
	 *            算法名，如MD2, MD5, SHA-1, SHA-256, SHA-512
	 * @param data
	 *            待计算的数据
	 * @param charset
	 *            字符串的编码
	 * @return 摘要结果
	 */
	public static String digestHex(String algorithm, String data, String charset) throws UnsupportedEncodingException {
		byte[] digest = DigestUtils.getDigest(algorithm).digest(data.getBytes(charset));
		return byteArrayToHexString(digest);
	}

	/**
	 * 对数据进行MD5算法的数据摘要
	 * 
	 * @param origin
	 *            待计算的数据
	 */
	public static String MD5Encode(String origin) {
		String resultString = null;
		try {
			resultString = new String(origin);
			MessageDigest md = DigestUtils.getMd5Digest();
			resultString = byteArrayToHexString(md.digest(resultString.getBytes()));
		} catch (Exception ex) {

		}
		return resultString;
	}

	/**
	 * 对文件进行MD5算法的数据摘要
	 * 
	 * @param file
	 *            待计算的文件
	 */
	public static String digestHex(File file) {
		FileInputStream fis = null;
		try {
			MessageDigest md = DigestUtils.getMd5Digest();
			fis = new FileInputStream(file);
			byte[] buffer = new byte[2048];
			int length = -1;
			// long s = System.currentTimeMillis();
			while ((length = fis.read(buffer)) != -1) {
				md.update(buffer, 0, length);
			}
			// 32位加密
			byte[] b = md.digest();
			return byteArrayToHexString(b);
			// 16位加密
			// return buf.toString().substring(8, 24);
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		} finally {
			try {
				if (null != fis) {
					fis.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
}
