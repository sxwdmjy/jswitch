package com.jswitch.sip.utils;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.charset.UnsupportedCharsetException;

/**
 * @author danmo
 * @date 2024-06-18 11:31
 **/
public class UriDecoder {

    static Charset utf8CharSet = null;
    static {
        try {
            utf8CharSet = StandardCharsets.UTF_8;
        } catch (UnsupportedCharsetException e) {
            throw new RuntimeException("Problem in decodePath: UTF-8 charset not supported.", e);
        }
    }

    public static String decode(String uri) {
        String uriToWorkOn = uri;
        int indexOfNextPercent = uriToWorkOn.indexOf("%");
        StringBuilder decodedUri = new StringBuilder();

        while(indexOfNextPercent != -1) {
            decodedUri.append(uriToWorkOn.substring(0, indexOfNextPercent));
            if(indexOfNextPercent + 2 < uriToWorkOn.length()) {
                String hexadecimalString = uriToWorkOn.substring(indexOfNextPercent + 1, indexOfNextPercent + 3);
                try {
                    byte hexadecimalNumber = (byte) Integer.parseInt(hexadecimalString, 16);
                    String correspondingCharacter = utf8CharSet.decode(ByteBuffer.wrap(new byte[] {hexadecimalNumber})).toString();
                    decodedUri.append(correspondingCharacter);
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Illegal hex characters in pattern %" + hexadecimalString);
                }
            }
            uriToWorkOn = uriToWorkOn.substring(indexOfNextPercent + 3);
            indexOfNextPercent = uriToWorkOn.indexOf("%");
        }
        decodedUri.append(uriToWorkOn);
        return decodedUri.toString();
    }
}
