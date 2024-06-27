package com.jswitch.sip.header;

import com.jswitch.sip.ParameterNames;

import java.text.ParseException;

public class Priority extends SIPHeader implements PriorityHeader {


    private static final long serialVersionUID = 3837543366074322106L;


    public static final String EMERGENCY = ParameterNames.EMERGENCY;


    public static final String URGENT = ParameterNames.URGENT;


    public static final String NORMAL = ParameterNames.NORMAL;


    public static final String NON_URGENT = ParameterNames.NON_URGENT;

    protected String priority;


    public Priority() {
        super(NAME);
    }


    public StringBuilder encodeBody(StringBuilder buffer) {
        return buffer.append(priority);
    }


    public String getPriority() {
        return priority;
    }


    public void setPriority(String p) throws ParseException {
        if (p == null)
            throw new NullPointerException("SIP Exception, Priority, setPriority(), the priority parameter is null");
        priority = p;
    }
}
