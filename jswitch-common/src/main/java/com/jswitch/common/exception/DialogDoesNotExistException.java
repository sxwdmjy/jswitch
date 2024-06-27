package com.jswitch.common.exception;





public class DialogDoesNotExistException extends SipException {


    public DialogDoesNotExistException(){

        super();

    }



    public DialogDoesNotExistException(String message) {

        super(message);

    }


    public DialogDoesNotExistException(String message, Throwable cause) {

        super(message, cause);

    }



}

