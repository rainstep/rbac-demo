package com.example.rbacdemo.common.util;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

public class AlgorithmUtils {
	private static Logger logger = LoggerFactory.getLogger(AlgorithmUtils.class);

	/// <summary>
    /// 加解密密钥
    /// </summary>
    private static String key = "abcdef1234567890";

	/**
     * ase加密
     * @param text
     * @return
     */
    public static String aesEncrypt(String text) {
    	String result = null;
        if (text == null || key == null) {
			return null;
		}
		try {
			KeyGenerator generator = KeyGenerator.getInstance("AES");
			SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
			secureRandom.setSeed(key.getBytes());
			generator.init(128, secureRandom);
			SecretKey secretKey = generator.generateKey();
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(secretKey.getEncoded(), "AES"));
	        byte[] bytes = cipher.doFinal(text.getBytes("utf-8"));
	        result = Base64.getEncoder().encodeToString(bytes);
		} catch (Exception e) {
			logger.error(ExceptionUtils.getRootCauseMessage(e));
		}
        return result;
    }

    /**
     * ase解密
     * @param text
     * @return
     */
    public static String aesDecrypt(String text) {
    	String result = null;
        if (text == null || key == null) {
			return null;
		}
        try {
			KeyGenerator generator = KeyGenerator.getInstance("AES");
			SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
			generator.init(128, secureRandom);
			secureRandom.setSeed(key.getBytes());
			SecretKey secretKey = generator.generateKey();
	        Cipher cipher = Cipher.getInstance("AES");
	        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(secretKey.getEncoded(), "AES"));
	        byte[] bytes = Base64.getDecoder().decode(text);
	        bytes = cipher.doFinal(bytes);
	        result = new String(bytes, "utf-8");
        } catch (Exception e) {
			logger.error(ExceptionUtils.getRootCauseMessage(e));
		}
        return result;
    }
    
    /**
     * md5加密
     * @param text
     * @return
     */
    public static String md5Encrypt(String text) {
    	String result = null;
        try{
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] results = md.digest(text.getBytes("utf-8"));
			StringBuffer buff=new StringBuffer();
			for(byte b: results){
				// String.format("%02x", b) : 转十六进制不足位补0
				buff.append(String.format("%02x", b));     
			}
			result = buff.toString();
		}catch(Exception e){
			logger.error(ExceptionUtils.getRootCauseMessage(e));
		}
        return result;
   }
}
