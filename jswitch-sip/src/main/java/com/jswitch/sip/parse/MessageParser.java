package com.jswitch.sip.parse;

import com.jswitch.sip.SipMessage;

import java.text.ParseException;

public interface MessageParser {

	SipMessage parseSIPMessage(byte[] msgBytes, boolean readBody, boolean strict, ParseExceptionListener exhandler) throws ParseException;

}
