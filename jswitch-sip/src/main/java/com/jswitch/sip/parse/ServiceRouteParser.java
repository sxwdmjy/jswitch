package com.jswitch.sip.parse;


import com.jswitch.sip.header.SIPHeader;
import com.jswitch.sip.header.ServiceRoute;
import com.jswitch.sip.header.ServiceRouteList;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;

@Slf4j
public class ServiceRouteParser extends AddressParametersParser {


    public ServiceRouteParser(String serviceRoute) {
        super(serviceRoute);

    }

    protected ServiceRouteParser(Lexer lexer) {
        super(lexer);

    }

    public SIPHeader parse() throws ParseException {
        ServiceRouteList serviceRouteList = new ServiceRouteList();

        if (log.isDebugEnabled())
            dbg_enter("ServiceRouteParser.parse");

        try {
            this.lexer.match(TokenTypes.SERVICE_ROUTE);
            this.lexer.SPorHT();
            this.lexer.match(':');
            this.lexer.SPorHT();
            while (true) {
                ServiceRoute serviceRoute = new ServiceRoute();
                super.parse(serviceRoute);
                serviceRouteList.add(serviceRoute);
                this.lexer.SPorHT();
                if (lexer.lookAhead(0) == ',') {
                    this.lexer.match(',');
                    this.lexer.SPorHT();
                } else if (lexer.lookAhead(0) == '\n')
                    break;
                else
                    throw createParseException("unexpected char");
            }
            return serviceRouteList;
        } finally {
            if (log.isDebugEnabled())
                dbg_leave("ServiceRouteParser.parse");
        }

    }

}
