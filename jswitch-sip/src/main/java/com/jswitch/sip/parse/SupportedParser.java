package com.jswitch.sip.parse;


import com.jswitch.sip.SIPHeaderNames;
import com.jswitch.sip.core.Token;
import com.jswitch.sip.header.SIPHeader;
import com.jswitch.sip.header.Supported;
import com.jswitch.sip.header.SupportedList;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;

@Slf4j
public class SupportedParser extends HeaderParser {


    public SupportedParser(String supported) {
        super(supported);
    }

    protected SupportedParser(Lexer lexer) {
        super(lexer);
    }


    public SIPHeader parse() throws ParseException {
        SupportedList supportedList = new SupportedList();
        if (log.isDebugEnabled())
            dbg_enter("SupportedParser.parse");

        try {
            Token token;

            headerName(TokenTypes.SUPPORTED);

            this.lexer.SPorHT();
            Supported supported = new Supported();
            supported.setHeaderName(SIPHeaderNames.SUPPORTED);

            if (lexer.lookAhead(0) != '\n') {
                // Parsing the option tag
                this.lexer.match(TokenTypes.ID);
                token = lexer.getNextToken();
                supported.setOptionTag(token.getTokenValue());
                this.lexer.SPorHT();
            }

            supportedList.add(supported);

            while (lexer.lookAhead(0) == ',') {
                this.lexer.match(',');
                this.lexer.SPorHT();

                supported = new Supported();

                // Parsing the option tag
                this.lexer.match(TokenTypes.ID);
                token = lexer.getNextToken();
                supported.setOptionTag(token.getTokenValue());
                this.lexer.SPorHT();

                supportedList.add(supported);
            }
        } finally {
            if (log.isDebugEnabled())
                dbg_leave("SupportedParser.parse");
        }

        return supportedList;
    }

}
