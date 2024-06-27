
package com.jswitch.sip.parse;


import com.jswitch.common.exception.InvalidArgumentException;
import com.jswitch.sip.SIPHeaderNames;
import com.jswitch.sip.core.Token;
import com.jswitch.sip.header.SIPHeader;
import com.jswitch.sip.header.Warning;
import com.jswitch.sip.header.WarningList;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;

@Slf4j
public class WarningParser extends HeaderParser {


    public WarningParser(String warning) {
        super(warning);
    }


    protected WarningParser(Lexer lexer) {
        super(lexer);
    }


    public SIPHeader parse() throws ParseException {
        WarningList warningList = new WarningList();
        if (log.isDebugEnabled())
            dbg_enter("WarningParser.parse");

        try {
            headerName(TokenTypes.WARNING);

            while (lexer.lookAhead(0) != '\n') {
                Warning warning = new Warning();
                warning.setHeaderName(SIPHeaderNames.WARNING);

                // Parsing the 3digits code
                this.lexer.match(TokenTypes.ID);
                Token token = lexer.getNextToken();
                try {
                    int code = Integer.parseInt(token.getTokenValue());
                    warning.setCode(code);
                } catch (NumberFormatException | InvalidArgumentException ex) {
                    throw createParseException(ex.getMessage());
                }
                this.lexer.SPorHT();

                this.lexer.match(TokenTypes.ID);
                token = lexer.getNextToken();
                if (lexer.lookAhead(0) == ':') {
                    this.lexer.match(':');
                    this.lexer.match(TokenTypes.ID);
                    Token token2 = lexer.getNextToken();
                    warning.setAgent(token.getTokenValue() + ":"
                            + token2.getTokenValue());
                } else {
                    warning.setAgent(token.getTokenValue());
                }

                this.lexer.SPorHT();

                String text = this.lexer.quotedString();
                warning.setText(text);
                this.lexer.SPorHT();

                warningList.add(warning);

                while (lexer.lookAhead(0) == ',') {
                    this.lexer.match(',');
                    this.lexer.SPorHT();

                    warning = new Warning();

                    // Parsing the 3digits code
                    this.lexer.match(TokenTypes.ID);
                    Token tok = lexer.getNextToken();
                    try {
                        int code = Integer.parseInt(tok.getTokenValue());
                        warning.setCode(code);
                    } catch (NumberFormatException | InvalidArgumentException ex) {
                        throw createParseException(ex.getMessage());
                    }
                    this.lexer.SPorHT();

                    this.lexer.match(TokenTypes.ID);
                    tok = lexer.getNextToken();

                    if (lexer.lookAhead(0) == ':') {
                        this.lexer.match(':');
                        this.lexer.match(TokenTypes.ID);
                        Token token2 = lexer.getNextToken();
                        warning.setAgent(tok.getTokenValue() + ":"
                                + token2.getTokenValue());
                    } else {
                        warning.setAgent(tok.getTokenValue());
                    }

                    this.lexer.SPorHT();

                    text = this.lexer.quotedString();
                    warning.setText(text);
                    this.lexer.SPorHT();

                    warningList.add(warning);
                }

            }
        } finally {
            if (log.isDebugEnabled())
                dbg_leave("WarningParser.parse");
        }

        return warningList;
    }

}
