package com.jswitch.sip.parse;

import com.jswitch.sip.header.Organization;
import com.jswitch.sip.header.SIPHeader;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;

@Slf4j
public class OrganizationParser extends HeaderParser {


    public OrganizationParser(String organization) {
        super(organization);
    }


    protected OrganizationParser(Lexer lexer) {
        super(lexer);
    }


    public SIPHeader parse() throws ParseException {

        if (log.isDebugEnabled())
            dbg_enter("OrganizationParser.parse");
        Organization organization = new Organization();
        try {
            headerName(TokenTypes.ORGANIZATION);
            String value = this.lexer.getRest();
            organization.setOrganization(value.trim());
            return organization;
        } finally {
            if (log.isDebugEnabled())
                dbg_leave("OrganizationParser.parse");
        }
    }


}
