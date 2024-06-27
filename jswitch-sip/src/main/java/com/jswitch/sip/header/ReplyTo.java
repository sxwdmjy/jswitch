package com.jswitch.sip.header;


import com.jswitch.sip.adress.AddressImpl;
import com.jswitch.sip.HostPort;

import static com.jswitch.common.constant.Separators.*;

public final class ReplyTo extends AddressParametersHeader implements ReplyToHeader {


    private static final long serialVersionUID = -9103698729465531373L;


    public ReplyTo() {
        super(NAME);
    }


    public ReplyTo(AddressImpl address) {
        super(NAME);
        this.address = address;
    }


    public String encode() {
        return headerName + COLON + SP + encodeBody(new StringBuilder()).toString() + NEWLINE;
    }


    public StringBuilder encodeBody(StringBuilder retval) {
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

    public HostPort getHostPort() {
        return address.getHostPort();
    }


    public String getDisplayName() {
        return address.getDisplayName();
    }
}

