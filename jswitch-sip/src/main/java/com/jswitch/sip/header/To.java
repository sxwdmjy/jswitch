package com.jswitch.sip.header;

import com.jswitch.sip.HostPort;
import com.jswitch.sip.ParameterNames;
import com.jswitch.sip.adress.AddressImpl;

import java.text.ParseException;

import static com.jswitch.common.constant.Separators.*;

/**
 * @author danmo
 * @date 2024-06-18 15:00
 **/
public final class To extends AddressParametersHeader implements ToHeader {

    public To() {
        super(ToHeader.NAME, true);
    }

    /**
     * Generate a TO header from a FROM header
     */
    public To(From from) {
        super(ToHeader.NAME);
        setAddress(from.address);
        setParameters(from.parameters);
    }


    public String encode() {
        return headerName + COLON + SP + encodeBody() + NEWLINE;
    }


    protected String encodeBody() {
        return encodeBody(new StringBuilder()).toString();
    }

    public StringBuilder encodeBody(StringBuilder buffer) {
        if (address != null) {
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
        }
        return buffer;
    }


    public HostPort getHostPort() {
        if (address == null)
            return null;
        return address.getHostPort();
    }


    public String getDisplayName() {
        if (address == null)
            return null;
        return address.getDisplayName();
    }


    public String getTag() {
        if (parameters == null)
            return null;
        return getParameter(ParameterNames.TAG);

    }


    public boolean hasTag() {
        if (parameters == null)
            return false;
        return hasParameter(ParameterNames.TAG);

    }


    public void removeTag() {
        if (parameters != null)
            parameters.delete(ParameterNames.TAG);

    }


    public void setTag(String t) throws ParseException {
        this.setParameter(ParameterNames.TAG, t);
    }


    public String getUserAtHostPort() {
        if (address == null)
            return null;
        return address.getUserAtHostPort();
    }

    public boolean equals(Object other) {
        return (other instanceof ToHeader) && super.equals(other);
    }
}
