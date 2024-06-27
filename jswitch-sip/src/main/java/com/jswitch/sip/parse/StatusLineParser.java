package com.jswitch.sip.parse;


import com.jswitch.sip.header.StatusLine;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;

@Slf4j
public class StatusLineParser extends Parser {
    public StatusLineParser(String statusLine) {
        this.lexer = new Lexer("status_lineLexer", statusLine);
    }

    public StatusLineParser(Lexer lexer) {
        this.lexer = lexer;
        this.lexer.selectLexer("status_lineLexer");
    }

    protected int statusCode() throws ParseException {
        String scode = this.lexer.number();
        if (log.isDebugEnabled())
            dbg_enter("statusCode");
        try {
            int retval = Integer.parseInt(scode);
            return retval;
        } catch (NumberFormatException ex) {
            throw new ParseException(
                    lexer.getBuffer() + ":" + ex.getMessage(),
                    lexer.getPtr());
        } finally {
            if (log.isDebugEnabled())
                dbg_leave("statusCode");
        }

    }

    protected String reasonPhrase() throws ParseException {
        return this.lexer.getRest().trim();
    }

    public StatusLine parse() throws ParseException {
        try {
            if (log.isDebugEnabled())
                dbg_enter("parse");
            StatusLine retval = new StatusLine();
            String version = this.sipVersion();
            retval.setSipVersion(version);
            lexer.SPorHT();
            int scode = statusCode();
            retval.setStatusCode(scode);
            lexer.SPorHT();
            String rp = reasonPhrase();
            retval.setReasonPhrase(rp);
            lexer.SPorHT();
            return retval;
        } finally {
            if (log.isDebugEnabled())
                dbg_leave("parse");
        }
    }

}

