
package com.jswitch.sip.header;

import java.util.Calendar;

import static com.jswitch.sip.SIPHeaderNames.DATE;

public class SIPDateHeader extends SIPHeader implements DateHeader {


    private static final long serialVersionUID = 1734186339037274664L;

    protected SIPDate date;


    public SIPDateHeader() {
        super(DATE);
    }


    public StringBuilder encodeBody(StringBuilder retval) {
        return date.encode(retval);
    }


    public void setDate(SIPDate d) {
        date = d;

    }


    public void setDate(Calendar dat) {
        if (dat != null)
            date = new SIPDate(dat.getTime().getTime());
    }

    public Calendar getDate() {
        if (date == null)
            return null;
        return date.getJavaCal();
    }

    public Object clone() {
        SIPDateHeader retval = (SIPDateHeader) super.clone();
        if (this.date != null)
            retval.date = (SIPDate) this.date.clone();
        return retval;
    }
}

