
package com.jswitch.sip.header;


public class WarningList extends SIPHeaderList<Warning> {

    private static final long serialVersionUID = -1423278728898430175L;

    public Object clone() {
        WarningList retval = new WarningList();
        return retval.clonehlist(this.hlist);
    }

    public WarningList() {
        super(Warning.class, Warning.NAME);
    }
}
