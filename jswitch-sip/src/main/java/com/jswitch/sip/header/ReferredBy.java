
package com.jswitch.sip.header;


import com.jswitch.sip.adress.AddressImpl;

import java.text.ParseException;

import static com.jswitch.common.constant.Separators.*;

public final class ReferredBy extends AddressParametersHeader implements ExtensionHeader, ReferredByHeader {


    private static final long serialVersionUID = 3134344915465784267L;

    public static final String NAME = "Referred-By";


    public ReferredBy() {
        super(NAME);
    }

    public void setValue(String value) throws ParseException {
        throw new ParseException(value,0);

    }


    public StringBuilder encodeBody(StringBuilder retval) {
        if (address == null)
            return null;
        if (address.getAddressType() == AddressImpl.ADDRESS_SPEC) {
            retval.append(LESS_THAN);
        }
        address.encode(retval);
        if (address.getAddressType() == AddressImpl.ADDRESS_SPEC) {
            retval.append(GREATER_THAN);
        }

        if (!parameters.isEmpty()) {
            retval.append(SEMICOLON); 
            parameters.encode(retval);
        }
        return retval;
    }
}

