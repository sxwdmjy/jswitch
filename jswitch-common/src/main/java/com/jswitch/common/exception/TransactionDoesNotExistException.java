package com.jswitch.common.exception;



public class TransactionDoesNotExistException extends SipException {




    public TransactionDoesNotExistException(){

        super();

    }

    public TransactionDoesNotExistException(String message) {

        super(message);

    }


    public TransactionDoesNotExistException(String message, Throwable cause) {

        super(message, cause);

    }



}

