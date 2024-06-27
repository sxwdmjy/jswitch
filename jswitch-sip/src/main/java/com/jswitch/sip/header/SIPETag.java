package com.jswitch.sip.header;

import java.text.ParseException;

public class SIPETag extends SIPHeader implements SIPETagHeader, ExtensionHeader {


    private static final long serialVersionUID = 3837543366074322107L;


    protected String entityTag;


    public SIPETag() {
        super(NAME);
    }

    public SIPETag(String tag) throws ParseException {
        this();
        this.setETag(tag);
    }

    public StringBuilder encodeBody(StringBuilder retval) {
        return retval.append(entityTag);
    }


    public String getETag() {
        return entityTag;
    }


    public void setETag(String etag) throws ParseException {
        if (etag == null)
            throw new NullPointerException("SIP Exception, SIP-ETag, setETag(), the etag parameter is null");
        this.entityTag = etag;
    }

    public void setValue(String value) throws ParseException {
        this.setETag(value);

    }
}
