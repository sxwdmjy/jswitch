package com.jswitch.sip.header;


import java.text.ParseException;

import static com.jswitch.sip.SIPHeaderNames.REQUIRE;

public class Require extends SIPHeader implements RequireHeader {


    private static final long serialVersionUID = -3743425404884053281L;

    protected String optionTag;


    public Require() {
        super(REQUIRE);
    }


    public Require(String s) {
        super(REQUIRE);
        optionTag = s;
    }


    public StringBuilder encodeBody(StringBuilder retval) {
        return retval.append(optionTag);
    }


    public void setOptionTag(String optionTag) throws ParseException {
        if (optionTag == null)
            throw new NullPointerException("SIP Exception, Require, setOptionTag(), the optionTag parameter is null");
        this.optionTag = optionTag;
    }

    public String getOptionTag() {
        return optionTag;
    }
}
