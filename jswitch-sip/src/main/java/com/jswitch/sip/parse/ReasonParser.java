package com.jswitch.sip.parse;

import com.jswitch.sip.core.Token;
import com.jswitch.sip.header.Reason;
import com.jswitch.sip.header.ReasonList;
import com.jswitch.sip.header.SIPHeader;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;

@Slf4j
public class ReasonParser extends ParametersParser {


    public ReasonParser(String reason) {
        super(reason);
    }


    protected ReasonParser(Lexer lexer) {
        super(lexer);
    }


    public SIPHeader parse() throws ParseException {
        ReasonList reasonList = new ReasonList();
        if (log.isDebugEnabled())
            dbg_enter("ReasonParser.parse");

        try {
            headerName(TokenTypes.REASON);
            this.lexer.SPorHT();
            while (lexer.lookAhead(0) != '\n') {
                Reason reason = new Reason();
                this.lexer.match(TokenTypes.ID);
                Token token = lexer.getNextToken();
                String value = token.getTokenValue();

                reason.setProtocol(value);
                super.parse(reason);
                reasonList.add(reason);
                if (lexer.lookAhead(0) == ',') {
                    this.lexer.match(',');
                    this.lexer.SPorHT();
                } else
                    this.lexer.SPorHT();

            }
        } finally {
            if (log.isDebugEnabled())
                dbg_leave("ReasonParser.parse");
        }

        return reasonList;
    }

}
