
package com.jswitch.sip.parse;

import com.jswitch.sip.core.Token;
import com.jswitch.sip.header.Event;
import com.jswitch.sip.header.SIPHeader;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;

@Slf4j
public class EventParser extends ParametersParser {

    public EventParser(String event) {
        super(event);
    }


    protected EventParser(Lexer lexer) {
        super(lexer);
    }


    public SIPHeader parse() throws ParseException {

        if (log.isDebugEnabled())
            dbg_enter("EventParser.parse");

        try {
            headerName(TokenTypes.EVENT);
            this.lexer.SPorHT();

            Event event = new Event();
            this.lexer.match(TokenTypes.ID);
            Token token = lexer.getNextToken();
            String value = token.getTokenValue();

            event.setEventType(value);
            super.parse(event);

            this.lexer.SPorHT();
            this.lexer.match('\n');

            return event;

        } catch (ParseException ex) {
            throw createParseException(ex.getMessage());
        } finally {
            if (log.isDebugEnabled())
                dbg_leave("EventParser.parse");
        }
    }


}

