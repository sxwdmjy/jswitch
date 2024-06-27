package com.jswitch.sip.header;

public class SecurityClientList extends SIPHeaderList<SecurityClient>
{


    private static final long serialVersionUID = 3094231003329176217L;


    public SecurityClientList()
    {
        super(SecurityClient.class, SecurityClientHeader.NAME);
    }


    public Object clone() {
        SecurityClientList retval = new SecurityClientList();
        return retval.clonehlist(this.hlist);
    }

}


