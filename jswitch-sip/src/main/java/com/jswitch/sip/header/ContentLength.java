package com.jswitch.sip.header;

import com.jswitch.common.exception.InvalidArgumentException;

/**
 * @author danmo
 * @date 2024-06-13 18:58
 **/
public class ContentLength extends SIPHeader implements ContentLengthHeader {

    public final static String NAME_LOWER = NAME.toLowerCase();

    protected int contentLength = -1;

    public ContentLength() {
        super(NAME);
    }

    public ContentLength(int length) {
        super(NAME);
        this.contentLength = length;
    }

    @Override
    public void setContentLength(int contentLength) {
        if (contentLength < 0)
            throw new InvalidArgumentException("SIP Exception, ContentLength, setContentLength(), the contentLength parameter is <0");
        this.contentLength = contentLength;
    }

    @Override
    public int getContentLength() {
        return contentLength;
    }


    @Override
    public StringBuilder encodeBody(StringBuilder buffer) {
        if (contentLength < 0)
            buffer.append("0");
        else
            buffer.append(contentLength);
        return buffer;
    }

    public boolean match(Object other) {
        if (other instanceof ContentLength)
            return true;
        else
            return false;
    }

    public boolean equals(Object other) {
        if (other instanceof ContentLengthHeader o) {
            return this.getContentLength() == o.getContentLength();
        }
        return false;
    }

}
