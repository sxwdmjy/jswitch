package com.jswitch.sip.parse;

import com.jswitch.sip.SIPHeaderNames;
import com.jswitch.sip.header.Priority;
import com.jswitch.sip.header.SIPHeader;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;

@Slf4j
public class PriorityParser extends HeaderParser {


    public PriorityParser(String priority) {
        super(priority);
    }

    protected PriorityParser(Lexer lexer) {
        super(lexer);
    }


    public SIPHeader parse() throws ParseException {
        if (log.isDebugEnabled())
            dbg_enter("PriorityParser.parse");
        Priority priority = new Priority();
        try {
            headerName(TokenTypes.PRIORITY);

            priority.setHeaderName(SIPHeaderNames.PRIORITY);

            this.lexer.SPorHT();
            priority.setPriority(this.lexer.ttokenSafe());

            this.lexer.SPorHT();
            this.lexer.match('\n');

            return priority;
        } finally {
            if (log.isDebugEnabled())
                dbg_leave("PriorityParser.parse");
        }
    }

}

