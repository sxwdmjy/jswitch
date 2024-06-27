
package com.jswitch.sip.header;


import com.jswitch.sip.adress.AddressImpl;

import static com.jswitch.common.constant.Separators.SEMICOLON;

public class Route extends AddressParametersHeader implements RouteHeader {


    private static final long serialVersionUID = 5683577362998368846L;


    public Route() {
        super(NAME);
    }


    public Route(AddressImpl address) {
        super(NAME);
        this.address = address;
    }


    public int hashCode() {
        return this.address.getHostPort().encode().toLowerCase().hashCode();
    }


    public String encodeBody() {
        return encodeBody(new StringBuilder()).toString();
    }

    public StringBuilder encodeBody(StringBuilder buffer) {
        boolean addrFlag = address.getAddressType() == AddressImpl.NAME_ADDR;
        if (!addrFlag) {
            buffer.append('<');
            address.encode(buffer);
            buffer.append('>');
        } else {
            address.encode(buffer);
        }
        if (!parameters.isEmpty()) {
            buffer.append(SEMICOLON);
            parameters.encode(buffer);
        }
        return buffer;
    }

    public boolean equals(Object other) {
        return (other instanceof RouteHeader) && super.equals(other);
    }

}

