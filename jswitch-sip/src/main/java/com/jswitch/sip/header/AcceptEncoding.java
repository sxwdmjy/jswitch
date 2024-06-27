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


import com.jswitch.common.exception.InvalidArgumentException;

import java.text.ParseException;

import static com.jswitch.common.constant.Separators.SEMICOLON;

public final class AcceptEncoding extends ParametersHeader implements AcceptEncodingHeader {


    private static final long serialVersionUID = -1476807565552873525L;


    protected String contentCoding;


    public AcceptEncoding() {
        super(NAME);
    }


    protected String encodeBody() {
        return encode(new StringBuilder()).toString();
    }

    public StringBuilder encodeBody(StringBuilder buffer) {
        if (contentCoding != null) {
            buffer.append(contentCoding);
        }
        if (parameters != null && !parameters.isEmpty()) {
            buffer.append(SEMICOLON).append(parameters.encode());
        }
        return buffer;
    }


    public float getQValue() {
        return getParameterAsFloat("q");
    }


    public String getEncoding() {
        return contentCoding;
    }


    public void setQValue(float q) throws InvalidArgumentException {
        if (q < 0.0 || q > 1.0)
            throw new InvalidArgumentException("qvalue out of range!");
        super.setParameter("q", q);
    }


    public void setEncoding(String encoding) throws ParseException {
        if (encoding == null)
            throw new NullPointerException(" encoding parameter is null");
        contentCoding = encoding;
    }

}
