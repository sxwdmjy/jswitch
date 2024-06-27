package com.jswitch.sip.header;


import com.jswitch.common.exception.InvalidArgumentException;
import com.jswitch.sip.ParameterNames;

import java.text.ParseException;

import static com.jswitch.common.constant.Separators.SEMICOLON;

public class Reason extends ParametersHeader implements ReasonHeader {


    private static final long serialVersionUID = -8903376965568297388L;
    public final String TEXT = ParameterNames.TEXT;
    public final String CAUSE = ParameterNames.CAUSE;

    protected String protocol;


    public int getCause() {
        return getParameterAsInt(CAUSE);
    }


    public void setCause(int cause) throws InvalidArgumentException {
        this.parameters.set("cause", cause);
    }


    public void setProtocol(String protocol) throws ParseException {
        this.protocol = protocol;
    }

    public String getProtocol() {
        return this.protocol;
    }


    public void setText(String text) throws ParseException {
        if (text != null && !text.trim().isEmpty()) {
            if (text.charAt(0) != '"') {
                text = '"' + text.replace("\"", "\\\"") + '"';
            }
            this.parameters.set("text", text);
        }
    }

    public String getText() {
        return this.parameters.getParameter("text");
    }


    public Reason() {
        super(NAME);
    }


    public String getName() {
        return NAME;

    }

    public StringBuilder encodeBody(StringBuilder buffer) {
        buffer.append(protocol);
        if (parameters != null && !parameters.isEmpty()) {
            buffer.append(SEMICOLON);
            buffer = parameters.encode(buffer);
        }
        return buffer;
    }

}
