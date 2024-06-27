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


import com.jswitch.sip.NameValue;
import com.jswitch.sip.NameValueList;
import com.jswitch.sip.ParameterNames;
import com.jswitch.sip.SIPObject;

import static com.jswitch.common.constant.Separators.COMMA;
import static com.jswitch.common.constant.Separators.SP;
import static com.jswitch.sip.ParameterNames.*;

public class Challenge extends SIPObject {


    private static final long serialVersionUID = 5944455875924336L;

    protected String scheme;


    protected NameValueList authParams;


    public Challenge() {
        authParams = new NameValueList();
        authParams.setSeparator(COMMA);
    }


    public String encode() {
        return new StringBuilder(scheme)
                .append(SP)
                .append(authParams.encode())
                .toString();
    }


    public String getScheme() {
        return scheme;
    }


    public NameValueList getAuthParams() {
        return authParams;
    }

    public String getDomain() {
        return (String) authParams.getValue(DOMAIN);
    }


    public String getURI() {
        return (String) authParams.getValue(URI);
    }

    public String getOpaque() {
        return (String) authParams.getValue(OPAQUE);
    }


    public String getQOP() {
        return (String) authParams.getValue(QOP);
    }


    public String getAlgorithm() {
        return (String) authParams.getValue(ALGORITHM);
    }


    public String getStale() {
        return (String) authParams.getValue(STALE);
    }


    public String getSignature() {
        return (String) authParams.getValue(SIGNATURE);
    }


    public String getSignedBy() {
        return (String) authParams.getValue(SIGNED_BY);
    }


    public String getResponse() {
        return (String) authParams.getValue(RESPONSE);
    }


    public String getRealm() {
        return (String) authParams.getValue(REALM);
    }


    public String getParameter(String name) {
        return (String) authParams.getValue(name);
    }


    public boolean hasParameter(String name) {
        return authParams.getNameValue(name) != null;
    }


    public boolean hasParameters() {
        return !authParams.isEmpty();
    }


    public boolean removeParameter(String name) {
        return authParams.delete(name);
    }


    public void removeParameters() {
        authParams = new NameValueList();
    }

    public void setParameter(NameValue nv) {
        authParams.set(nv);
    }


    public void setScheme(String s) {
        scheme = s;
    }


    public void setAuthParams(NameValueList a) {
        authParams = a;
    }

    public Object clone() {
        Challenge retval = (Challenge) super.clone();
        if (this.authParams != null)
            retval.authParams = (NameValueList) this.authParams.clone();
        return retval;
    }
}
