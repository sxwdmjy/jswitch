package com.jswitch.sip.header;

public class SecurityVerifyList extends SIPHeaderList<SecurityVerify>
{


    private static final long serialVersionUID = 563201040577795125L;

    public SecurityVerifyList()
    {
        super(SecurityVerify.class, SecurityVerifyHeader.NAME);
    }

    public Object clone() {
        SecurityVerifyList retval = new SecurityVerifyList();
        return retval.clonehlist(this.hlist);
    }


}


