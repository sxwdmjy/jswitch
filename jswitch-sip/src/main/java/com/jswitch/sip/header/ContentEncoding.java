/*
 * Conditions Of Use
 *
 * This software was developed by employees of the National Institute of
 * Standards and Technology (NIST), an agency of the Federal Government.
 * Pursuant to title 15 Untied States Code Section 105, works of NIST
 * employees are not subject to copyright protection in the United States
 * and are considered to be in the public domain.  As a result, a formal
 * license is not needed to use the software.
 *
 * This software is provided by NIST as a service and is expressly
 * provided "AS IS."  NIST MAKES NO WARRANTY OF ANY KIND, EXPRESS, IMPLIED
 * OR STATUTORY, INCLUDING, WITHOUT LIMITATION, THE IMPLIED WARRANTY OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE, NON-INFRINGEMENT
 * AND DATA ACCURACY.  NIST does not warrant or make any representations
 * regarding the use of the software or the results thereof, including but
 * not limited to the correctness, accuracy, reliability or usefulness of
 * the software.
 *
 * Permission to use this software is contingent upon your acceptance
 * of the terms of this agreement
 *
 * .
 *
 */
/*******************************************************************************
 * Product of NIST/ITL Advanced Networking Technologies Division (ANTD).        *
 *******************************************************************************/
package com.jswitch.sip.header;

import java.text.ParseException;

import static com.jswitch.sip.SIPHeaderNames.CONTENT_ENCODING;

public class ContentEncoding extends SIPHeader implements ContentEncodingHeader {

    private static final long serialVersionUID = 2034230276579558857L;
    protected String contentEncoding;

    public ContentEncoding() {
        super(CONTENT_ENCODING);
    }


    public ContentEncoding(String enc) {
        super(CONTENT_ENCODING);
        contentEncoding = enc;
    }


    public StringBuilder encodeBody(StringBuilder buffer) {
        return buffer.append(contentEncoding);
    }


    public String getEncoding() {
        return contentEncoding;
    }


    public void setEncoding(String encoding) throws ParseException {
        if (encoding == null)
            throw new NullPointerException("SIP Exception, " + " encoding is null");
        contentEncoding = encoding;
    }
}
