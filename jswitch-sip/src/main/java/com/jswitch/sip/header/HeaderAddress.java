package com.jswitch.sip.header;

import com.jswitch.sip.adress.Address;

/**
 * @author danmo
 * @date 2024-06-18 14:53
 **/
public interface HeaderAddress {

    public void setAddress(Address address);


    public Address getAddress();
}
