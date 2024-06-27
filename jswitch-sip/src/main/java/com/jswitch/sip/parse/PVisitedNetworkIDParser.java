package com.jswitch.sip.parse;


import com.jswitch.sip.core.Token;
import com.jswitch.sip.header.PVisitedNetworkID;
import com.jswitch.sip.header.PVisitedNetworkIDList;
import com.jswitch.sip.header.SIPHeader;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;

@Slf4j
public class PVisitedNetworkIDParser extends ParametersParser implements TokenTypes {

    /**
     * Constructor
     */
    public PVisitedNetworkIDParser(String networkID) {
        super(networkID);

    }

    protected PVisitedNetworkIDParser(Lexer lexer) {
        super(lexer);

    }

    public SIPHeader parse() throws ParseException {

        PVisitedNetworkIDList visitedNetworkIDList = new PVisitedNetworkIDList();

        if (log.isDebugEnabled())
            dbg_enter("VisitedNetworkIDParser.parse");

        try {
            this.lexer.match(TokenTypes.P_VISITED_NETWORK_ID);
            this.lexer.SPorHT();
            this.lexer.match(':');
            this.lexer.SPorHT();

            while (true) {

                PVisitedNetworkID visitedNetworkID = new PVisitedNetworkID();

                if (this.lexer.lookAhead(0) == '\"')
                    parseQuotedString(visitedNetworkID);
                else
                    parseToken(visitedNetworkID);

                visitedNetworkIDList.add(visitedNetworkID);

                this.lexer.SPorHT();
                char la = lexer.lookAhead(0);
                if (la == ',') {
                    this.lexer.match(',');
                    this.lexer.SPorHT();
                } else if (la == '\n')
                    break;
                else
                    throw createParseException("unexpected char = " + la);
            }
            return visitedNetworkIDList;
        } finally {
            if (log.isDebugEnabled())
                dbg_leave("VisitedNetworkIDParser.parse");
        }

    }

    protected void parseQuotedString(PVisitedNetworkID visitedNetworkID) throws ParseException {

        if (log.isDebugEnabled())
            dbg_enter("parseQuotedString");

        try {

            StringBuilder retval = new StringBuilder();

            if (this.lexer.lookAhead(0) != '\"')
                throw createParseException("unexpected char");
            this.lexer.consume(1);

            while (true) {
                char next = this.lexer.getNextChar();
                if (next == '\"') {
                    // Got to the terminating quote.
                    break;
                } else if (next == '\0') {
                    throw new ParseException("unexpected EOL", 1);
                } else if (next == '\\') {
                    retval.append(next);
                    next = this.lexer.getNextChar();
                    retval.append(next);
                } else {
                    retval.append(next);
                }
            }

            visitedNetworkID.setVisitedNetworkID(retval.toString());
            super.parse(visitedNetworkID);



        }finally {
            if (log.isDebugEnabled())
                dbg_leave("parseQuotedString.parse");
        }

    }

    protected void parseToken(PVisitedNetworkID visitedNetworkID) throws ParseException
    {
        // issued by Miguel Freitas

        lexer.match(TokenTypes.ID);
        Token token = lexer.getNextToken();
        //String value = token.getTokenValue();
        visitedNetworkID.setVisitedNetworkID(token);
        super.parse(visitedNetworkID);

    }


}
