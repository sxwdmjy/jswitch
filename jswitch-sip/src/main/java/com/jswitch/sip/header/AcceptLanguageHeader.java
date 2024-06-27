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
 * File Name     : AcceptLanguageHeader.java
 * Author        : Phelim O'Doherty
 * <p>
 * HISTORY
 * Version   Date      Author              Comments
 * 1.1     08/10/2002  Phelim O'Doherty
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */
package com.jswitch.sip.header;


import com.jswitch.common.exception.InvalidArgumentException;

import java.util.Locale;


public interface AcceptLanguageHeader extends Parameters, Header {


    public float getQValue();

    public void setQValue(float qValue) throws InvalidArgumentException;


    public Locale getAcceptLanguage();


    public void setAcceptLanguage(Locale language);


    public final static String NAME = "Accept-Language";

}

