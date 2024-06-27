package com.jswitch.sip.parse;


import com.jswitch.sip.core.Token;
import com.jswitch.sip.header.SIPETag;
import com.jswitch.sip.header.SIPHeader;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;

@Slf4j
public class SIPETagParser extends HeaderParser {

    public SIPETagParser(String etag) {
        super(etag);
    }


    protected SIPETagParser(Lexer lexer) {
        super(lexer);
    }


    public SIPHeader parse() throws ParseException {

        if (log.isDebugEnabled())
            dbg_enter("SIPEtag.parse");

        SIPETag sipEtag = new SIPETag();
        try {
            headerName(TokenTypes.SIP_ETAG);

            this.lexer.SPorHT();
            this.lexer.match(TokenTypes.ID);
            Token token = lexer.getNextToken();

            sipEtag.setETag(token.getTokenValue());

            this.lexer.SPorHT();
            this.lexer.match('\n');

            return sipEtag;
        } finally {
            if (log.isDebugEnabled())
                dbg_leave("SIPEtag.parse");
        }
    }
}
