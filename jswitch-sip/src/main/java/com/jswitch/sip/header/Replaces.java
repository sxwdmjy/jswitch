package com.jswitch.sip.header;


import com.jswitch.sip.CallIdentifier;
import com.jswitch.sip.ParameterNames;
import lombok.Getter;

import java.text.ParseException;

import static com.jswitch.common.constant.Separators.SEMICOLON;

public class Replaces extends ParametersHeader implements ExtensionHeader, ReplacesHeader {

    private static final long serialVersionUID = 8765762413224043300L;

    public static final String NAME = "Replaces";


    public CallIdentifier callIdentifier;
    @Getter
    public String callId;


    public Replaces() {
        super(NAME);
    }


    public Replaces(String callId) throws IllegalArgumentException {
        super(NAME);
        this.callIdentifier = new CallIdentifier(callId);
    }


    public StringBuilder encodeBody(StringBuilder retval) {
        if (callId == null)
            return retval;
        else {
            retval.append(callId);
            if (!parameters.isEmpty()) {
                retval.append(SEMICOLON);
                parameters.encode(retval);
            }
            return retval;
        }
    }


    public CallIdentifier getCallIdentifer() {
        return callIdentifier;
    }


    public void setCallId(String cid) {
        callId = cid;
    }


    public void setCallIdentifier(CallIdentifier cid) {
        callIdentifier = cid;
    }

    public String getToTag() {
        if (parameters == null)
            return null;
        return getParameter(ParameterNames.TO_TAG);
    }

    public void setToTag(String t) throws ParseException {
        if (t == null)
            throw new NullPointerException("null tag ");
        else if (t.trim().isEmpty())
            throw new ParseException("bad tag", 0);
        this.setParameter(ParameterNames.TO_TAG, t);
    }

    public boolean hasToTag() {
        return hasParameter(ParameterNames.TO_TAG);
    }


    public void removeToTag() {
        parameters.delete(ParameterNames.TO_TAG);
    }

    public String getFromTag() {
        if (parameters == null)
            return null;
        return getParameter(ParameterNames.FROM_TAG);
    }

    public void setFromTag(String t) throws ParseException {
        if (t == null)
            throw new NullPointerException("null tag ");
        else if (t.trim().isEmpty())
            throw new ParseException("bad tag", 0);
        this.setParameter(ParameterNames.FROM_TAG, t);
    }

    public boolean hasFromTag() {
        return hasParameter(ParameterNames.FROM_TAG);
    }


    public void removeFromTag() {
        parameters.delete(ParameterNames.FROM_TAG);
    }



    public void setValue(String value) throws ParseException {
        throw new ParseException(value,0);

    }


}


