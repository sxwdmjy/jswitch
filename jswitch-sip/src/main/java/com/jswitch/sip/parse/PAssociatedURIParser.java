package com.jswitch.sip.parse;

import com.jswitch.sip.header.PAssociatedURI;
import com.jswitch.sip.header.PAssociatedURIList;
import com.jswitch.sip.header.SIPHeader;
import com.jswitch.sip.header.SIPHeaderNamesIms;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;

@Slf4j
public class PAssociatedURIParser extends AddressParametersParser
{

    public PAssociatedURIParser(String associatedURI)
    {
        super(associatedURI);
    }

    protected PAssociatedURIParser(Lexer lexer)
    {
        super(lexer);
    }


    public SIPHeader parse() throws ParseException
    {
        if (log.isDebugEnabled())
            dbg_enter("PAssociatedURIParser.parse");

        PAssociatedURIList associatedURIList = new PAssociatedURIList();

        try {

            headerName(TokenTypes.P_ASSOCIATED_URI);

            PAssociatedURI associatedURI = new PAssociatedURI();
            associatedURI.setHeaderName(SIPHeaderNamesIms.P_ASSOCIATED_URI);

            super.parse(associatedURI);
            associatedURIList.add(associatedURI);

            this.lexer.SPorHT();
            while (lexer.lookAhead(0) == ',')
            {
                this.lexer.match(',');
                this.lexer.SPorHT();

                associatedURI = new PAssociatedURI();
                super.parse(associatedURI);
                associatedURIList.add(associatedURI);

                this.lexer.SPorHT();
            }
            this.lexer.SPorHT();
            this.lexer.match('\n');

            return associatedURIList;

        } finally {
            if (log.isDebugEnabled())
                dbg_leave("PAssociatedURIParser.parse");
        }

    }

}
