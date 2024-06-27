package com.jswitch.sip.header;

import com.jswitch.sip.adress.Address;
import com.jswitch.sip.adress.AddressImpl;

/**
 * @author danmo
 * @date 2024-06-18 14:54
 **/
public abstract class AddressParametersHeader extends ParametersHeader implements Parameters {

    protected AddressImpl address;


    public Address getAddress() {
        return address;
    }


    public void setAddress(Address address) {
        this.address = (AddressImpl) address;
    }


    protected AddressParametersHeader(String name) {
        super(name);
    }


    protected AddressParametersHeader(String name, boolean sync) {
        super(name,sync);
    }


    public Object clone() {
        AddressParametersHeader retval = (AddressParametersHeader) super.clone();
        if (this.address != null)
            retval.address = (AddressImpl) this.address.clone();
        return retval;
    }


    public boolean equals(Object other) {
        if (this==other) return true;
        if (other instanceof HeaderAddress && other instanceof Parameters) {
            final HeaderAddress o = (HeaderAddress) other;
            return this.getAddress().equals( o.getAddress() ) && this.equalParameters( (Parameters) o );
        }
        return false;
    }
}
