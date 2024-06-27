
package com.jswitch.sip.header;

import lombok.Getter;

import java.text.ParseException;

import static com.jswitch.sip.SIPHeaderNames.ALLOW;


@Getter
public final class Allow extends SIPHeader implements AllowHeader {


    private static final long serialVersionUID = -3105079479020693930L;

    protected String method = "";


    public Allow() {
        super(ALLOW);
    }


    public Allow(String m) {
        super(ALLOW);
        method = m;
    }


    public void setMethod(String method) throws ParseException {
        if (method == null)
            throw new NullPointerException("SIP Exception, Allow, setMethod(), the method parameter is null.");
        this.method = method;
    }

    @Override
    public StringBuilder encodeBody(StringBuilder buffer) {
        return buffer.append(method);
    }
}
