package com.jswitch.sip.parse;

import com.jswitch.sip.core.Token;
import com.jswitch.sip.header.SIPHeader;
import com.jswitch.sip.header.SIPIfMatch;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;

@Slf4j
public class SIPIfMatchParser extends HeaderParser {


    public SIPIfMatchParser(String etag) {
        super(etag);
    }


    protected SIPIfMatchParser(Lexer lexer) {
        super(lexer);
    }


    public SIPHeader parse() throws ParseException {

        if (log.isDebugEnabled())
            dbg_enter("SIPIfMatch.parse");

        SIPIfMatch sipIfMatch = new SIPIfMatch();
        try {
            headerName(TokenTypes.SIP_IF_MATCH);

            this.lexer.SPorHT();
            this.lexer.match(TokenTypes.ID);
            Token token = lexer.getNextToken();

            sipIfMatch.setETag(token.getTokenValue());

            this.lexer.SPorHT();
            this.lexer.match('\n');

            return sipIfMatch;
        } finally {
            if (log.isDebugEnabled())
                dbg_leave("SIPIfMatch.parse");
        }
    }
}
