
package com.jswitch.sip.parse;


import com.jswitch.sip.adress.AddressImpl;
import com.jswitch.sip.GenericURI;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;

@Slf4j
public class AddressParser extends Parser {

    public AddressParser(Lexer lexer) {
        this.lexer = lexer;
        this.lexer.selectLexer("charLexer");
    }

    public AddressParser(String address) {
        this.lexer = new Lexer("charLexer", address);
    }

    protected AddressImpl nameAddr() throws ParseException {
        if (log.isDebugEnabled())
            dbg_enter("nameAddr");
        try {
            if (this.lexer.lookAhead(0) == '<') {
                this.lexer.consume(1);
                this.lexer.selectLexer("sip_urlLexer");
                this.lexer.SPorHT();
                URLParser uriParser = new URLParser((Lexer) lexer);
                GenericURI uri = uriParser.uriReference( true );
                AddressImpl retval = new AddressImpl();
                retval.setAddressType(AddressImpl.NAME_ADDR);
                retval.setURI(uri);
                this.lexer.SPorHT();
                this.lexer.match('>');
                return retval;
            } else {
                AddressImpl addr = new AddressImpl();
                addr.setAddressType(AddressImpl.NAME_ADDR);
                String name = null;
                if (this.lexer.lookAhead(0) == '\"') {
                    name = this.lexer.quotedString();
                    this.lexer.SPorHT();
                } else
                    name = this.lexer.getNextToken('<');
                addr.setDisplayName(name.trim());
                this.lexer.match('<');
                this.lexer.SPorHT();
                URLParser uriParser = new URLParser((Lexer) lexer);
                GenericURI uri = uriParser.uriReference( true );
                AddressImpl retval = new AddressImpl();
                addr.setAddressType(AddressImpl.NAME_ADDR);
                addr.setURI(uri);
                this.lexer.SPorHT();
                this.lexer.match('>');
                return addr;
            }
        } finally {
            if (log.isDebugEnabled())
                dbg_leave("nameAddr");
        }
    }

    public AddressImpl address( boolean inclParams ) throws ParseException {
        if (log.isDebugEnabled())
            dbg_enter("address");
        AddressImpl retval = null;
        try {
            int k = 0;
            while (lexer.hasMoreChars()) {
                char la = lexer.lookAhead(k);
                if (la == '<'
                    || la == '\"'
                    || la == ':'
                    || la == '/')
                    break;
                else if (la == '\0')
                    throw createParseException("unexpected EOL");
                else
                    k++;
            }
            char la = lexer.lookAhead(k);
            if (la == '<' || la == '\"') {
                retval = nameAddr();
            } else if (la == ':' || la == '/') {
                retval = new AddressImpl();
                URLParser uriParser = new URLParser((Lexer) lexer);
                GenericURI uri = uriParser.uriReference( inclParams );
                retval.setAddressType(AddressImpl.ADDRESS_SPEC);
                retval.setURI(uri);
            } else {
                throw createParseException("Bad address spec");
            }
            return retval;
        } finally {
            if (log.isDebugEnabled())
                dbg_leave("address");
        }

    }


}

