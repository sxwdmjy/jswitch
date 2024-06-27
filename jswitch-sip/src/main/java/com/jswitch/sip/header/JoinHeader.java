package com.jswitch.sip.header;


import java.text.ParseException;

public interface JoinHeader extends Parameters, Header {


    public void setToTag(String tag) throws ParseException;

    public void setFromTag(String tag) throws ParseException;


    public String getToTag();

    public String getFromTag();


    public void setCallId(String callId) throws ParseException;


    public String getCallId();


    public final static String NAME = "Join";

}


