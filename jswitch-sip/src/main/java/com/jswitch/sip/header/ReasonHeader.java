/**
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Unpublished - rights reserved under the Copyright Laws of the United States.
 * Copyright � 2003 Sun Microsystems, Inc. All rights reserved.
 * Copyright � 2005 BEA Systems, Inc. All rights reserved.
 * <p>
 * Use is subject to license terms.
 * <p>
 * This distribution may include materials developed by third parties.
 * <p>
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * <p>
 * Module Name   : JSIP Specification
 * File Name     : ReasonHeader.java
 * Author        : Phelim O'Doherty
 * <p>
 * HISTORY
 * Version   Date      Author              Comments
 * 1.1     08/10/2002  Phelim O'Doherty    Initial version, optional header to
 * support RFC3326.
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */
package com.jswitch.sip.header;

import com.jswitch.common.exception.InvalidArgumentException;

import java.text.ParseException;

public interface ReasonHeader extends Parameters, Header {

    public int getCause();


    public void setCause(int cause) throws InvalidArgumentException;


    public void setProtocol(String protocol) throws ParseException;


    public String getProtocol();

    public void setText(String text) throws ParseException;

    public String getText();


    public final static String NAME = "Reason";

}

