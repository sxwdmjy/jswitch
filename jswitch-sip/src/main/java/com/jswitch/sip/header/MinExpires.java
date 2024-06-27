package com.jswitch.sip.header;

import com.jswitch.common.exception.InvalidArgumentException;

public class MinExpires extends SIPHeader implements MinExpiresHeader {


    private static final long serialVersionUID = 7001828209606095801L;

    protected int expires;


    public MinExpires() {
        super(NAME);
    }


    public StringBuilder encodeBody(StringBuilder retval) {
        return retval.append(Integer.toString(expires));
    }


    public int getExpires() {
        return expires;
    }


    public void setExpires(int expires) throws InvalidArgumentException {
        if (expires < 0)
            throw new InvalidArgumentException("bad argument " + expires);
        this.expires = expires;
    }

}

