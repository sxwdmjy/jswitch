package com.jswitch.sip.header;

public class PVisitedNetworkIDList extends SIPHeaderList<PVisitedNetworkID> {

    private static final long serialVersionUID = -4346667490341752478L;


    public PVisitedNetworkIDList() {
        super(PVisitedNetworkID.class, PVisitedNetworkIDHeader.NAME);
    }

    public Object clone() {
        PVisitedNetworkIDList retval = new PVisitedNetworkIDList();
        return retval.clonehlist(this.hlist);
    }

}
