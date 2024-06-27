package com.jswitch.sip;

import lombok.Getter;

import java.text.ParseException;

/**
 * @author danmo
 * @date 2024-06-18 10:51
 **/
public class GenericURI extends NetObject implements URI {

    public static final String SIP = ParameterNames.SIP_URI_SCHEME;
    public static final String SIPS = ParameterNames.SIPS_URI_SCHEME;
    public static final String TEL = ParameterNames.TEL_URI_SCHEME;
    public static final String POSTDIAL = ParameterNames.POSTDIAL;
    public static final String PHONE_CONTEXT_TAG = ParameterNames.PHONE_CONTEXT_TAG;
    public static final String ISUB = ParameterNames.ISUB;
    public static final String PROVIDER_TAG = ParameterNames.PROVIDER_TAG;


    protected String uriString;

    @Getter
    protected String scheme;

    protected GenericURI() {
    }

    public GenericURI(String uriString) throws ParseException {
        try {
            this.uriString = uriString;
            int i = uriString.indexOf(":");
            scheme = uriString.substring(0, i);
        } catch (Exception e) {
            throw new ParseException("GenericURI, Bad URI format", 0);
        }
    }

    public String encode() {
        return uriString;
    }

    public StringBuilder encode(StringBuilder buffer) {
        return buffer.append(uriString);
    }

    public String toString() {
        return this.encode();

    }

    public boolean isSipURI() {
        return this instanceof SipUri;
    }

    // @Override
    public boolean equals(Object that) {
        if (this == that) return true;
        else if (that instanceof URI uri) {
            return this.toString().equalsIgnoreCase(uri.toString());
        }
        return false;
    }

    public int hashCode() {
        return this.toString().hashCode();
    }
}
