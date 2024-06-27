
package com.jswitch.sip.header;


import com.jswitch.common.exception.InvalidArgumentException;

import java.text.ParseException;

import static com.jswitch.common.constant.Separators.SEMICOLON;

public final class SessionExpires extends ParametersHeader implements ExtensionHeader, SessionExpiresHeader {

    private static final long serialVersionUID = 8765762413224043300L;

    public static final String NAME = "Session-Expires";

    public int expires;

    public static final String REFRESHER = "refresher";
    public SessionExpires() {
        super(NAME);
    }

    public int getExpires() {
        return expires;
    }

    public void setExpires(int expires) throws InvalidArgumentException {
        if (expires < 0)
            throw new InvalidArgumentException("bad argument " + expires);
        this.expires = expires;
    }

    public void setValue(String value) throws ParseException {
        throw new ParseException(value,0);

    }


    public StringBuilder encodeBody(StringBuilder retval) {

        retval.append(Integer.toString(expires));

        if (!parameters.isEmpty()) {
    		retval.append(SEMICOLON); 
    		parameters.encode(retval);
    	}
        return retval;
    }

    public String getRefresher() {
       return parameters.getParameter(REFRESHER);
    }

    public void setRefresher(String refresher) {
        this.parameters.set(REFRESHER,refresher);
    }
}



