package com.jswitch.common.utils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * @author danmo
 * @date 2024-06-03 13:49
 **/
public class AESUtils {
    private static final String ALGORITHM = "AES";
    private static final int KEY_SIZE = 256;
    private static final String SECURITY_KEY = "gAvBWhsxWgHWYHSY744m0opdvYvHd/b6Ooy/DKTJEeY=";

    /**
     * 生成密钥
     *
     * @return Base64编码的密钥字符串
     * @throws Exception
     */
    private static String generateKey() throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance(ALGORITHM);
        keyGen.init(KEY_SIZE, new SecureRandom());
        SecretKey secretKey = keyGen.generateKey();
        return Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }

    /**
     * 加密
     *
     * @param data 待加密数据
     * @param key  Base64编码的密钥字符串
     * @return 加密后的Base64编码字符串
     * @throws Exception
     */
    private static String encrypt(String data, String key) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(key);
        SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, ALGORITHM);

        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        byte[] encryptedData = cipher.doFinal(data.getBytes("UTF-8"));

        return Base64.getEncoder().encodeToString(encryptedData);
    }

    /**
     * 解密
     *
     * @param encryptedData Base64编码的加密数据
     * @param key           Base64编码的密钥字符串
     * @return 解密后的字符串
     * @throws Exception
     */
    private static String decrypt(String encryptedData, String key) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(key);
        SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, ALGORITHM);

        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
        byte[] decryptedData = cipher.doFinal(Base64.getDecoder().decode(encryptedData));

        return new String(decryptedData, "UTF-8");
    }

    public static String encrypt(String data) throws Exception {
        return encrypt(data, SECURITY_KEY);
    }

    public static String decrypt(String encryptedData) throws Exception {
        return decrypt(encryptedData, SECURITY_KEY);
    }

    public static void main(String[] args) throws Exception {
        String encrypt = encrypt("123456");
        System.out.println(encrypt);
        System.out.println(decrypt(encrypt));
    }
}
