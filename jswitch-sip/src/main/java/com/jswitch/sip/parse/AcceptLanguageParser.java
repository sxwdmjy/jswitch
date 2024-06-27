package com.jswitch.sip.parse;

import com.jswitch.common.exception.InvalidArgumentException;
import com.jswitch.sip.core.Token;
import com.jswitch.sip.header.AcceptLanguage;
import com.jswitch.sip.header.AcceptLanguageList;
import com.jswitch.sip.header.SIPHeader;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;

@Slf4j
public class AcceptLanguageParser extends HeaderParser {


    public AcceptLanguageParser(String acceptLanguage) {
        super(acceptLanguage);
    }


    protected AcceptLanguageParser(Lexer lexer) {
        super(lexer);
    }


    public SIPHeader parse() throws ParseException {
        AcceptLanguageList acceptLanguageList = new AcceptLanguageList();
        if (log.isDebugEnabled())
            dbg_enter("AcceptLanguageParser.parse");

        try {
            headerName(TokenTypes.ACCEPT_LANGUAGE);
            do {
            	AcceptLanguage acceptLanguage = new AcceptLanguage();
                this.lexer.SPorHT();
                if (lexer.startsId()) {
                    Token value = lexer.match(TokenTypes.ID);	// e.g. "en-gb" or '*'
                   	acceptLanguage.setLanguageRange(value.getTokenValue());
                    this.lexer.SPorHT();
	                while (lexer.lookAhead(0) == ';') {
	                    this.lexer.match(';');
	                    this.lexer.SPorHT();
	                    this.lexer.match('q');
	                    this.lexer.SPorHT();
	                    this.lexer.match('=');
	                    this.lexer.SPorHT();
	                    lexer.match(TokenTypes.ID);
	                    value = lexer.getNextToken();
	                    try {
	                        float fl = Float.parseFloat(value.getTokenValue());
	                        acceptLanguage.setQValue(fl);
	                    } catch (NumberFormatException ex) {
	                        throw createParseException(ex.getMessage());
	                    } catch (InvalidArgumentException ex) {
	                        throw createParseException(ex.getMessage());
	                    }
	                    this.lexer.SPorHT();
	                }
                }
                acceptLanguageList.add(acceptLanguage);
                if ( lexer.lookAhead(0) == ',' ) {
                    this.lexer.match(',');
                    this.lexer.SPorHT();
                } else
                    break;
            } while (true);
        } finally {
            if (log.isDebugEnabled())
                dbg_leave("AcceptLanguageParser.parse");
        }

        return acceptLanguageList;
    }
}
