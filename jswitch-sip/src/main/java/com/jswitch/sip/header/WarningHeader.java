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
 * File Name     : WarningHeader.java
 * Author        : Phelim O'Doherty
 * <p>
 * HISTORY
 * Version   Date      Author              Comments
 * 1.1     08/10/2002  Phelim O'Doherty
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */
package com.jswitch.sip.header;


import com.jswitch.common.exception.InvalidArgumentException;

import java.text.ParseException;

public interface WarningHeader extends Header {


    public String getAgent();


    public void setAgent(String agent) throws ParseException;


    public String getText();

    public void setText(String text) throws ParseException;

    public void setCode(int code) throws InvalidArgumentException;


    public int getCode();

    public final static String NAME = "Warning";

    public final static int INCOMPATIBLE_NETWORK_PROTOCOL = 300;


    public final static int INCOMPATIBLE_NETWORK_ADDRESS_FORMATS = 301;


    public final static int INCOMPATIBLE_TRANSPORT_PROTOCOL = 302;


    public final static int INCOMPATIBLE_BANDWIDTH_UNITS = 303;


    public final static int MEDIA_TYPE_NOT_AVAILABLE = 304;

    public final static int INCOMPATIBLE_MEDIA_FORMAT = 305;


    public final static int ATTRIBUTE_NOT_UNDERSTOOD = 306;

    public final static int SESSION_DESCRIPTION_PARAMETER_NOT_UNDERSTOOD = 307;


    public final static int MULTICAST_NOT_AVAILABLE = 330;


    public final static int UNICAST_NOT_AVAILABLE = 331;


    public final static int INSUFFICIENT_BANDWIDTH = 370;


    public final static int MISCELLANEOUS_WARNING = 399;

}
