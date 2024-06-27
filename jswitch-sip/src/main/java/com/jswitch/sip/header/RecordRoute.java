
package com.jswitch.sip.header;


import com.jswitch.sip.adress.AddressImpl;

import static com.jswitch.common.constant.Separators.*;

public class RecordRoute extends AddressParametersHeader implements RecordRouteHeader {


    private static final long serialVersionUID = 2388023364181727205L;


    public RecordRoute(AddressImpl address) {
        super(NAME);
        this.address = address;
    }


    public RecordRoute() {
        super(RECORD_ROUTE);

    }


    public String encodeBody() {
        return encodeBody(new StringBuilder()).toString();
    }

    public StringBuilder encodeBody(StringBuilder buffer) {
        if (address.getAddressType() == AddressImpl.ADDRESS_SPEC) {
            buffer.append(LESS_THAN);
        }
        address.encode(buffer);
        if (address.getAddressType() == AddressImpl.ADDRESS_SPEC) {
            buffer.append(GREATER_THAN);
        }

        if (!parameters.isEmpty()) {
            buffer.append(SEMICOLON);
            this.parameters.encode(buffer);
        }
        return buffer;
    }
}
