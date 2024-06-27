package com.jswitch.sip.header;


import com.jswitch.common.exception.InvalidArgumentException;
import lombok.Getter;

@Getter
public class Expires extends SIPHeader implements ExpiresHeader {

    private static final long serialVersionUID = 3134344915465784267L;

    protected int expires;


    public Expires() {
        super(NAME);
    }


    public String encodeBody() {
        return encodeBody(new StringBuilder()).toString();
    }

    public StringBuilder encodeBody(StringBuilder buffer) {
        return buffer.append(expires);
    }


    public void setExpires(int expires) throws InvalidArgumentException {
        if (expires < 0)
            throw new InvalidArgumentException("bad argument " + expires);
        this.expires = expires;
    }
}
