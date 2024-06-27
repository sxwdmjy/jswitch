package com.jswitch.sip.header;


import com.jswitch.sip.adress.AddressImpl;

import java.text.ParseException;

import static com.jswitch.common.constant.Separators.*;

/**
 * SERVICE-ROUTE header SIP param: RFC 3608.
 *
 */


public class ServiceRoute extends AddressParametersHeader implements ServiceRouteHeader, SIPHeaderNamesIms, ExtensionHeader {


    public ServiceRoute(AddressImpl address) {
        super(NAME);
        this.address = address;
    }

    public ServiceRoute() {
        super(SERVICE_ROUTE);
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


    public void setValue(String value) throws ParseException {
        throw new ParseException (value,0);

    }


}
