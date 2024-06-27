
package com.jswitch.sip.header;

public class ErrorInfoList extends SIPHeaderList<ErrorInfo> {


    private static final long serialVersionUID = 1L;

    public Object clone() {
        ErrorInfoList retval = new ErrorInfoList();
        retval.clonehlist(this.hlist);
        return retval;
    }


    public ErrorInfoList() {
        super(ErrorInfo.class, ErrorInfoHeader.NAME);
    }
}
