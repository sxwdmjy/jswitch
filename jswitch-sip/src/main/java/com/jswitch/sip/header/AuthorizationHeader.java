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
 * File Name     : AuthorizationHeader.java
 * Author        : Phelim O'Doherty
 *
 *  HISTORY
 *  Version   Date      Author              Comments
 *  1.1     08/10/2002  Phelim O'Doherty    
 *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */
package com.jswitch.sip.header;


import com.jswitch.sip.URI;

import java.text.ParseException;

public interface AuthorizationHeader extends Parameters, Header {


    public void setScheme(String scheme);



    public String getScheme();


    public void setRealm(String realm) throws ParseException;



    public String getRealm();


    public void setUsername(String username) throws ParseException;


    public String getUsername();

    

    public void setNonce(String nonce) throws ParseException;


    public String getNonce();    
    

    public void setURI(URI uri);


    public URI getURI();     
    

    public void setResponse(String response) throws ParseException;


    public String getResponse();


    public void setAlgorithm(String algorithm) throws ParseException;


    public String getAlgorithm();    


    public void setCNonce(String cNonce) throws ParseException;


    public String getCNonce();        
    

    public void setOpaque(String opaque) throws ParseException;


    public String getOpaque();    


    public void setQop(String qop) throws ParseException;


    public String getQop();    
    

    public void setNonceCount(int nonceCount) throws ParseException;


    public int getNonceCount();    
    

    public final static String NAME = "Authorization";

}

