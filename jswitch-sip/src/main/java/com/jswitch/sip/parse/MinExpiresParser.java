package com.jswitch.sip.parse;

import com.jswitch.common.exception.InvalidArgumentException;
import com.jswitch.sip.SIPHeaderNames;
import com.jswitch.sip.header.MinExpires;
import com.jswitch.sip.header.SIPHeader;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;

@Slf4j
public class MinExpiresParser extends HeaderParser {

    public MinExpiresParser(String minExpires) {
        super(minExpires);
    }


    protected MinExpiresParser(Lexer lexer) {
        super(lexer);
    }


    public SIPHeader parse() throws ParseException {
        if (log.isDebugEnabled())
            dbg_enter("MinExpiresParser.parse");
        MinExpires minExpires = new MinExpires();
        try {
            headerName(TokenTypes.MIN_EXPIRES);

            minExpires.setHeaderName(SIPHeaderNames.MIN_EXPIRES);

            String number = this.lexer.number();
            try {
                minExpires.setExpires(Integer.parseInt(number));
            } catch (InvalidArgumentException ex) {
                throw createParseException(ex.getMessage());
            }
            this.lexer.SPorHT();

            this.lexer.match('\n');

            return minExpires;
        } finally {
            if (log.isDebugEnabled())
                dbg_leave("MinExpiresParser.parse");
        }
    }
}

