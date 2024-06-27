package com.jswitch.sip.parse;

import com.jswitch.sip.header.PPreferredIdentity;
import com.jswitch.sip.header.SIPHeader;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;

@Slf4j
public class PPreferredIdentityParser extends AddressParametersParser implements TokenTypes {

    public PPreferredIdentityParser(String preferredIdentity) {
        super(preferredIdentity);

    }


    protected PPreferredIdentityParser(Lexer lexer) {
        super(lexer);

    }

    public SIPHeader parse() throws ParseException {

        if (log.isDebugEnabled())
            dbg_enter("PreferredIdentityParser.parse");

        try {
            this.lexer.match(TokenTypes.P_PREFERRED_IDENTITY);
            this.lexer.SPorHT();
            this.lexer.match(':');
            this.lexer.SPorHT();

            PPreferredIdentity p = new PPreferredIdentity();
            super.parse(p);
            return p;
        } finally {
            if (log.isDebugEnabled())
                dbg_leave("PreferredIdentityParser.parse");
        }


    }

}
