package com.jswitch.sip.parse;

import com.jswitch.sip.header.CallID;
import com.jswitch.sip.header.SIPHeader;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;

@Slf4j
public class CallIDParser extends HeaderParser {


    public CallIDParser(String callID) {
        super(callID);
    }


    protected CallIDParser(Lexer lexer) {
        super(lexer);
    }

    public SIPHeader parse() throws ParseException {
        if (log.isDebugEnabled())
            dbg_enter("parse");
        try {
            this.lexer.match(TokenTypes.CALL_ID);
            this.lexer.SPorHT();
            this.lexer.match(':');
            this.lexer.SPorHT();

            CallID callID = new CallID();

            this.lexer.SPorHT();
            String rest = lexer.getRest();
            callID.setCallId(rest.trim());
            return callID;
        } finally {
            if (log.isDebugEnabled())
                dbg_leave("parse");
        }
    }

}
