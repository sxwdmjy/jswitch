package com.jswitch.sip.parse;

import com.jswitch.common.exception.InvalidArgumentException;
import com.jswitch.sip.SIPHeaderNames;
import com.jswitch.sip.core.Token;
import com.jswitch.sip.header.RAck;
import com.jswitch.sip.header.SIPHeader;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;

@Slf4j
public class RAckParser extends HeaderParser {


    public RAckParser(String rack) {
        super(rack);
    }


    protected RAckParser(Lexer lexer) {
        super(lexer);
    }


    public SIPHeader parse() throws ParseException {

        if (log.isDebugEnabled())
            dbg_enter("RAckParser.parse");
        RAck rack = new RAck();
        try {
            headerName(TokenTypes.RACK);

            rack.setHeaderName(SIPHeaderNames.RACK);

            try {
                String number = this.lexer.number();
                rack.setRSequenceNumber(Long.parseLong(number));
                this.lexer.SPorHT();
                number = this.lexer.number();
                rack.setCSequenceNumber(Long.parseLong(number));
                this.lexer.SPorHT();
                this.lexer.match(TokenTypes.ID);
                Token token = lexer.getNextToken();
                rack.setMethod(token.getTokenValue());

            } catch (InvalidArgumentException ex) {
                throw createParseException(ex.getMessage());
            }
            this.lexer.SPorHT();
            this.lexer.match('\n');

            return rack;
        } finally {
            if (log.isDebugEnabled())
                dbg_leave("RAckParser.parse");
        }
    }

}
