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
 * of the terms of this agreement.
 *
 */
/*****************************************************************************
 * Product of NIST/ITL Advanced Networking Technologies Division (ANTD).     *
 ******************************************************************************/
package com.jswitch.sip.header;


import com.jswitch.common.constant.Separators;
import com.jswitch.sip.NameValue;
import com.jswitch.sip.ParameterNames;
import com.jswitch.sip.URI;
import lombok.Getter;

import java.text.ParseException;

import static com.jswitch.common.constant.Separators.SP;

@Getter
public abstract class AuthenticationHeader extends ParametersHeader {

    public static final String DOMAIN = ParameterNames.DOMAIN;

    public static final String REALM = ParameterNames.REALM;

    public static final String OPAQUE = ParameterNames.OPAQUE;

    public static final String ALGORITHM = ParameterNames.ALGORITHM;

    public static final String QOP = ParameterNames.QOP;

    public static final String STALE = ParameterNames.STALE;

    public static final String SIGNATURE = ParameterNames.SIGNATURE;

    public static final String RESPONSE = ParameterNames.RESPONSE;

    public static final String SIGNED_BY = ParameterNames.SIGNED_BY;

    public static final String NC = ParameterNames.NC;

    public static final String USERNAME = ParameterNames.USERNAME;

    public static final String CNONCE = ParameterNames.CNONCE;

    public static final String NONCE = ParameterNames.NONCE;

    public static final String IK = ParameterNamesIms.IK;
    public static final String CK = ParameterNamesIms.CK;
    public static final String INTEGRITY_PROTECTED = ParameterNamesIms.INTEGRITY_PROTECTED;

    protected String scheme;

    public AuthenticationHeader(String name) {
        super(name);
        parameters.setSeparator(Separators.COMMA);
        this.scheme = ParameterNames.DIGEST;
    }

    public AuthenticationHeader() {
        super();
        parameters.setSeparator(Separators.COMMA);
    }

    public void setParameter(String name, String value) throws ParseException {
        NameValue nv = super.parameters.getNameValue(name.toLowerCase());
        if (nv == null) {
            nv = new NameValue(name, value);
            if (name.equalsIgnoreCase(ParameterNames.QOP)
                    || name.equalsIgnoreCase(ParameterNames.REALM)
                    || name.equalsIgnoreCase(ParameterNames.CNONCE)
                    || name.equalsIgnoreCase(ParameterNames.NONCE)
                    || name.equalsIgnoreCase(ParameterNames.USERNAME)
                    || name.equalsIgnoreCase(ParameterNames.DOMAIN)
                    || name.equalsIgnoreCase(ParameterNames.OPAQUE)
                    || name.equalsIgnoreCase(ParameterNames.NEXT_NONCE)
                    || name.equalsIgnoreCase(ParameterNames.URI)
                    || name.equalsIgnoreCase(ParameterNames.RESPONSE)
                    || name.equalsIgnoreCase(ParameterNamesIms.IK)
                    || name.equalsIgnoreCase(ParameterNamesIms.CK)
                    || name.equalsIgnoreCase(ParameterNamesIms.INTEGRITY_PROTECTED)) {
                if (((this instanceof Authorization) || (this instanceof ProxyAuthorization))
                        && name.equalsIgnoreCase(ParameterNames.QOP)) {
                    // NOP, QOP not quoted in authorization headers
                } else {
                    nv.setQuotedValue();
                }
                if (value == null)
                    throw new NullPointerException("null value");
                if (value.startsWith(Separators.DOUBLE_QUOTE))
                    throw new ParseException(value
                            + " : Unexpected DOUBLE_QUOTE", 0);
            }
            super.setParameter(nv);
        } else
            nv.setValueAsObject(value);

    }


    public void setChallenge(Challenge challenge) {
        this.scheme = challenge.scheme;
        super.parameters = challenge.authParams;
    }


    public StringBuilder encodeBody(StringBuilder buffer) {
        this.parameters.setSeparator(Separators.COMMA);
        buffer = buffer.append(this.scheme).append(SP);
        return parameters.encode(buffer);
    }


    public void setScheme(String scheme) {
        this.scheme = scheme;
    }


