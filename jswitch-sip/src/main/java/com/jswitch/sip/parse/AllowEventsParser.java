
package com.jswitch.sip.parse;

import com.jswitch.sip.core.Token;
import com.jswitch.sip.header.AllowEvents;
import com.jswitch.sip.header.AllowEventsList;
import com.jswitch.sip.header.SIPHeader;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;

@Slf4j
public class AllowEventsParser extends HeaderParser {


    public AllowEventsParser(String allowEvents) {
        super(allowEvents);
    }


    protected AllowEventsParser(Lexer lexer) {
        super(lexer);
    }


    public SIPHeader parse() throws ParseException {

        if (log.isDebugEnabled())
            dbg_enter("AllowEventsParser.parse");
        AllowEventsList list = new AllowEventsList();

        try {
            headerName(TokenTypes.ALLOW_EVENTS);

            AllowEvents allowEvents = new AllowEvents();
            this.lexer.match(TokenTypes.ID);
            Token token = lexer.getNextToken();
            allowEvents.setEventType(token.getTokenValue());

            list.add(allowEvents);
            this.lexer.SPorHT();
            while (lexer.lookAhead(0) == ',') {
                this.lexer.match(',');
                this.lexer.SPorHT();

                allowEvents = new AllowEvents();
                this.lexer.match(TokenTypes.ID);
                token = lexer.getNextToken();
                allowEvents.setEventType(token.getTokenValue());

                list.add(allowEvents);
                this.lexer.SPorHT();
            }
            this.lexer.SPorHT();
            this.lexer.match('\n');

            return list;
        } finally {
            if (log.isDebugEnabled())
                dbg_leave("AllowEventsParser.parse");
        }
    }


}
