
package com.jswitch.sip.header;

import java.text.ParseException;

public class Unsupported extends SIPHeader implements UnsupportedHeader {


    private static final long serialVersionUID = -2479414149440236199L;

    protected String optionTag;


    public Unsupported() {
        super(NAME);
    }


    public Unsupported(String ot) {
        super(NAME);
        optionTag = ot;
    }


    public StringBuilder encodeBody(StringBuilder retval) {
        return retval.append(optionTag);
    }


    public String getOptionTag() {
        return optionTag;
    }


    public void setOptionTag(String o) throws ParseException {
        if (o == null)
            throw new NullPointerException("SIP Exception, Unsupported, setOptionTag(), The option tag parameter is null");
        optionTag = o;
    }
}
