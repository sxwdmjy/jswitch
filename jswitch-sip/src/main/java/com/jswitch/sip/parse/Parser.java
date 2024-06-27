
package com.jswitch.sip.parse;

import com.jswitch.sip.core.LexerCore;
import com.jswitch.sip.core.ParserCore;
import com.jswitch.sip.core.Token;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;

@Slf4j
public abstract class Parser extends ParserCore implements TokenTypes {

    protected ParseException createParseException(String exceptionString) {
        return new ParseException(
                lexer.getBuffer() + ":" + exceptionString,
                lexer.getPtr());
    }

    protected Lexer getLexer() {
        return (Lexer) this.lexer;
    }

    protected String sipVersion() throws ParseException {
        if (log.isDebugEnabled())
            dbg_enter("sipVersion");
        try {
            Token tok = lexer.match(SIP);
            if (!tok.getTokenValue().equalsIgnoreCase("SIP"))
                createParseException("Expecting SIP");
            lexer.match('/');
            tok = lexer.match(ID);
            if (!tok.getTokenValue().equals("2.0"))
                createParseException("Expecting SIP/2.0");

            return "SIP/2.0";
        } finally {
            if (log.isDebugEnabled())
                dbg_leave("sipVersion");
        }
    }


    protected String method() throws ParseException {
        try {
            if (log.isDebugEnabled())
                dbg_enter("method");
            Token[] tokens = this.lexer.peekNextToken(1);
            Token token = (Token) tokens[0];
            if (token.getTokenType() == INVITE
                    || token.getTokenType() == ACK
                    || token.getTokenType() == OPTIONS
                    || token.getTokenType() == BYE
                    || token.getTokenType() == REGISTER
                    || token.getTokenType() == CANCEL
                    || token.getTokenType() == SUBSCRIBE
                    || token.getTokenType() == NOTIFY
                    || token.getTokenType() == PUBLISH
                    || token.getTokenType() == MESSAGE
                    || token.getTokenType() == ID) {
                lexer.consume();
                return token.getTokenValue();
            } else {
                throw createParseException("Invalid Method");
            }
        } finally {
            if (log.isDebugEnabled())
                dbg_leave("method");
        }
    }


    public static final void checkToken(String token) throws ParseException {

        if (token == null || token.length() == 0) {
            throw new ParseException("null or empty token", -1);
        } else {
            // JvB: check that it is a valid token
            for (int i = 0; i < token.length(); ++i) {
                if (!LexerCore.isTokenChar(token.charAt(i))) {
                    throw new ParseException("Invalid character(s) in string (not allowed in 'token')", i);
                }
            }
        }
    }
}

