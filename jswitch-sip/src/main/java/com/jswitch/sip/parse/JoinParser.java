package com.jswitch.sip.parse;

import com.jswitch.sip.header.Join;
import com.jswitch.sip.header.SIPHeader;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;

@Slf4j
public class JoinParser extends ParametersParser {


    public JoinParser(String callID) {
        super(callID);
    }


    protected JoinParser(Lexer lexer) {
        super(lexer);
    }


    public SIPHeader parse() throws ParseException {
        if (log.isDebugEnabled())
            dbg_enter("parse");
        try {
            headerName(TokenTypes.JOIN_TO);

            Join join = new Join();
            this.lexer.SPorHT();
            String callId = lexer.byteStringNoSemicolon();
            this.lexer.SPorHT();
            super.parse(join);
            join.setCallId(callId);
            return join;
        } finally {
            if (log.isDebugEnabled())
                dbg_leave("parse");
        }
    }

    public static void main(String args[]) throws ParseException {
        String to[] =
            {   "Join: 12345th5z8z\n",
                "Join: 12345th5z8z;to-tag=tozght6-45;from-tag=fromzght789-337-2\n",
            };

        for (int i = 0; i < to.length; i++) {
            JoinParser tp = new JoinParser(to[i]);
            Join t = (Join) tp.parse();
            System.out.println("Parsing => " + to[i]);
            System.out.print("encoded = " + t.encode() + "==> ");
            System.out.println("callId " + t.getCallId() + " from-tag=" + t.getFromTag()
                    + " to-tag=" + t.getToTag()) ;

        }
    }

}
