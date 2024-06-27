package com.jswitch.sip.header;


import java.text.ParseException;

public class SIPIfMatch extends SIPHeader implements SIPIfMatchHeader,ExtensionHeader {


    private static final long serialVersionUID = 3833745477828359730L;


    protected String entityTag;


    public SIPIfMatch() {
        super(NAME);
    }

    public SIPIfMatch(String etag) throws ParseException {
        this();
        this.setETag( etag );
    }


    public StringBuilder encodeBody(StringBuilder retval) {
        return retval.append(entityTag);
    }

    public String getETag() {
        return entityTag;
    }


    public void setETag(String etag) throws ParseException {
        if (etag == null)
            throw new NullPointerException("SIP Exception, SIP-If-Match, setETag(), the etag parameter is null");
        this.entityTag = etag;
    }

    public void setValue(String value) throws ParseException {
        this.setETag(value);


    }
}
