package com.jswitch.sip.header;


import java.text.ParseException;


public class SecurityVerify extends SecurityAgree implements SecurityVerifyHeader, ExtensionHeader
{


    public SecurityVerify()
    {
        super(SecurityVerifyHeader.NAME);

    }


    public void setValue(String value) throws ParseException
    {
        throw new ParseException(value,0);
    }


}
