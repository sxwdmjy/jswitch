package com.jswitch.sip.header;


import java.text.ParseException;



public class SecurityServer extends SecurityAgree implements SecurityServerHeader, ExtensionHeader
{


    public SecurityServer()
    {
        super(SecurityServerHeader.NAME);

    }


    public void setValue(String value) throws ParseException
    {
        throw new ParseException(value,0);
    }



}
