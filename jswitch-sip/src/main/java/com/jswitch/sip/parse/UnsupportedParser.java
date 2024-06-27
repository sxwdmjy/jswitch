package com.jswitch.sip.parse;


import com.jswitch.sip.SIPHeaderNames;
import com.jswitch.sip.core.Token;
import com.jswitch.sip.header.SIPHeader;
import com.jswitch.sip.header.Unsupported;
import com.jswitch.sip.header.UnsupportedList;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;

@Slf4j
public class UnsupportedParser extends HeaderParser {


    public UnsupportedParser(String unsupported) {
        super(unsupported);
    }


    protected UnsupportedParser(Lexer lexer) {
        super(lexer);
    }


    public SIPHeader parse() throws ParseException {
        UnsupportedList unsupportedList = new UnsupportedList();
        if (log.isDebugEnabled())
            dbg_enter("UnsupportedParser.parse");

        try {
            headerName(TokenTypes.UNSUPPORTED);

            while (lexer.lookAhead(0) != '\n') {
                this.lexer.SPorHT();
                Unsupported unsupported = new Unsupported();
                unsupported.setHeaderName(SIPHeaderNames.UNSUPPORTED);

                // Parsing the option tag
                this.lexer.match(TokenTypes.ID);
                Token token = lexer.getNextToken();
                unsupported.setOptionTag(token.getTokenValue());
                this.lexer.SPorHT();

                unsupportedList.add(unsupported);

                while (lexer.lookAhead(0) == ',') {
                    this.lexer.match(',');
                    this.lexer.SPorHT();

                    unsupported = new Unsupported();

                    // Parsing the option tag
                    this.lexer.match(TokenTypes.ID);
                    token = lexer.getNextToken();
                    unsupported.setOptionTag(token.getTokenValue());
                    this.lexer.SPorHT();

                    unsupportedList.add(unsupported);
                }

            }
        } finally {
            if (log.isDebugEnabled())
                dbg_leave("UnsupportedParser.parse");
        }

        return unsupportedList;
    }

}
