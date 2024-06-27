
package com.jswitch.sip.parse;

import com.jswitch.sip.header.Contact;
import com.jswitch.sip.header.ContactList;
import com.jswitch.sip.header.SIPHeader;

import java.text.ParseException;

public class ContactParser extends AddressParametersParser {

    public ContactParser(String contact) {
        super(contact);
    }

    protected ContactParser(Lexer lexer) {
        super(lexer);
        this.lexer = lexer;
    }

    public SIPHeader parse() throws ParseException {
        headerName(TokenTypes.CONTACT);
        ContactList retval = new ContactList();
        while (true) {
            Contact contact = new Contact();
            if (lexer.lookAhead(0) == '*') {
                final char next = lexer.lookAhead(1);
                if (next == ' ' || next == '\t' || next == '\r' || next == '\n') {
                    this.lexer.match('*');
                    contact.setWildCardFlag(true);
                } else {
                    super.parse(contact);
                }
            } else {
                super.parse(contact);
            }
            retval.add(contact);
            this.lexer.SPorHT();
            char la = lexer.lookAhead(0);
            if (la == ',') {
                this.lexer.match(',');
                this.lexer.SPorHT();
            } else if (la == '\n' || la == '\0')
                break;
            else
                throw createParseException("unexpected char");
        }
        return retval;
    }

}
