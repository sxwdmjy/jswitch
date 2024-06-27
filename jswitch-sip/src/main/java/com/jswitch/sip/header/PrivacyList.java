package com.jswitch.sip.header;


public class PrivacyList extends SIPHeaderList<Privacy> {

    private static final long serialVersionUID = 1798720509806307461L;


    public PrivacyList() {
        super(Privacy.class, PrivacyHeader.NAME);
    }


    public Object clone() {
        PrivacyList retval = new PrivacyList();
        return retval.clonehlist(this.hlist);
    }




}
