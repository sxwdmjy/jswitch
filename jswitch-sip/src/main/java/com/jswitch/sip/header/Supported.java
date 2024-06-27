package com.jswitch.sip.header;

import com.jswitch.sip.SIPHeaderNames;

import java.text.ParseException;

import static com.jswitch.common.constant.Separators.*;

public class Supported extends SIPHeader implements SupportedHeader {


    private static final long serialVersionUID = -7679667592702854542L;

    protected String optionTag;


    public Supported() {
        super(SIPHeaderNames.SUPPORTED);
        optionTag = null;
    }


    public Supported(String option_tag) {
        super(SIPHeaderNames.SUPPORTED);
        optionTag = option_tag;
    }


    public String encode() {
        String retval = headerName + COLON;
        if (optionTag != null)
            retval += SP + optionTag;
        retval += NEWLINE;
        return retval;
    }


    public StringBuilder encodeBody(StringBuilder retval) {
        return optionTag != null ? retval.append(optionTag) : retval.append("");
    }


    public void setOptionTag(String optionTag) throws ParseException {
        if (optionTag == null)
            throw new NullPointerException("SIP Exception, Supported, setOptionTag(), the optionTag parameter is null");
        this.optionTag = optionTag;
    }


    public String getOptionTag() {
        return optionTag;
    }
}

