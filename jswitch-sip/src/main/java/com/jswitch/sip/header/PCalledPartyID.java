package com.jswitch.sip.header;


import com.jswitch.sip.adress.AddressImpl;

import java.text.ParseException;

import static com.jswitch.common.constant.Separators.*;

public class PCalledPartyID extends AddressParametersHeader implements PCalledPartyIDHeader, SIPHeaderNamesIms , ExtensionHeader{


    public PCalledPartyID(AddressImpl address) {
        super(NAME);
        this.address = address;
    }

    public PCalledPartyID() {
        super(CALLED_PARTY_ID);

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
            retval= this.parameters.encode(retval);
        } 
        return retval;
    }

    public void setValue(String value) throws ParseException {
        throw new ParseException(value,0);

    }

}