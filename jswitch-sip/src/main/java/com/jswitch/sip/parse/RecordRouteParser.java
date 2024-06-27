package com.jswitch.sip.parse;

import com.jswitch.sip.header.RecordRoute;
import com.jswitch.sip.header.RecordRouteList;
import com.jswitch.sip.header.SIPHeader;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;

@Slf4j
public class RecordRouteParser extends AddressParametersParser {

    public RecordRouteParser(String recordRoute) {
        super(recordRoute);
    }

    protected RecordRouteParser(Lexer lexer) {
        super(lexer);
    }


    public SIPHeader parse() throws ParseException {
        RecordRouteList recordRouteList = new RecordRouteList();

        if (log.isDebugEnabled())
            dbg_enter("RecordRouteParser.parse");

        try {
            this.lexer.match(TokenTypes.RECORD_ROUTE);
            this.lexer.SPorHT();
            this.lexer.match(':');
            this.lexer.SPorHT();
            while (true) {
                RecordRoute recordRoute = new RecordRoute();
                super.parse(recordRoute);
                recordRouteList.add(recordRoute);
                this.lexer.SPorHT();
                char la = lexer.lookAhead(0);
                if (la == ',') {
                    this.lexer.match(',');
                    this.lexer.SPorHT();
                } else if (la == '\n')
                    break;
                else
                    throw createParseException("unexpected char");
            }
            return recordRouteList;
        } finally {
            if (log.isDebugEnabled())
                dbg_leave("RecordRouteParser.parse");
        }

    }


}
