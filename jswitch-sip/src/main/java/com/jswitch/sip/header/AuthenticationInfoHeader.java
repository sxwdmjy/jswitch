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
 * File Name     : AuthenticationInfoHeader.java
 * Author        : Phelim O'Doherty
 *
 *  HISTORY
 *  Version   Date      Author              Comments
 *  1.1     08/10/2002  Phelim O'Doherty    Initial version
 *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */
package com.jswitch.sip.header;

import java.text.ParseException;

public interface AuthenticationInfoHeader extends Parameters, Header {


    public void setNextNonce(String nextNonce) throws ParseException;


    public String getNextNonce();   
    

    public void setQop(String qop) throws ParseException;


    public String getQop();        


    public void setCNonce(String cNonce) throws ParseException;


    public String getCNonce();  
    

    public void setNonceCount(int nonceCount) throws ParseException;


    public int getNonceCount();    
    

    public void setResponse(String response) throws ParseException;


    public String getResponse();    
    

    public final static String NAME = "Authentication-Info";

}

