package com.jswitch.sip.header;


public class PAssertedIdentityList extends SIPHeaderList<PAssertedIdentity> {

    private static final long serialVersionUID = -6465152445570308974L;


    public PAssertedIdentityList()
    {
        super(PAssertedIdentity.class, PAssertedIdentityHeader.NAME);
    }


    public Object clone() {
        PAssertedIdentityList retval = new PAssertedIdentityList();
        return retval.clonehlist(this.hlist);
    }
}
