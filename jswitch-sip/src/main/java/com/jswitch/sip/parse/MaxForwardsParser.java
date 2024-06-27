package com.jswitch.sip.parse;

import com.jswitch.common.exception.InvalidArgumentException;
import com.jswitch.sip.MaxForwards;
import com.jswitch.sip.header.SIPHeader;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;

@Slf4j
public class MaxForwardsParser extends HeaderParser {

    public MaxForwardsParser(String contentLength) {
        super(contentLength);
    }

    protected MaxForwardsParser(Lexer lexer) {
        super(lexer);
    }

    public SIPHeader parse() throws ParseException {
        if (log.isDebugEnabled())
            dbg_enter("MaxForwardsParser.enter");
        try {
            MaxForwards contentLength = new MaxForwards();
            headerName(TokenTypes.MAX_FORWARDS);
            String number = this.lexer.number();
            contentLength.setMaxForwards(Integer.parseInt(number));
            this.lexer.SPorHT();
            this.lexer.match('\n');
            return contentLength;
        } catch (InvalidArgumentException | NumberFormatException ex) {
            throw createParseException(ex.getMessage());
        } finally {
            if (log.isDebugEnabled())
                dbg_leave("MaxForwardsParser.leave");
        }
    }


}
