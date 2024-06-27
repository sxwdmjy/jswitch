package com.jswitch.sip.parse;


import com.jswitch.sip.NameValue;
import com.jswitch.sip.core.Token;
import com.jswitch.sip.header.AuthenticationHeader;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;

@Slf4j
public abstract class ChallengeParser extends HeaderParser {


    protected ChallengeParser(String challenge) {
        super(challenge);
    }


    protected ChallengeParser(Lexer lexer) {
        super(lexer);
    }

    protected void parseParameter(AuthenticationHeader header)
        throws ParseException {

        if (log.isDebugEnabled())
            dbg_enter("parseParameter");
        try {
            NameValue nv = this.nameValue('=');
            header.setParameter(nv);
        } finally {
            if (log.isDebugEnabled())
                dbg_leave("parseParameter");
        }

    }


    public void parse(AuthenticationHeader header) throws ParseException {

        this.lexer.SPorHT();
        lexer.match(TokenTypes.ID);
        Token type = lexer.getNextToken();
        this.lexer.SPorHT();
        header.setScheme(type.getTokenValue());

        // The parameters:
        try {
            while (lexer.lookAhead(0) != '\n') {
                this.parseParameter(header);
                this.lexer.SPorHT();
                char la = lexer.lookAhead(0);
                if (la == '\n' || la == '\0')
                    break;
                this.lexer.match(',');
                this.lexer.SPorHT();
            }
        } catch (ParseException ex) {
            throw ex;
        }
    }
}
