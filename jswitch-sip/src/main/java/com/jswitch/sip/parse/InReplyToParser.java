package com.jswitch.sip.parse;


import com.jswitch.sip.SIPHeaderNames;
import com.jswitch.sip.core.Token;
import com.jswitch.sip.header.InReplyTo;
import com.jswitch.sip.header.InReplyToList;
import com.jswitch.sip.header.SIPHeader;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;

@Slf4j
public class InReplyToParser extends HeaderParser {


    public InReplyToParser(String inReplyTo) {
        super(inReplyTo);
    }


    protected InReplyToParser(Lexer lexer) {
        super(lexer);
    }


    public SIPHeader parse() throws ParseException {

        if (log.isDebugEnabled())
            dbg_enter("InReplyToParser.parse");
        InReplyToList list = new InReplyToList();

        try {
            headerName(TokenTypes.IN_REPLY_TO);

            while (lexer.lookAhead(0) != '\n') {
                InReplyTo inReplyTo = new InReplyTo();
                inReplyTo.setHeaderName(SIPHeaderNames.IN_REPLY_TO);

                this.lexer.match(TokenTypes.ID);
                Token token = lexer.getNextToken();
                if (lexer.lookAhead(0) == '@') {
                    this.lexer.match('@');
                    this.lexer.match(TokenTypes.SAFE);
                    Token secToken = lexer.getNextToken();
                    inReplyTo.setCallId(
                            token.getTokenValue() + "@" + secToken.getTokenValue());
                } else {
                    inReplyTo.setCallId(token.getTokenValue());
                }

                this.lexer.SPorHT();

                list.add(inReplyTo);

                while (lexer.lookAhead(0) == ',') {
                    this.lexer.match(',');
                    this.lexer.SPorHT();

                    inReplyTo = new InReplyTo();

                    this.lexer.match(TokenTypes.ID);
                    token = lexer.getNextToken();
                    if (lexer.lookAhead(0) == '@') {
                        this.lexer.match('@');
                        this.lexer.match(TokenTypes.SAFE);
                        Token secToken = lexer.getNextToken();
                        inReplyTo.setCallId(
                                token.getTokenValue()
                                        + "@"
                                        + secToken.getTokenValue());
                    } else {
                        inReplyTo.setCallId(token.getTokenValue());
                    }

                    list.add(inReplyTo);
                }
            }

            return list;
        } finally {
            if (log.isDebugEnabled())
                dbg_leave("InReplyToParser.parse");
        }
    }

}

