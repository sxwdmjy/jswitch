package com.jswitch.sip.parse;


import com.jswitch.sip.header.SIPHeader;
import com.jswitch.sip.header.SecurityClient;
import com.jswitch.sip.header.SecurityClientList;

import java.text.ParseException;

public class SecurityClientParser extends SecurityAgreeParser
{

    public SecurityClientParser(String security)
    {
        super(security);
    }

    protected SecurityClientParser(Lexer lexer)
    {
        super(lexer);
    }


    public SIPHeader parse() throws ParseException
    {
        dbg_enter("SecuriryClient parse");
        try {

            headerName(TokenTypes.SECURITY_CLIENT);
            SecurityClient secClient = new SecurityClient();
            SecurityClientList secClientList = (SecurityClientList) super.parse(secClient);
            return secClientList;


        } finally {
            dbg_leave("SecuriryClient parse");
        }
    }





}


