
package com.jswitch.sip.header;


import com.jswitch.common.exception.InvalidArgumentException;

public interface PMediaAuthorizationHeader extends Header
{


    public final static String NAME = "P-Media-Authorization";


    public void setMediaAuthorizationToken(String token) throws InvalidArgumentException;


    public String getToken();


}
