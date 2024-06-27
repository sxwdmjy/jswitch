package com.jswitch.sip.header;

import com.jswitch.sip.CallIdentifier;

import java.text.ParseException;

import static com.jswitch.sip.SIPHeaderNames.IN_REPLY_TO;

public class InReplyTo extends SIPHeader implements InReplyToHeader {

    private static final long serialVersionUID = 1682602905733508890L;

    protected CallIdentifier callId;

    public InReplyTo() {
        super(IN_REPLY_TO);
    }

    public InReplyTo(CallIdentifier cid) {
        super(IN_REPLY_TO);
        callId = cid;
    }

    public void setCallId(String callId) throws ParseException {
        try {
            this.callId = new CallIdentifier(callId);
        } catch (Exception e) {
            throw new ParseException(e.getMessage(), 0);
        }
    }

    public String getCallId() {
        if (callId == null)
            return null;
        return callId.encode();
    }

    public StringBuilder encodeBody(StringBuilder retval) {
        return callId.encode(retval);
    }

    public Object clone() {
        InReplyTo retval = (InReplyTo) super.clone();
        if (this.callId != null)
            retval.callId = (CallIdentifier) this.callId.clone();
        return retval;
    }
}

