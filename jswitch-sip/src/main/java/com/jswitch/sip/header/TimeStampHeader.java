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
 * File Name     : TimeStampHeader.java
 * Author        : Phelim O'Doherty
 *
 *  HISTORY
 *  Version   Date      Author              Comments
 *  1.1     08/10/2002  Phelim O'Doherty    
 *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */
package com.jswitch.sip.header;

import com.jswitch.common.exception.InvalidArgumentException;

public interface TimeStampHeader extends Header {


    public void setTimeStamp(float timeStamp) throws InvalidArgumentException;
    

    public float getTimeStamp();

    public long getTime();

    public void setTime(long timeStamp) throws InvalidArgumentException;    
    

    public float getDelay();    


    public void setDelay(float delay) throws InvalidArgumentException;

    public int getTimeDelay();    
    

    public void setTimeDelay(int delay) throws InvalidArgumentException;
    

    public final static String NAME = "Timestamp";
}

