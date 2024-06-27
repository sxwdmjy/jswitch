package com.jswitch.sip.header;


import com.jswitch.common.exception.InvalidArgumentException;
import lombok.Getter;

import java.text.ParseException;

@Getter
public class PMediaAuthorization extends SIPHeader implements PMediaAuthorizationHeader, SIPHeaderNamesIms, ExtensionHeader
{


    private static final long serialVersionUID = -6463630258703731133L;



    private String token;



    public PMediaAuthorization()
    {
        super(P_MEDIA_AUTHORIZATION);
    }


    public void setMediaAuthorizationToken(String token) throws InvalidArgumentException
    {
        if (token == null || token.isEmpty())
            throw new InvalidArgumentException(" the Media-Authorization-Token parameter is null or empty");

        this.token = token;
    }

    public StringBuilder encodeBody(StringBuilder encoding) {
        return encoding.append(token);
    }


    public void setValue(String value) throws ParseException {
        throw new ParseException(value,0);

    }


    public boolean equals(Object other)
    {
        if (other instanceof PMediaAuthorizationHeader)
        {
            final PMediaAuthorizationHeader o = (PMediaAuthorizationHeader) other;
            return this.getToken().equals(o.getToken());
        }
        return false;

    }


    public Object clone() {
        PMediaAuthorization retval = (PMediaAuthorization) super.clone();
        if (this.token != null)
            retval.token = this.token;
        return retval;
    }

}
