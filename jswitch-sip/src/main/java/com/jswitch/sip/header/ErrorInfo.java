
package com.jswitch.sip.header;


import com.jswitch.sip.GenericURI;
import com.jswitch.sip.URI;

import java.text.ParseException;

import static com.jswitch.common.constant.Separators.*;

public final class ErrorInfo extends ParametersHeader implements ErrorInfoHeader {


    private static final long serialVersionUID = -6347702901964436362L;

    protected GenericURI errorInfo;


    public ErrorInfo() {
        super(NAME);
    }

    public ErrorInfo(GenericURI errorInfo) {
        this();
        this.errorInfo = errorInfo;
    }


    public StringBuilder encodeBody(StringBuilder retval) {
            retval.append(LESS_THAN);
            errorInfo.encode(retval);
            retval.append(GREATER_THAN);
        if (!parameters.isEmpty()) {
            retval.append(SEMICOLON);
            parameters.encode(retval);
        }
        return retval;
    }


    public void setErrorInfo(URI errorInfo) {
        this.errorInfo = (GenericURI) errorInfo;

    }

    public URI getErrorInfo() {
        return errorInfo;
    }


    public void setErrorMessage(String message) throws ParseException {
        if (message == null)
            throw new NullPointerException("SIP Exception , ErrorInfoHeader, setErrorMessage(), the message parameter is null");
        setParameter("message", message);
    }


    public String getErrorMessage() {
        return getParameter("message");
    }

    public Object clone() {
        ErrorInfo retval = (ErrorInfo) super.clone();
        if (this.errorInfo != null)
            retval.errorInfo = (GenericURI) this.errorInfo.clone();
        return retval;
    }
}

