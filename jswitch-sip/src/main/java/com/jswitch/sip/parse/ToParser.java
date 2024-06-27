package com.jswitch.sip.parse;

import com.jswitch.sip.header.To;
import com.jswitch.sip.header.SIPHeader;

import java.text.ParseException;

public class ToParser extends AddressParametersParser {

    public ToParser(String to) {
        super(to);
    }

    protected ToParser(Lexer lexer) {
        super(lexer);
    }

    public SIPHeader parse() throws ParseException {

        headerName(TokenTypes.TO);
        To to = new To();
        super.parse(to);
        this.lexer.match('\n');        
        return to;
    }

}

