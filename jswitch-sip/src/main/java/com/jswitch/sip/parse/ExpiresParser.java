
package com.jswitch.sip.parse;

import com.jswitch.common.exception.InvalidArgumentException;
import com.jswitch.sip.header.Expires;
import com.jswitch.sip.header.SIPHeader;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;

@Slf4j
public class ExpiresParser extends HeaderParser {

    public ExpiresParser(String text) {
        super(text);
    }


    protected ExpiresParser(Lexer lexer) {
        super(lexer);
    }

    public SIPHeader parse() throws ParseException {
        Expires expires = new Expires();
        if (log.isDebugEnabled())
            dbg_enter("parse");
        try {
            lexer.match(TokenTypes.EXPIRES);
            lexer.SPorHT();
            lexer.match(':');
            lexer.SPorHT();
            String nextId = lexer.getNextId();
            try {
                int delta = Integer.parseInt(nextId);
                expires.setExpires(delta);
                this.lexer.match('\n');
                return expires;
            } catch (NumberFormatException ex) {
                throw createParseException("bad integer format");
            } catch (InvalidArgumentException ex) {
                throw createParseException(ex.getMessage());
            }
        } finally {
            if (log.isDebugEnabled())
                dbg_leave("parse");
        }

    }
}
