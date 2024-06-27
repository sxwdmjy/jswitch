package com.jswitch.sip.header;


import java.text.ParseException;

public class Authorization extends AuthenticationHeader   implements AuthorizationHeaderIms{


    private static final long serialVersionUID = -8897770321892281348L;


    public Authorization() {
        super(AuthorizationHeader.NAME);
    }

    public void setIntegrityProtected(String integrityProtected) throws ParseException {
        if (integrityProtected == null)
            throw new NullPointerException("Exception, AuthenticationHeader, setIntegrityProtected(), The integrity-protected parameter is null");

        setParameter(INTEGRITY_PROTECTED, integrityProtected);
    }
}
