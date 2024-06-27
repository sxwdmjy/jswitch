package com.jswitch.sip.parse;


import com.jswitch.common.exception.InvalidArgumentException;
import com.jswitch.sip.core.Token;
import com.jswitch.sip.header.AcceptEncoding;
import com.jswitch.sip.header.AcceptEncodingList;
import com.jswitch.sip.header.SIPHeader;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;


@Slf4j
public class AcceptEncodingParser extends HeaderParser {


    public AcceptEncodingParser(String acceptEncoding) {
        super(acceptEncoding);
    }


    protected AcceptEncodingParser(Lexer lexer) {
        super(lexer);
    }


    public SIPHeader parse() throws ParseException {
        AcceptEncodingList acceptEncodingList = new AcceptEncodingList();
        if (log.isDebugEnabled())
            dbg_enter("AcceptEncodingParser.parse");

        try {
            headerName(TokenTypes.ACCEPT_ENCODING);
            if (!lexer.startsId()) {
                AcceptEncoding acceptEncoding = new AcceptEncoding();
                acceptEncodingList.add(acceptEncoding);
            } else {
                do {
                    AcceptEncoding acceptEncoding = new AcceptEncoding();
                    if (lexer.startsId()) {
                        Token value = lexer.match(TokenTypes.ID);
                        acceptEncoding.setEncoding(value.getTokenValue());

	                    while (lexer.lookAhead(0) == ';') {
	                        this.lexer.match(';');
	                        this.lexer.SPorHT();
	                        Token pname = lexer.match(TokenTypes.ID);
	                        this.lexer.SPorHT();
	                        if ( lexer.lookAhead() == '=' ) {
	                        	this.lexer.match('=');
	                        	this.lexer.SPorHT();
	                        	value = lexer.match(TokenTypes.ID);
	                        	if ( pname.getTokenValue().equalsIgnoreCase("q")) {
			                        try {
			                            float qv = Float.parseFloat(value.getTokenValue());
			                            acceptEncoding.setQValue(qv);
			                        } catch (NumberFormatException ex) {
			                            throw createParseException(ex.getMessage());
			                        } catch (InvalidArgumentException ex) {
			                            throw createParseException(ex.getMessage());
			                        }
	                        	} else {
	                        		acceptEncoding.setParameter( pname.getTokenValue(), value.getTokenValue() );
	                        	}
		                        this.lexer.SPorHT();
	                        } else acceptEncoding.setParameter( pname.getTokenValue(), "" );
	                    }
                    }
                    acceptEncodingList.add(acceptEncoding);
                    if (lexer.lookAhead(0) == ',') {
                        this.lexer.match(',');
                        this.lexer.SPorHT();
                    } else break;
                } while (true);
            }
            return acceptEncodingList;
        } finally {
            if (log.isDebugEnabled())
                dbg_leave("AcceptEncodingParser.parse");
        }
    }
}
