
package com.jswitch.sip.header;


import com.jswitch.common.exception.InvalidArgumentException;

import java.text.ParseException;

import static com.jswitch.common.constant.Separators.SEMICOLON;

public class MinSE extends ParametersHeader implements ExtensionHeader, MinSEHeader {


    public static final String NAME = "Min-SE";


    private static final long serialVersionUID = 3134344915465784267L;


    public int expires;


    public MinSE() {
        super(NAME);
    }


    public StringBuilder encodeBody(StringBuilder retval) {
        retval.append(Integer.toString(expires));

        if (!parameters.isEmpty()) {
            retval.append(SEMICOLON);
            parameters.encode(retval);
        }
        return retval;
    }

    public void setValue(String value) throws ParseException {
        throw new ParseException(value,0);
    }


    public int getExpires() {
        return expires;
    }


    public void setExpires(int expires) throws InvalidArgumentException {
        if (expires < 0)
            throw new InvalidArgumentException("bad argument " + expires);
        this.expires = expires;
    }
}
