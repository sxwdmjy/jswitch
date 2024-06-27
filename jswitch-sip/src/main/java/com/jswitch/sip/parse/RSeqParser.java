package com.jswitch.sip.parse;

import com.jswitch.common.exception.InvalidArgumentException;
import com.jswitch.sip.SIPHeaderNames;
import com.jswitch.sip.header.RSeq;
import com.jswitch.sip.header.SIPHeader;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;

@Slf4j
public class RSeqParser extends HeaderParser {


    public RSeqParser(String rseq) {
        super(rseq);
    }


    protected RSeqParser(Lexer lexer) {
        super(lexer);
    }


    public SIPHeader parse() throws ParseException {

        if (log.isDebugEnabled())
            dbg_enter("RSeqParser.parse");
        RSeq rseq = new RSeq();
        try {
            headerName(TokenTypes.RSEQ);

            rseq.setHeaderName(SIPHeaderNames.RSEQ);

            String number = this.lexer.number();
            try {
                rseq.setSeqNumber(Long.parseLong(number));
            } catch (InvalidArgumentException ex) {
                throw createParseException(ex.getMessage());
            }
            this.lexer.SPorHT();

            this.lexer.match('\n');

            return rseq;
        } finally {
            if (log.isDebugEnabled())
                dbg_leave("RSeqParser.parse");
        }
    }


}
