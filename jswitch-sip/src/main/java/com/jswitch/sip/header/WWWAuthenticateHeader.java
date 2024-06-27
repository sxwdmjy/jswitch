/**
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Unpublished - rights reserved under the Copyright Laws of the United States.
 * Copyright � 2003 Sun Microsystems, Inc. All rights reserved.
 * Copyright � 2005 BEA Systems, Inc. All rights reserved.
 *
 * Use is subject to license terms.
 *
 * This distribution may include materials developed by third parties. 
 *
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 *
 * Module Name   : JSIP Specification
 * File Name     : WWWAuthenticateHeader.java
 * Author        : Phelim O'Doherty
 *
 *  HISTORY
 *  Version   Date      Author              Comments
 *  1.1     08/10/2002  Phelim O'Doherty    
 *  1.2     13/06/2005  Phelim O'Doherty    Deprecated get/set URI parameter
 *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */
package com.jswitch.sip.header;


import com.jswitch.sip.URI;

import java.text.ParseException;

public interface WWWAuthenticateHeader extends Parameters, Header {


    public void setScheme(String scheme);

    public String getScheme();    

    public void setRealm(String realm) throws ParseException;

    public String getRealm();

    public void setNonce(String nonce) throws ParseException;

    public String getNonce();    

    public void setURI(URI uri);

    public URI getURI();            

    public void setAlgorithm(String algorithm) throws ParseException;

    public String getAlgorithm();        

    public void setQop(String qop) throws ParseException;
    public String getQop();        

    public void setOpaque(String opaque) throws ParseException;

    public String getOpaque();    

    public void setDomain(String domain) throws ParseException;

    public String getDomain();       

    public void setStale(boolean stale);

    public boolean isStale();      
 

    public final static String NAME = "WWW-Authenticate";
}

