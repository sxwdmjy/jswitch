package com.jswitch.sip.parse;

import com.jswitch.sip.header.SIPHeader;
import com.jswitch.sip.header.WWWAuthenticate;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;

@Slf4j
public class WWWAuthenticateParser extends ChallengeParser {


    public WWWAuthenticateParser(String wwwAuthenticate) {
        super(wwwAuthenticate);
    }


    protected WWWAuthenticateParser(Lexer lexer) {
        super(lexer);
    }


    public SIPHeader parse() throws ParseException {
        if (log.isDebugEnabled())
            dbg_enter("parse");
        try {
            headerName(TokenTypes.WWW_AUTHENTICATE);
            WWWAuthenticate wwwAuthenticate = new WWWAuthenticate();
            super.parse(wwwAuthenticate);
            return wwwAuthenticate;
        } finally {
            if (log.isDebugEnabled())
                dbg_leave("parse");
        }
    }
}

