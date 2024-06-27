
package com.jswitch.sip.parse;

import com.jswitch.sip.header.Route;
import com.jswitch.sip.header.RouteList;
import com.jswitch.sip.header.SIPHeader;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;

@Slf4j
public class RouteParser extends AddressParametersParser {


    public RouteParser(String route) {
        super(route);
    }

    protected RouteParser(Lexer lexer) {
        super(lexer);
    }


    public SIPHeader parse() throws ParseException {
        RouteList routeList = new RouteList();
        if (log.isDebugEnabled())
            dbg_enter("parse");

        try {
            this.lexer.match(TokenTypes.ROUTE);
            this.lexer.SPorHT();
            this.lexer.match(':');
            this.lexer.SPorHT();
            while (true) {
                Route route = new Route();
                super.parse(route);
                routeList.add(route);
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
            return routeList;
        } finally {
            if (log.isDebugEnabled())
                dbg_leave("parse");
        }

    }

}
