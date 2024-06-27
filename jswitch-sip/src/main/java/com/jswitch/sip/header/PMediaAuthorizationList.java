package com.jswitch.sip.header;

public class PMediaAuthorizationList extends SIPHeaderList<PMediaAuthorization> {
    private static final long serialVersionUID = -8226328073989632317L;


    public PMediaAuthorizationList()
    {
        super(PMediaAuthorization.class, PMediaAuthorizationHeader.NAME);
    }


    public Object clone() {
        PMediaAuthorizationList retval = new PMediaAuthorizationList();
        return retval.clonehlist(this.hlist);
    }

}
