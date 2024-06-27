package com.jswitch.sip.header;

import lombok.Getter;

import java.text.ParseException;

/**
 * Privacy SIP header - RFC 3323.
 *
 */
@Getter
public class Privacy extends SIPHeader implements PrivacyHeader, SIPHeaderNamesIms, ExtensionHeader
{


    private String privacy;



    public Privacy() {
        super(PRIVACY);
    }


    public Privacy(String privacy)
    {
        this();
        this.privacy = privacy;

    }



    public StringBuilder encodeBody(StringBuilder buffer) {
        return buffer.append(this.privacy);
    }


    public void setPrivacy(String privacy) throws ParseException
    {

        if (privacy == null || privacy == "")
            throw new NullPointerException("SIP Exception, Privacy, setPrivacy(), privacy value is null or empty");
        this.privacy = privacy;

    }


    public void setValue(String value) throws ParseException {
        throw new ParseException(value,0);

    }

    public boolean equals(Object other)
    {
        if (other instanceof PrivacyHeader)
        {
            PrivacyHeader o = (PrivacyHeader) other;
            return (this.getPrivacy().equals( o.getPrivacy() ));
        }
        return false;

    }


    public Object clone() {
        Privacy retval = (Privacy) super.clone();
        if (this.privacy != null)
            retval.privacy = this.privacy;
        return retval;
    }



}
