package com.jswitch.sip.header;

public final class ReasonList extends SIPHeaderList<Reason> {

    private static final long serialVersionUID = 7459989997463160670L;

    public Object clone() {
        ReasonList retval = new ReasonList();
        retval.clonehlist(this.hlist);
        return retval;
    }

    /** Default constructor
     */
    public ReasonList() {
        super(Reason.class, ReasonHeader.NAME);
    }


}
