package com.jswitch.sip.parse;

import com.jswitch.common.exception.InvalidArgumentException;
import com.jswitch.sip.header.MinSE;
import com.jswitch.sip.header.SIPHeader;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;

@Slf4j
public class MinSEParser extends ParametersParser {


    public MinSEParser(String text) {
        super(text);
    }


    protected MinSEParser(Lexer lexer) {
        super(lexer);
    }

    public SIPHeader parse() throws ParseException {
        MinSE minse = new MinSE();
        if (log.isDebugEnabled())
            dbg_enter("parse");
        try {
            headerName(TokenTypes.MINSE_TO);

            String nextId = lexer.getNextId();
            try {
                int delta = Integer.parseInt(nextId);
                minse.setExpires(delta);
            } catch (NumberFormatException ex) {
                throw createParseException("bad integer format");
            } catch (InvalidArgumentException ex) {
                throw createParseException(ex.getMessage());
            }
            this.lexer.SPorHT();
            super.parse(minse);
            return minse;

        } finally {
            if (log.isDebugEnabled())
                dbg_leave("parse");
        }

    }

    public static void main(String args[]) throws ParseException {
        String to[] =
            {   "Min-SE: 30\n",
                "Min-SE: 45;some-param=somevalue\n",
            };

        for (int i = 0; i < to.length; i++) {
            MinSEParser tp = new MinSEParser(to[i]);
            MinSE t = (MinSE) tp.parse();
            System.out.println("encoded = " + t.encode());
            System.out.println("\ntime=" + t.getExpires() );
            if ( t.getParameter("some-param") != null)
                System.out.println("some-param=" + t.getParameter("some-param") );

        }
    }




}
