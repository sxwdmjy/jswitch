
package com.jswitch.sip.parse;

import com.jswitch.common.exception.InvalidArgumentException;
import com.jswitch.sip.core.Token;
import com.jswitch.sip.header.RetryAfter;
import com.jswitch.sip.header.SIPHeader;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;

@Slf4j
public class RetryAfterParser extends HeaderParser {

    public RetryAfterParser(String retryAfter) {
        super(retryAfter);
    }


    protected RetryAfterParser(Lexer lexer) {
        super(lexer);
    }


    public SIPHeader parse() throws ParseException {

        if (log.isDebugEnabled())
            dbg_enter("RetryAfterParser.parse");

        RetryAfter retryAfter = new RetryAfter();
        try {
            headerName(TokenTypes.RETRY_AFTER);

            String value = lexer.number();
            try {
                int ds = Integer.parseInt(value);
                retryAfter.setRetryAfter(ds);
            } catch (NumberFormatException | InvalidArgumentException ex) {
                throw createParseException(ex.getMessage());
            }

            this.lexer.SPorHT();
            if (lexer.lookAhead(0) == '(') {
                String comment = this.lexer.comment();
                retryAfter.setComment(comment);
            }
            this.lexer.SPorHT();

            while (lexer.lookAhead(0) == ';') {
                this.lexer.match(';');
                this.lexer.SPorHT();
                lexer.match(TokenTypes.ID);
                Token token = lexer.getNextToken();
                value = token.getTokenValue();
                if (value.equalsIgnoreCase("duration")) {
                    this.lexer.match('=');
                    this.lexer.SPorHT();
                    value = lexer.number();
                    try {
                        int duration = Integer.parseInt(value);
                        retryAfter.setDuration(duration);
                    } catch (NumberFormatException | InvalidArgumentException ex) {
                        throw createParseException(ex.getMessage());
                    }
                } else {
                    this.lexer.SPorHT();
                    this.lexer.match('=');
                    this.lexer.SPorHT();
                    lexer.match(TokenTypes.ID);
                    Token secondToken = lexer.getNextToken();
                    String secondValue = secondToken.getTokenValue();
                    retryAfter.setParameter(value, secondValue);
                }
                this.lexer.SPorHT();
            }
        } finally {
            if (log.isDebugEnabled())
                dbg_leave("RetryAfterParser.parse");
        }

        return retryAfter;
    }

}
