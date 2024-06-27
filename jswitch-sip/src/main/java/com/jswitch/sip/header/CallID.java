package com.jswitch.sip.header;

import com.jswitch.sip.CallIdentifier;

import java.text.ParseException;

/**
 * @author danmo
 * @date 2024-06-18 15:13
 **/
public class CallID extends SIPHeader implements CallIdHeader {

    public final static String NAME_LOWER = NAME.toLowerCase();


    protected CallIdentifier callIdentifier;


    public CallID() {
        super(NAME);
    }


    public boolean equals(Object other) {

        if (this == other) return true;

        if (other instanceof CallIdHeader) {
            final CallIdHeader o = (CallIdHeader) other;
            return this.getCallId().equalsIgnoreCase(o.getCallId());
        }
        return false;
    }


    public String encodeBody() {
        return encodeBody(new StringBuilder()).toString();
    }

    public StringBuilder encodeBody(StringBuilder buffer) {
        if (callIdentifier != null)
            callIdentifier.encode(buffer);

        return buffer;
    }


    public String getCallId() {
        return encodeBody();
    }


    public CallIdentifier getCallIdentifer() {
        return callIdentifier;
    }


    public void setCallId(String cid) throws ParseException {
        try {
            callIdentifier = new CallIdentifier(cid);
        } catch (IllegalArgumentException ex) {
            throw new ParseException(cid, 0);
        }
    }

    public void setCallIdentifier(CallIdentifier cid) {
        callIdentifier = cid;
    }


    public CallID(String callId) throws IllegalArgumentException {
        super(NAME);
        this.callIdentifier = new CallIdentifier(callId);
    }

    public Object clone() {
        CallID retval = (CallID) super.clone();
        if (this.callIdentifier != null)
            retval.callIdentifier = (CallIdentifier) this.callIdentifier.clone();
        return retval;
    }
}
