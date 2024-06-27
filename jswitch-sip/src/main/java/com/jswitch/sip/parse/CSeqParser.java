package com.jswitch.sip.parse;

import com.jswitch.common.exception.InvalidArgumentException;
import com.jswitch.sip.header.CSeq;
import com.jswitch.sip.SipRequest;
import com.jswitch.sip.header.SIPHeader;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;

@Slf4j
public class CSeqParser extends HeaderParser {

    public CSeqParser(String cseq) {
        super(cseq);
    }

    protected CSeqParser(Lexer lexer) {
        super(lexer);
    }

    public SIPHeader parse() throws ParseException {
        try {
            CSeq c = new CSeq();
            headerName(CSEQ);
            String number = this.lexer.number();
            c.setSeqNumber(Long.parseLong(number));
            this.lexer.SPorHT();
            String m = SipRequest.getCannonicalName(method()).intern();
            c.setMethod(m);
            this.lexer.SPorHT();
            this.lexer.match('\n');
            return c;
        } catch (NumberFormatException ex) {
            log.error("Number format exception {}", ex.getMessage(), ex);
            throw createParseException("Number format exception");
        } catch (InvalidArgumentException ex) {
            log.error("Invalid argument exception {}", ex.getMessage(), ex);
            throw createParseException(ex.getMessage());
        }
    }
}
