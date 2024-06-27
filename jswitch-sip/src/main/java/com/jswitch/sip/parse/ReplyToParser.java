
package com.jswitch.sip.parse;


import com.jswitch.sip.SIPHeaderNames;
import com.jswitch.sip.header.ReplyTo;
import com.jswitch.sip.header.SIPHeader;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;

@Slf4j
public class ReplyToParser extends AddressParametersParser {


    public ReplyToParser(String replyTo) {
        super(replyTo);
    }


    protected ReplyToParser(Lexer lexer) {
        super(lexer);
    }


    public SIPHeader parse() throws ParseException {
        ReplyTo replyTo = new ReplyTo();
        if (log.isDebugEnabled())
            dbg_enter("ReplyTo.parse");

        try {
            headerName(TokenTypes.REPLY_TO);

            replyTo.setHeaderName(SIPHeaderNames.REPLY_TO);

            super.parse(replyTo);

            return replyTo;
        } finally {
            if (log.isDebugEnabled())
                dbg_leave("ReplyTo.parse");
        }

    }


}

