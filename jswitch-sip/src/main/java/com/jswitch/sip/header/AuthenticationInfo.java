
package com.jswitch.sip.header;


import com.jswitch.common.constant.Separators;
import com.jswitch.sip.NameValue;
import com.jswitch.sip.ParameterNames;

import java.text.ParseException;

public final class AuthenticationInfo extends ParametersHeader implements AuthenticationInfoHeader {


    private static final long serialVersionUID = -4371927900917127057L;


    private String scheme;


    public AuthenticationInfo() {
        super(NAME);
        parameters.setSeparator(Separators.COMMA);
    }

    public void add(NameValue nv) {
        parameters.set(nv);
    }


    public StringBuilder encodeBody(StringBuilder buffer) {
        if (scheme != null)
            buffer.append(scheme).append(' ');
        return parameters.encode(buffer);

    }


    public NameValue getAuthInfo(String name) {
        return parameters.getNameValue(name);
    }

    public String getAuthenticationInfo() {
        return this.encodeBody(new StringBuilder()).toString();
    }

    public String getCNonce() {
        return this.getParameter(ParameterNames.CNONCE);
    }


    public String getNextNonce() {
        return this.getParameter(ParameterNames.NEXT_NONCE);
    }


    public int getNonceCount() {
        return this.getParameterAsInt(ParameterNames.NONCE_COUNT);
    }


    public String getQop() {
        return this.getParameter(ParameterNames.QOP);
    }


    public String getResponse() {
        return this.getParameter(ParameterNames.RESPONSE_AUTH);
    }


    public String getSNum() {
        return this.getParameter(ParameterNames.SNUM);
    }


    public String getSRand() {
        return this.getParameter(ParameterNames.SRAND);
    }


    public String getTargetName() {
        return this.getParameter(ParameterNames.TARGET_NAME);
    }


    public String getScheme() {
        return scheme;
    }


    public void setCNonce(String cNonce) throws ParseException {
        this.setParameter(ParameterNames.CNONCE, cNonce);
    }

    public void setNextNonce(String nextNonce) throws ParseException {
        this.setParameter(ParameterNames.NEXT_NONCE, nextNonce);
    }

    public void setNonceCount(int nonceCount) throws ParseException {
        if (nonceCount < 0)
            throw new ParseException("bad value", 0);
        String nc = Integer.toHexString(nonceCount);

        String base = "00000000";
        nc = base.substring(0, 8 - nc.length()) + nc;
        this.setParameter(ParameterNames.NC, nc);
    }

    public void setQop(String qop) throws ParseException {
        this.setParameter(ParameterNames.QOP, qop);
    }

    public void setResponse(String response) throws ParseException {
        this.setParameter(ParameterNames.RESPONSE_AUTH, response);
    }

    public void setSNum(String sNum) throws ParseException {
        this.setParameter(ParameterNames.SNUM, sNum);
    }

    public void setSRand(String sRand) throws ParseException {
        this.setParameter(ParameterNames.SRAND, sRand);
    }

    public void setTargetName(String targetName) throws ParseException {
        this.setParameter(ParameterNames.TARGET_NAME, targetName);
    }

    public void setScheme(String scheme) throws ParseException {
        this.scheme = scheme;
    }

    public void setParameter(String name, String value) throws ParseException {
        if (name == null)
            throw new NullPointerException("null name");
        NameValue nv = super.parameters.getNameValue(name.toLowerCase());
        if (nv == null) {
            nv = new NameValue(name, value);
            if (name.equalsIgnoreCase(ParameterNames.QOP)
                    || name.equalsIgnoreCase(ParameterNames.NEXT_NONCE)
                    || name.equalsIgnoreCase(ParameterNames.REALM)
                    || name.equalsIgnoreCase(ParameterNames.CNONCE)
                    || name.equalsIgnoreCase(ParameterNames.NONCE)
                    || name.equalsIgnoreCase(ParameterNames.OPAQUE)
                    || name.equalsIgnoreCase(ParameterNames.USERNAME)
                    || name.equalsIgnoreCase(ParameterNames.DOMAIN)
                    || name.equalsIgnoreCase(ParameterNames.NEXT_NONCE)
                    || name.equalsIgnoreCase(ParameterNames.RESPONSE_AUTH)
                    || name.equalsIgnoreCase(ParameterNames.SRAND)
                    || name.equalsIgnoreCase(ParameterNames.SNUM)
                    || name.equalsIgnoreCase(ParameterNames.TARGET_NAME)) {
                if (value == null)
                    throw new NullPointerException("null value");
                if (value.startsWith(Separators.DOUBLE_QUOTE))
                    throw new ParseException(
                            value + " : Unexpected DOUBLE_QUOTE",
                            0);
                nv.setQuotedValue();
            }
            super.setParameter(nv);
        } else
            nv.setValueAsObject(value);
    }
}
