package com.jswitch.sip.header;


import com.jswitch.sip.adress.AddressImpl;

import java.text.ParseException;

import static com.jswitch.common.constant.Separators.GREATER_THAN;
import static com.jswitch.common.constant.Separators.LESS_THAN;

/**
 * P-Preferred-Identity SIP Private Header - RFC 3325.
 */



public class PPreferredIdentity extends AddressParametersHeader implements PPreferredIdentityHeader, SIPHeaderNamesIms , ExtensionHeader {


    public PPreferredIdentity(AddressImpl address) {
        super(NAME);
        this.address = address;
    }

    public PPreferredIdentity() {
        super(P_PREFERRED_IDENTITY);
    }


    public StringBuilder encodeBody(StringBuilder retval) {
        if (address.getAddressType() == AddressImpl.ADDRESS_SPEC) {
            retval.append(LESS_THAN);
        }
        address.encode(retval);
        if (address.getAddressType() == AddressImpl.ADDRESS_SPEC) {
            retval.append(GREATER_THAN);
        }


        return retval;
    }

    public void setValue(String value) throws ParseException {
        throw new ParseException (value,0);

    }

}

