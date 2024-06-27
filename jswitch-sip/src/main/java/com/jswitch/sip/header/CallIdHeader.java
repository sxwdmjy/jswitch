package com.jswitch.sip.header;

import java.text.ParseException;

/**
 * @author danmo
 * @date 2024-06-18 15:13
 **/
public interface CallIdHeader extends Header {

    public void setCallId(String callId) throws ParseException;

    public String getCallId();

    public boolean equals(Object obj);


    public final static String NAME = "Call-ID";
}
