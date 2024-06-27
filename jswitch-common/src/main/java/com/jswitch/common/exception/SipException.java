package com.jswitch.common.exception;

/**
 * @author danmo
 * @date 2024-06-13 18:28
 **/

public class SipException extends RuntimeException {

    public SipException() {
        super();
    }

    public SipException(String message) {
        super(message);
    }


    public SipException(String message, Throwable cause) {
        super(message, cause);
    }

    public SipException(Throwable cause) {
        super(cause);
    }
}
