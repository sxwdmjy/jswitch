package com.jswitch.sip.exception;

import com.jswitch.sip.SipMessage;
import com.jswitch.sip.header.SIPHeader;

import java.text.ParseException;

/**
 * @author danmo
 * @date 2024-06-18 15:25
 **/
public class SIPDuplicateHeaderException extends ParseException {

    private static final long serialVersionUID = 8241107266407879291L;
    protected SIPHeader sipHeader;
    protected SipMessage sipMessage;

    public SIPDuplicateHeaderException(String msg) {
        super(msg, 0);
    }

    public SipMessage getSIPMessage() {
        return sipMessage;
    }

    public SIPHeader getSIPHeader() {
        return sipHeader;
    }

    public void setSIPHeader(SIPHeader sipHeader) {
        this.sipHeader = sipHeader;
    }

    public void setSIPMessage(SipMessage sipMessage) {
        this.sipMessage = sipMessage;
    }
}
