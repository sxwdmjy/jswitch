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

import static com.jswitch.common.constant.Separators.*;

public class ExtensionHeaderImpl extends SIPHeader implements ExtensionHeader {


    private static final long serialVersionUID = -4593922839612081849L;

    protected String value;

    public ExtensionHeaderImpl() {
    }

    public ExtensionHeaderImpl(String headerName) {
        super(headerName);
    }


    public void setName(String headerName) {
        this.headerName = headerName;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getHeaderValue() {
        if (this.value != null) {
            return this.value;
        } else {
            String encodedHdr = null;
            try {
                encodedHdr = this.encode();
            } catch (Exception ex) {
                return null;
            }
            StringBuilder buffer = new StringBuilder(encodedHdr);
            while (!buffer.isEmpty() && buffer.charAt(0) != ':') {
                buffer.deleteCharAt(0);
            }
            buffer.deleteCharAt(0);
            this.value = buffer.toString().trim();
            return this.value;
        }
    }

    public String encode() {
        return new StringBuffer(this.headerName)
                .append(COLON)
                .append(SP)
                .append(this.value)
                .append(NEWLINE)
                .toString();
    }


    public StringBuilder encodeBody(StringBuilder buffer) {
        return buffer.append(this.encodeBody());
    }


    public String encodeBody() {
        return this.getHeaderValue();
    }
}
