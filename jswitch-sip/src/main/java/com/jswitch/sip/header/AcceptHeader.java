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
 * File Name     : AcceptHeader.java
 * Author        : Phelim O'Doherty
 *
 *  HISTORY
 *  Version   Date      Author              Comments
 *  1.1     08/10/2002  Phelim O'Doherty   
 *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */
package com.jswitch.sip.header;


import com.jswitch.common.exception.InvalidArgumentException;

public interface AcceptHeader extends MediaType, Parameters, Header {


    public void setQValue(float qValue) throws InvalidArgumentException;


    public float getQValue();



    public boolean allowsAllContentSubTypes();



    /**

     * Gets boolean value to indicate if the AcceptHeader allows all media

     * types, that is the content type is "*".

     *

     * @return true if all contenet types are allowed, false otherwise.

     */

    public boolean allowsAllContentTypes();



    /**

     * Name of AcceptHeader

     */

    public final static String NAME = "Accept";



}

