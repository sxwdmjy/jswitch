package com.jswitch.sip.parse;


import com.jswitch.sip.header.PAssertedIdentity;
import com.jswitch.sip.header.PAssertedIdentityList;
import com.jswitch.sip.header.SIPHeader;
import com.jswitch.sip.header.SIPHeaderNamesIms;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;

@Slf4j
public class PAssertedIdentityParser extends AddressParametersParser implements TokenTypes{


    public PAssertedIdentityParser(String assertedIdentity) {
        super(assertedIdentity);
        this.allowParameters = false;

    }

    protected PAssertedIdentityParser(Lexer lexer) {
        super(lexer);
        this.allowParameters = false;

    }


    public SIPHeader parse() throws ParseException {

        if (log.isDebugEnabled())
            dbg_enter("AssertedIdentityParser.parse");

        PAssertedIdentityList assertedIdList = new PAssertedIdentityList();

        try {

            headerName(TokenTypes.P_ASSERTED_IDENTITY);

            PAssertedIdentity pai = new PAssertedIdentity();
            pai.setHeaderName(SIPHeaderNamesIms.P_ASSERTED_IDENTITY);

            super.parse(pai);
            assertedIdList.add(pai);

            this.lexer.SPorHT();
            while (lexer.lookAhead(0) == ',')
            {
                this.lexer.match(',');
                this.lexer.SPorHT();

                pai = new PAssertedIdentity();
                super.parse(pai);
                assertedIdList.add(pai);

                this.lexer.SPorHT();
            }
            this.lexer.SPorHT();
            this.lexer.match('\n');

            return assertedIdList;

        }

        finally {
            if (log.isDebugEnabled())
                dbg_leave("AssertedIdentityParser.parse");
            }
    }
}
