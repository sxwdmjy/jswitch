package com.jswitch.sip.parse;

import com.jswitch.sip.SipMessage;

import java.text.ParseException;

public interface ParseExceptionListener {

    public void handleException(ParseException ex, SipMessage sipMessage, Class headerClass, String headerText, String messageText) throws ParseException;
}

