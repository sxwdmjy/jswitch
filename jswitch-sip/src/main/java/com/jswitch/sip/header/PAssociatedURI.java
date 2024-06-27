
package com.jswitch.sip.header;


import com.jswitch.sip.adress.AddressImpl;
import com.jswitch.sip.GenericURI;
import com.jswitch.sip.URI;

import java.text.ParseException;

import static com.jswitch.common.constant.Separators.*;

public class PAssociatedURI extends AddressParametersHeader implements PAssociatedURIHeader, SIPHeaderNamesIms, ExtensionHeader
{

    public PAssociatedURI()
    {
        super(PAssociatedURIHeader.NAME);
    }


    public PAssociatedURI(AddressImpl address)
    {
        super(PAssociatedURIHeader.NAME);
        this.address = address;
    }


    public PAssociatedURI(GenericURI associatedURI)
    {
        super(PAssociatedURIHeader.NAME);
        this.address = new AddressImpl();
        this.address.setURI(associatedURI);
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
            retval= retval.append(SEMICOLON);
            retval= this.parameters.encode(retval);
        }        
        return retval;
    }



    public void setAssociatedURI(URI associatedURI) throws NullPointerException
    {
        if (associatedURI == null)
            throw new NullPointerException("null URI");

        this.address.setURI(associatedURI);
    }


    public URI getAssociatedURI() {
        return this.address.getURI();
    }


    public Object clone() {
        PAssociatedURI retval = (PAssociatedURI) super.clone();
        if (this.address != null)
            retval.address = (AddressImpl) this.address.clone();
        return retval;
    }


    public void setValue(String value) throws ParseException {
        throw new ParseException(value,0);

    }

}
