package com.jswitch.sip.parse;


import com.jswitch.sip.header.PProfileKey;
import com.jswitch.sip.header.SIPHeader;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;

@Slf4j
public class PProfileKeyParser extends AddressParametersParser implements TokenTypes{

    protected PProfileKeyParser(Lexer lexer) {
        super(lexer);

    }

    public PProfileKeyParser(String profilekey){
        super(profilekey);
    }

    public SIPHeader parse() throws ParseException {
        if (log.isDebugEnabled())
            dbg_enter("PProfileKey.parse");
        try {

            this.lexer.match(TokenTypes.P_PROFILE_KEY);
            this.lexer.SPorHT();
            this.lexer.match(':');
            this.lexer.SPorHT();

            PProfileKey p = new PProfileKey();
            super.parse(p);
            return p;

        } finally {
            if (log.isDebugEnabled())
                dbg_leave("PProfileKey.parse");
            }


    }

}
