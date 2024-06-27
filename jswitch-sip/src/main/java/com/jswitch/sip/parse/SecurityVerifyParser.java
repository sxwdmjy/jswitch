package com.jswitch.sip.parse;

import com.jswitch.sip.header.SIPHeader;
import com.jswitch.sip.header.SecurityVerify;
import com.jswitch.sip.header.SecurityVerifyList;

import java.text.ParseException;

public class SecurityVerifyParser extends SecurityAgreeParser
{

    public SecurityVerifyParser(String security)
    {
        super(security);
    }

    protected SecurityVerifyParser(Lexer lexer)
    {
        super(lexer);
    }


    public SIPHeader parse() throws ParseException
    {
        dbg_enter("SecuriryVerify parse");
        try {

            headerName(TokenTypes.SECURITY_VERIFY);
            SecurityVerify secVerify = new SecurityVerify();
            SecurityVerifyList secVerifyList = (SecurityVerifyList) super.parse(secVerify);
            return secVerifyList;
        } finally {
            dbg_leave("SecuriryVerify parse");
        }
    }





}


