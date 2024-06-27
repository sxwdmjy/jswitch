package com.jswitch.sip.header;


public class PAccessNetworkInfoList extends SIPHeaderList<PAccessNetworkInfo> {

    private static final long serialVersionUID = 6993762829214108619L;

    public PAccessNetworkInfoList() {
        super(PAccessNetworkInfo.class, PAccessNetworkInfoHeader.NAME);
    }

    @Override
    public Object clone() {
        PAccessNetworkInfoList retVal = new PAccessNetworkInfoList();
        return retVal.clonehlist(this.hlist);
    }
}
