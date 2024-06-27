package com.jswitch.sip.header;


public class SecurityServerList extends SIPHeaderList<SecurityServer>
{


    private static final long serialVersionUID = -1392066520803180238L;

    public SecurityServerList()
    {
        super(SecurityServer.class, SecurityServerHeader.NAME);
    }

    public Object clone() {
        SecurityServerList retval = new SecurityServerList();
        return retval.clonehlist(this.hlist);
    }

}


