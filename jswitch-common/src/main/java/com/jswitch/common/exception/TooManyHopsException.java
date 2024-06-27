package com.jswitch.common.exception;

/**
 * @author danmo
 * @date 2024-06-18 15:17
 **/
public class TooManyHopsException extends SipException{

    public TooManyHopsException(){
        super();
    }


    public TooManyHopsException(String message) {
        super(message);
    }


    public TooManyHopsException(String message, Throwable cause) {
        super(message, cause);
    }
}
