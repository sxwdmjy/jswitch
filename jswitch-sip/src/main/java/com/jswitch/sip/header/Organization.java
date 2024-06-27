
package com.jswitch.sip.header;

import lombok.Getter;

import java.text.ParseException;

import static com.jswitch.sip.SIPHeaderNames.ORGANIZATION;

@Getter
public class Organization extends SIPHeader implements OrganizationHeader {


    private static final long serialVersionUID = -2775003113740192712L;

    protected String organization;


    public StringBuilder encodeBody(StringBuilder buffer) {
        return buffer.append(organization);
    }


    public Organization() {
        super(ORGANIZATION);
    }


    public void setOrganization(String o) throws ParseException {
        if (o == null)
            throw new NullPointerException("SIP Exception, Organization, setOrganization(), the organization parameter is null");
        organization = o;
    }
}
