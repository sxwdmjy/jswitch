
package com.jswitch.sip.header;


import com.jswitch.common.exception.InvalidArgumentException;

import java.text.ParseException;

import static com.jswitch.common.constant.Separators.DOUBLE_QUOTE;
import static com.jswitch.common.constant.Separators.SP;
import static com.jswitch.sip.SIPHeaderNames.WARNING;

public class Warning extends SIPHeader implements WarningHeader {


    private static final long serialVersionUID = -3433328864230783899L;


    protected int code;


    protected String agent;


    protected String text;


    public Warning() {
        super(WARNING);
    }


    public StringBuilder encodeBody(StringBuilder buffer) {
        return text != null
            ? buffer.append(Integer.toString(code))
            	.append(SP)
                .append(agent)
                .append(SP)
                .append(DOUBLE_QUOTE)
                .append(text)
                .append(DOUBLE_QUOTE)
            : buffer.append(Integer.toString(code)).append(SP).append(agent);
    }


    public int getCode() {
        return code;
    }


    public String getAgent() {
        return agent;
    }


    public String getText() {
        return text;
    }


    public void setCode(int code) throws InvalidArgumentException {
        if (code >99  && code < 1000) { // check this is a 3DIGIT code
            this.code = code;
        } else
            throw new InvalidArgumentException(
                "Code parameter in the Warning header is invalid: code="
                    + code);
    }


    public void setAgent(String host) throws ParseException {
        if (host == null)
            throw new NullPointerException("the host parameter in the Warning header is null");
        else {
            this.agent = host;
        }
    }

    public void setText(String text) throws ParseException {
        if (text == null) {
            throw new ParseException(
                "The text parameter in the Warning header is null",
                0);
        } else
            this.text = text;
    }
}

