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
 * File Name     : SubscriptionStateHeader.java
 * Author        : Phelim O'Doherty
 *
 *  HISTORY
 *  Version   Date      Author              Comments
 *  1.1     13/12/2002  Phelim O'Doherty    Initial version, extension header to
 *                                          support RFC3265
 *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */
package com.jswitch.sip.header;


import com.jswitch.common.exception.InvalidArgumentException;

import java.text.ParseException;

public interface SubscriptionStateHeader extends Parameters, Header {


    public void setExpires(int expires) throws InvalidArgumentException;


    public int getExpires();


    public void setRetryAfter(int retryAfter) throws InvalidArgumentException;


    public int getRetryAfter();


    public String getReasonCode();


    public void setReasonCode(String reasonCode) throws ParseException;


    public String getState();


    public void setState(String state) throws ParseException;



    public final static String NAME = "Subscription-State";

    public final static String UNKNOWN = "unknown";


    public final static String DEACTIVATED = "deactivated";


    public final static String PROBATION = "probation";


    public final static String REJECTED = "rejected";


    public final static String TIMEOUT = "timeout";


    public final static String GIVE_UP = "giveup";


    public final static String NO_RESOURCE = "noresource";

    public final static String ACTIVE = "active";


    public final static String TERMINATED = "terminated";


    public final static String PENDING = "pending";

}

