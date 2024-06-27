package com.jswitch.sip.parse;


import com.jswitch.sip.SIPHeaderNames;
import com.jswitch.sip.core.Token;
import com.jswitch.sip.header.Require;
import com.jswitch.sip.header.RequireList;
import com.jswitch.sip.header.SIPHeader;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;

@Slf4j
public class RequireParser extends HeaderParser {


    public RequireParser(String require) {
        super(require);
    }


    protected RequireParser(Lexer lexer) {
        super(lexer);
    }


    public SIPHeader parse() throws ParseException {
        RequireList requireList = new RequireList();
        if (log.isDebugEnabled())
            dbg_enter("RequireParser.parse");

        try {
            headerName(TokenTypes.REQUIRE);

            while (lexer.lookAhead(0) != '\n') {
                Require r = new Require();
                r.setHeaderName(SIPHeaderNames.REQUIRE);

                // Parsing the option tag
                this.lexer.match(TokenTypes.ID);
                Token token = lexer.getNextToken();
                r.setOptionTag(token.getTokenValue());
                this.lexer.SPorHT();

                requireList.add(r);

                while (lexer.lookAhead(0) == ',') {
                    this.lexer.match(',');
                    this.lexer.SPorHT();

                    r = new Require();

                    // Parsing the option tag
                    this.lexer.match(TokenTypes.ID);
                    token = lexer.getNextToken();
                    r.setOptionTag(token.getTokenValue());
                    this.lexer.SPorHT();

                    requireList.add(r);
                }

            }
        } finally {
            if (log.isDebugEnabled())
                dbg_leave("RequireParser.parse");
        }

        return requireList;
    }
}

