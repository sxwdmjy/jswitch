package com.jswitch.sip.parse;

import com.jswitch.sip.header.From;
import com.jswitch.sip.header.SIPHeader;

import java.text.ParseException;

public class FromParser extends AddressParametersParser {

    public FromParser(String from) {
        super(from);
    }

    protected FromParser(Lexer lexer) {
        super(lexer);
    }

    public SIPHeader parse() throws ParseException {
        From from = new From();
        headerName( FROM );
        super.parse(from);
        this.lexer.match('\n');
        return from;
    }


}
