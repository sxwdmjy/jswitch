
package com.jswitch.sip.parse;


import com.jswitch.sip.core.Token;
import com.jswitch.sip.header.Allow;
import com.jswitch.sip.header.AllowList;
import com.jswitch.sip.header.SIPHeader;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;

@Slf4j
public class AllowParser extends HeaderParser {


    public AllowParser(String allow) {
        super(allow);
    }


    protected AllowParser(Lexer lexer) {
        super(lexer);
    }

    public SIPHeader parse() throws ParseException {

        if (log.isDebugEnabled())
            dbg_enter("AllowParser.parse");
        AllowList list = new AllowList();

        try {
            headerName(TokenTypes.ALLOW);

            Allow allow = new Allow();
            list.add(allow);
            if ( lexer.startsId() ) {
	            Token token = this.lexer.match(TokenTypes.ID);
	            allow.setMethod(token.getTokenValue());
	            this.lexer.SPorHT();
	            while (lexer.lookAhead(0) == ',') {
	                this.lexer.match(',');
	                this.lexer.SPorHT();
	
	                allow = new Allow();
	                token = this.lexer.match(TokenTypes.ID);
	                allow.setMethod(token.getTokenValue());	
	                list.add(allow);
	                this.lexer.SPorHT();
	            }
            }
            this.lexer.match('\n');
            return list;
        } finally {
            if (log.isDebugEnabled())
                dbg_leave("AllowParser.parse");
        }
    }


}
