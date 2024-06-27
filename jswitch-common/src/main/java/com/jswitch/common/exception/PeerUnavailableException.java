package com.jswitch.common.exception;

/**
 * @author danmo
 * @date 2024-06-18 11:33
 **/
public class PeerUnavailableException extends SipException{

    public PeerUnavailableException() {
        super();
    }


    public PeerUnavailableException(String message) {
        super(message);
    }


    public PeerUnavailableException(String message, Throwable cause) {
        super(message, cause);
    }
}
