package com.jswitch.sip.parse;

import com.jswitch.sip.header.PCalledPartyID;
import com.jswitch.sip.header.SIPHeader;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;

@Slf4j
public class PCalledPartyIDParser extends AddressParametersParser
{


    public PCalledPartyIDParser(String calledPartyID)
    {
        super(calledPartyID);
    }

    protected PCalledPartyIDParser(Lexer lexer)
    {
        super(lexer);
    }


    public SIPHeader parse() throws ParseException
    {

        if (log.isDebugEnabled())
            dbg_enter("PCalledPartyIDParser.parse");

        try {
            this.lexer.match(TokenTypes.P_CALLED_PARTY_ID);
            this.lexer.SPorHT();
            this.lexer.match(':');
            this.lexer.SPorHT();

            PCalledPartyID calledPartyID = new PCalledPartyID();
            super.parse(calledPartyID);

            return calledPartyID;

        } finally {
            if (log.isDebugEnabled())
                dbg_leave("PCalledPartyIDParser.parse");
        }

    }

}
