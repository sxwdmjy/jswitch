package com.jswitch.sip.header;


import com.jswitch.common.constant.Separators;
import com.jswitch.sip.adress.AddressImpl;

import static com.jswitch.common.constant.Separators.GREATER_THAN;
import static com.jswitch.common.constant.Separators.SEMICOLON;

public final class ReferTo extends AddressParametersHeader implements ReferToHeader {


    private static final long serialVersionUID = -1666700428440034851L;


    public ReferTo() {
        super(NAME);
    }


    public StringBuilder encodeBody(StringBuilder retval) {
        if (address == null)
            return null;        
        if (address.getAddressType() == AddressImpl.ADDRESS_SPEC) {
            retval.append(Separators.LESS_THAN);
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

