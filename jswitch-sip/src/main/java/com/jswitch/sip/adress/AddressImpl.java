package com.jswitch.sip.adress;

import com.jswitch.sip.*;

import static com.jswitch.common.constant.Separators.*;

/**
 * @author danmo
 * @date 2024-06-18 14:55
 **/
public final class AddressImpl extends NetObject implements Address {

    private static final long serialVersionUID = 1L;


    public static final int NAME_ADDR = 1;


    public static final int ADDRESS_SPEC = 2;


    public static final int WILD_CARD = 3;

    protected int addressType;


    protected String displayName;


    protected GenericURI address;


    public boolean match(Object other) {
        if (other == null)
            return true;
        if (!(other instanceof Address))
            return false;
        else {
            AddressImpl that = (AddressImpl) other;
            if (that.getMatcher() != null)
                return that.getMatcher().match(this.encode());
            else if (that.displayName != null && this.displayName == null)
                return false;
            else if (that.displayName == null)
                return address.match(that.address);
            else
                return displayName.equalsIgnoreCase(that.displayName)
                        && address.match(that.address);
        }

    }

    public HostPort getHostPort() {
        if (!(address instanceof SipUri uri))
            throw new RuntimeException("address is not a SipUri");
        return uri.getHostPort();
    }


    public int getPort() {
        if (!(address instanceof SipUri uri))
            throw new RuntimeException("address is not a SipUri");
        return uri.getHostPort().getPort();
    }


    public String getUserAtHostPort() {
        if (address instanceof SipUri) {
            SipUri uri = (SipUri) address;
            return uri.getUserAtHostPort();
        } else
            return address.toString();
    }


    public String getHost() {
        if (!(address instanceof SipUri))
            throw new RuntimeException("address is not a SipUri");
        SipUri uri = (SipUri) address;
        return uri.getHostPort().getHost().getHostname();
    }


    public void removeParameter(String parameterName) {
        if (!(address instanceof SipUri))
            throw new RuntimeException("address is not a SipUri");
        SipUri uri = (SipUri) address;
        uri.removeParameter(parameterName);
    }


    public String encode() {
        return encode(new StringBuilder()).toString();
    }

    public StringBuilder encode(StringBuilder buffer) {
        if (this.addressType == WILD_CARD) {
            buffer.append('*');
        } else {
            if (displayName != null) {
                buffer.append(DOUBLE_QUOTE)
                        .append(displayName)
                        .append(DOUBLE_QUOTE)
                        .append(SP);
            }
            if (address != null) {
                if (addressType == NAME_ADDR || displayName != null)
                    buffer.append(LESS_THAN);
                address.encode(buffer);
                if (addressType == NAME_ADDR || displayName != null)
                    buffer.append(GREATER_THAN);
            }
        }
        return buffer;
    }

    public AddressImpl() {
        this.addressType = NAME_ADDR;
    }


    public int getAddressType() {
        return addressType;
    }


    public void setAddressType(int atype) {
        addressType = atype;
    }


    public String getDisplayName() {
        return displayName;
    }


    public void setDisplayName(String displayName) {
        this.displayName = displayName;
        this.addressType = NAME_ADDR;
    }


    public void setAddess(URI address) {
        this.address = (GenericURI) address;
    }


    public int hashCode() {
        return this.address.hashCode();
    }


    public boolean equals(Object other) {

        if (this == other) return true;

        if (other instanceof Address) {
            final Address o = (Address) other;

            return this.getURI().equals(o.getURI());
        }
        return false;
    }


    public boolean hasDisplayName() {
        return (displayName != null);
    }


    public void removeDisplayName() {
        displayName = null;
    }


    public boolean isSIPAddress() {
        return address instanceof SipUri;
    }


    public URI getURI() {
        return this.address;
    }


    public boolean isWildcard() {
        return this.addressType == WILD_CARD;
    }


    public void setURI(URI address) {
        this.address = (GenericURI) address;
    }


    public void setUser(String user) {
        ((SipUri) this.address).setUser(user);
    }


    public void setWildCardFlag() {
        this.addressType = WILD_CARD;
        this.address = new SipUri();
        ((SipUri) this.address).setUser("*");
    }

    public Object clone() {
        AddressImpl retval = (AddressImpl) super.clone();
        if (this.address != null)
            retval.address = (GenericURI) this.address.clone();
        return retval;
    }
}
