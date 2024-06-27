package com.jswitch.sip.parse;


import com.jswitch.common.exception.InvalidArgumentException;
import com.jswitch.sip.header.SIPHeader;
import com.jswitch.sip.header.SessionExpires;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;

@Slf4j
public class SessionExpiresParser extends ParametersParser {


    public SessionExpiresParser(String text) {
        super(text);
    }


    protected SessionExpiresParser(Lexer lexer) {
        super(lexer);
    }


    public SIPHeader parse() throws ParseException {
        SessionExpires se = new SessionExpires();
        if (log.isDebugEnabled())
            dbg_enter("parse");
        try {
            headerName(TokenTypes.SESSIONEXPIRES_TO);

            String nextId = lexer.getNextId();

            try {
                int delta = Integer.parseInt(nextId);
                se.setExpires(delta);
            } catch (NumberFormatException ex) {
                throw createParseException("bad integer format");
            } catch (InvalidArgumentException ex) {
                throw createParseException(ex.getMessage());
            }
            // May have parameters...
            this.lexer.SPorHT();
            super.parse(se);
            return se;

        } finally {
            if (log.isDebugEnabled())
                dbg_leave("parse");
        }

    }

    public static void main(String args[]) throws ParseException {
        String to[] =
            {   "Session-Expires: 30\n",
                "Session-Expires: 45;refresher=uac\n",
            };

        for (int i = 0; i < to.length; i++) {
            SessionExpiresParser tp = new SessionExpiresParser(to[i]);
            SessionExpires t = (SessionExpires) tp.parse();
            System.out.println("encoded = " + t.encode());
            System.out.println("\ntime=" + t.getExpires() );
            if ( t.getParameter("refresher") != null)
                System.out.println("refresher=" + t.getParameter("refresher") );

        }
    }


}

