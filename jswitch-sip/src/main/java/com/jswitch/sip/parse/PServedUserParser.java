package com.jswitch.sip.parse;


import com.jswitch.sip.header.PServedUser;
import com.jswitch.sip.header.SIPHeader;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;

@Slf4j
public class PServedUserParser extends AddressParametersParser {

    protected PServedUserParser(Lexer lexer) {
        super(lexer);
    }

    public PServedUserParser(String servedUser){
        super(servedUser);
    }

    public SIPHeader parse() throws ParseException {

        if (log.isDebugEnabled())
            dbg_enter("PServedUser.parse");

        try {
            PServedUser servedUser = new PServedUser();
            headerName(P_SERVED_USER);
            super.parse(servedUser);
            this.lexer.match('\n');
            return servedUser;
        }
        finally {
            if (log.isDebugEnabled())
                dbg_leave("PServedUser.parse");
        }
    }
}
