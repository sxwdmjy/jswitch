
package com.jswitch.sip.header;

import com.jswitch.sip.URI;

public interface PAssociatedURIHeader extends HeaderAddress, Parameters, Header
{


    public final static String NAME = "P-Associated-URI";


    public void setAssociatedURI(URI associatedURI) throws NullPointerException;

    public URI getAssociatedURI();




}
