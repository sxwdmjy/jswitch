package com.jswitch.sip.header;

import com.jswitch.sip.HostPort;
import com.jswitch.sip.ParameterNames;
import com.jswitch.sip.adress.Address;
import com.jswitch.sip.adress.AddressImpl;
import lombok.Getter;

import java.text.ParseException;

import static com.jswitch.common.constant.Separators.*;

/**
 * @author danmo
 * @date 2024-06-18 14:52
 **/
@Getter
public final class From extends AddressParametersHeader implements FromHeader {


    private static final long serialVersionUID = -1L;


    public From() {
        super(NAME);
    }


    public From(To to) {
        super(NAME);
        address = to.address;
        parameters = to.parameters;
    }


    protected String encodeBody() {
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
            parameters.encode(buffer);
        }
        return buffer;
    }


    public HostPort getHostPort() {
        return address.getHostPort();
    }


    public String getDisplayName() {
        return address.getDisplayName();
    }


    public String getTag() {
        if (parameters == null)
            return null;
        return getParameter(ParameterNames.TAG);
    }


    public boolean hasTag() {
        return hasParameter(ParameterNames.TAG);
    }


    public void removeTag() {
        parameters.delete(ParameterNames.TAG);
    }


    public void setAddress(Address address) {
        this.address = (AddressImpl) address;
    }


    public void setTag(String t) throws ParseException {
        //Parser.checkToken(t);
        this.setParameter(ParameterNames.TAG, t);
    }


    public String getUserAtHostPort() {
        return address.getUserAtHostPort();
    }

    public boolean equals(Object other) {
        return (other instanceof FromHeader) && super.equals(other);
    }
}
