package com.jswitch.sip.parse;

import com.jswitch.sip.header.SIPHeader;
import com.jswitch.sip.header.SecurityServer;
import com.jswitch.sip.header.SecurityServerList;

import java.text.ParseException;

public class SecurityServerParser extends SecurityAgreeParser
{

    public SecurityServerParser(String security)
    {
        super(security);
    }

    protected SecurityServerParser(Lexer lexer)
    {
        super(lexer);
    }


    public SIPHeader parse() throws ParseException
    {
        dbg_enter("SecuriryServer parse");
        try {

            headerName(TokenTypes.SECURITY_SERVER);
            SecurityServer secServer = new SecurityServer();
            SecurityServerList secServerList = (SecurityServerList) super.parse(secServer);
            return secServerList;

        } finally {
            dbg_leave("SecuriryServer parse");
        }
    }


}