    public void setRealm(String realm) throws ParseException {
        if (realm == null)
            throw new NullPointerException("SIP Exception, AuthenticationHeader, setRealm(), The realm parameter is null");
        setParameter(ParameterNames.REALM, realm);
    }


    public String getRealm() {
        return getParameter(ParameterNames.REALM);
    }


    public void setNonce(String nonce) throws ParseException {
        if (nonce == null)
            throw new NullPointerException("SIP Exception, AuthenticationHeader, setNonce(), The nonce parameter is null");
        setParameter(NONCE, nonce);
    }


    public String getNonce() {
        return getParameter(ParameterNames.NONCE);
    }


    public void setURI(URI uri) {
        if (uri != null) {
            NameValue nv = new NameValue(ParameterNames.URI, uri);
            nv.setQuotedValue();
            super.parameters.set(nv);
        } else {
            throw new NullPointerException("Null URI");
        }
    }


    public URI getURI() {
        return getParameterAsURI(ParameterNames.URI);
    }


    public void setAlgorithm(String algorithm) throws ParseException {
        if (algorithm == null)
            throw new NullPointerException("null arg");
        setParameter(ParameterNames.ALGORITHM, algorithm);
    }


    public String getAlgorithm() {
        return getParameter(ParameterNames.ALGORITHM);
    }


    public void setQop(String qop) throws ParseException {
        if (qop == null)
            throw new NullPointerException("null arg");
        setParameter(ParameterNames.QOP, qop);
    }


    public String getQop() {
        return getParameter(ParameterNames.QOP);
    }


    public void setOpaque(String opaque) throws ParseException {
        if (opaque == null)
            throw new NullPointerException("null arg");
        setParameter(ParameterNames.OPAQUE, opaque);
    }


    public String getOpaque() {
        return getParameter(ParameterNames.OPAQUE);
    }


    public void setDomain(String domain) throws ParseException {
        if (domain == null)
            throw new NullPointerException("null arg");
        setParameter(ParameterNames.DOMAIN, domain);
    }


    public String getDomain() {
        return getParameter(ParameterNames.DOMAIN);
    }


    public void setStale(boolean stale) {
        setParameter(new NameValue(ParameterNames.STALE, Boolean.valueOf(stale)));
    }


    public boolean isStale() {
        return this.getParameterAsBoolean(ParameterNames.STALE);
    }


    public void setCNonce(String cnonce) throws ParseException {
        this.setParameter(ParameterNames.CNONCE, cnonce);
    }


    public String getCNonce() {
        return getParameter(ParameterNames.CNONCE);
    }

    public int getNonceCount() {
        return this.getParameterAsHexInt(ParameterNames.NC);

    }

    public void setNonceCount(int param) throws ParseException {
        if (param < 0)
            throw new ParseException("bad value", 0);

        String nc = Integer.toHexString(param);

        String base = "00000000";
        nc = base.substring(0, 8 - nc.length()) + nc;
        this.setParameter(ParameterNames.NC, nc);

    }


    public String getResponse() {
        return (String) getParameterValue(ParameterNames.RESPONSE);
    }


    public void setResponse(String response) throws ParseException {
        if (response == null)
            throw new NullPointerException("Null parameter");
        this.setParameter(RESPONSE, response);
    }


    public String getUsername() {
        return (String) getParameter(ParameterNames.USERNAME);
    }

    public void setUsername(String username) throws ParseException {
        this.setParameter(ParameterNames.USERNAME, username);
    }

    public void setIK(String ik) throws ParseException {
        if (ik == null)
            throw new NullPointerException("SIP Exception, AuthenticationHeader, setIk(), The auth-param IK parameter is null");
        setParameter(IK, ik);
    }

    public String getIK() {
        return getParameter(ParameterNamesIms.IK);
    }

    public void setCK(String ck) throws ParseException {
        if (ck == null)
            throw new NullPointerException(
                    "SIP Exception, AuthenticationHeader, setCk(), The auth-param CK parameter is null");
        setParameter(CK, ck);
    }

    public String getCK() {
        return getParameter(ParameterNamesIms.CK);
    }


    public String getIntegrityProtected() {
        return getParameter(ParameterNamesIms.INTEGRITY_PROTECTED);
    }

}
