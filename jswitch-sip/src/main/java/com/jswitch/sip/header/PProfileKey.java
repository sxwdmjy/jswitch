package com.jswitch.sip.header;

import com.jswitch.sip.adress.AddressImpl;

import java.text.ParseException;

import static com.jswitch.common.constant.Separators.*;

public class PProfileKey extends AddressParametersHeader implements PProfileKeyHeader, SIPHeaderNamesIms , ExtensionHeader {

    public PProfileKey( ) {
        super(P_PROFILE_KEY);

    }

    public PProfileKey(AddressImpl address)
    {
        super(NAME);
        this.address = address;
    }

    @Override
    public StringBuilder encodeBody(StringBuilder retval) {


        if (address.getAddressType() == AddressImpl.ADDRESS_SPEC) {
            retval.append(LESS_THAN);
        }
        retval.append(address.encode());
        if (address.getAddressType() == AddressImpl.ADDRESS_SPEC) {
            retval.append(GREATER_THAN);
        }
        if (!parameters.isEmpty())
            retval.append(SEMICOLON + this.parameters.encode());

        return retval;
    }

    public void setValue(String value) throws ParseException {
        throw new ParseException(value,0);

    }

    public boolean equals(Object other)
    {
        return (other instanceof PProfileKey) && super.equals(other);

    }


    public Object clone() {
        return (PProfileKey) super.clone();
    }

}
