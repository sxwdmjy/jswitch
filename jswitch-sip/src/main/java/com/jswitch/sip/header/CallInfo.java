
package com.jswitch.sip.header;


import com.jswitch.sip.GenericURI;
import com.jswitch.sip.URI;

import java.text.ParseException;

import static com.jswitch.common.constant.Separators.*;

public final class CallInfo extends ParametersHeader implements CallInfoHeader {


    private static final long serialVersionUID = -8179246487696752928L;

    protected GenericURI info;


    public CallInfo() {
        super(CALL_INFO);
    }


    public String encodeBody() {
        return encodeBody(new StringBuilder()).toString();
    }

    public StringBuilder encodeBody(StringBuilder buffer) {
        buffer.append(LESS_THAN);
        info.encode(buffer);
        buffer.append(GREATER_THAN);

        if (parameters != null && !parameters.isEmpty()) {
            buffer.append(SEMICOLON);
            parameters.encode(buffer);
        }

        return buffer;
    }


    public String getPurpose() {
        return this.getParameter("purpose");
    }


    public URI getInfo() {
        return info;
    }


    public void setPurpose(String purpose) {
        if (purpose == null)
            throw new NullPointerException("null arg");
        try {
            this.setParameter("purpose", purpose);
        } catch (ParseException ex) {
        }
    }


    public void setInfo(URI info) {
        this.info = (GenericURI) info;
    }

    public Object clone() {
        CallInfo retval = (CallInfo) super.clone();
        if (this.info != null)
            retval.info = (GenericURI) this.info.clone();
        return retval;
    }
}
