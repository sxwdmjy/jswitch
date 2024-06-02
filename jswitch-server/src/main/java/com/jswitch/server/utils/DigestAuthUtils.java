package com.jswitch.server.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class DigestAuthUtils {

    public static String calculateMD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : messageDigest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static String calculateResponse(String username, String realm, String password, String nonce, String uri, String method) {
        String ha1 = calculateMD5(username + ":" + realm + ":" + password);
        String ha2 = calculateMD5(method + ":" + uri);
        return calculateMD5(ha1 + ":" + nonce + ":" + ha2);
    }

    private static String md5Hex(String input) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] digest = md.digest(input.getBytes());
        StringBuilder sb = new StringBuilder();
        for (byte b : digest) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
