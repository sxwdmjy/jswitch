package com.jswitch.sip.parse;


import com.jswitch.sip.SIPHeaderNames;
import com.jswitch.sip.core.Token;
import com.jswitch.sip.header.Accept;
import com.jswitch.sip.header.AcceptList;
import com.jswitch.sip.header.SIPHeader;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;

@Slf4j
public class AcceptParser extends ParametersParser {


    public AcceptParser(String accept) {
        super(accept);
    }


    protected AcceptParser(Lexer lexer) {
        super(lexer);
    }


    public SIPHeader parse() throws ParseException {

        if (log.isDebugEnabled())
            dbg_enter("AcceptParser.parse");
        AcceptList list = new AcceptList();

        try {
            headerName(TokenTypes.ACCEPT);

            Accept accept = new Accept();
            accept.setHeaderName(SIPHeaderNames.ACCEPT);

            this.lexer.SPorHT();
            if ( lexer.startsId() ) {	// allow can be empty
	            this.lexer.match(TokenTypes.ID);
	            Token token = lexer.getNextToken();
	            accept.setContentType(token.getTokenValue());
	            this.lexer.match('/');
	            this.lexer.match(TokenTypes.ID);
	            token = lexer.getNextToken();
	            accept.setContentSubType(token.getTokenValue());
	            this.lexer.SPorHT();	
	            super.parse(accept);
            }
            list.add(accept);

            while (lexer.lookAhead(0) == ',') {
                this.lexer.match(',');
                this.lexer.SPorHT();

                accept = new Accept();
                if ( lexer.startsId() ) {
	                this.lexer.match(TokenTypes.ID);
	                Token token = lexer.getNextToken();
	                accept.setContentType(token.getTokenValue());
	                this.lexer.match('/');
	                this.lexer.match(TokenTypes.ID);
	                token = lexer.getNextToken();
	                accept.setContentSubType(token.getTokenValue());
	                this.lexer.SPorHT();
	                super.parse(accept);
                }
                list.add(accept);
            }
            return list;
        } finally {
            if (log.isDebugEnabled())
                dbg_leave("AcceptParser.parse");
        }
    }
}
