
package com.jswitch.sip.header;

import com.jswitch.sip.URI;

public class ProxyAuthenticate extends AuthenticationHeader implements ProxyAuthenticateHeader {


    private static final long serialVersionUID = 3826145955463251116L;


    public ProxyAuthenticate() {
        super(NAME);
    }


    public URI getURI() {
        return null;
    }


    public void setURI(URI uri) {

    }

}
