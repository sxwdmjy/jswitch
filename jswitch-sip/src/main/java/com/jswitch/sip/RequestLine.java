package com.jswitch.sip;

import static com.jswitch.common.constant.Separators.*;

/**
 * @author danmo
 * @date 2024-06-18 10:33
 **/
public class RequestLine extends SIPObject  implements SipRequestLine{


    private static final long serialVersionUID = -3286426172326043129L;


    protected GenericURI uri;


    protected String method;


    protected String sipVersion;


    public RequestLine() {
        sipVersion = "SIP/2.0";
    }



    public String encode() {
        return encode(new StringBuilder()).toString();
    }

    public StringBuilder encode(StringBuilder buffer) {
        if (method != null) {
            buffer.append(method);
            buffer.append(SP);
        }
        if (uri != null) {
            uri.encode(buffer);
            buffer.append(SP);
        }
        buffer.append(sipVersion);
        buffer.append(NEWLINE);
        return buffer;
    }


    public GenericURI getUri() {
        return uri;
    }


    public RequestLine(GenericURI requestURI, String method) {
        this.uri = requestURI;
        this.method = method;
        this.sipVersion = "SIP/2.0";
    }


    public String getMethod() {
        return method;
    }


    public String getSipVersion() {
        return sipVersion;
    }


    public void setUri(URI uri) {
        this.uri = (GenericURI)uri;
    }


    public void setMethod(String method) {
        this.method = method;
    }


    public void setSipVersion(String version) {
        this.sipVersion = version;
    }


    public String getVersionMajor() {
        if (sipVersion == null)
            return null;
        String major = null;
        boolean slash = false;
        for (int i = 0; i < sipVersion.length(); i++) {
            if (sipVersion.charAt(i) == '.')
                break;
            if (slash) {
                if (major == null)
                    major = "" + sipVersion.charAt(i);
                else
                    major += sipVersion.charAt(i);
            }
            if (sipVersion.charAt(i) == '/')
                slash = true;
        }
        return major;
    }


    public String getVersionMinor() {
        if (sipVersion == null)
            return null;
        String minor = null;
        boolean dot = false;
        for (int i = 0; i < sipVersion.length(); i++) {
            if (dot) {
                if (minor == null)
                    minor = "" + sipVersion.charAt(i);
                else
                    minor += sipVersion.charAt(i);
            }
            if (sipVersion.charAt(i) == '.')
                dot = true;
        }
        return minor;
    }


    public boolean equals(Object other) {
        if(other == null)
            return false;
        if (this == other)
            return true;
        if (!other.getClass().equals(this.getClass())) {
            return false;
        }
        RequestLine that = (RequestLine) other;
        if (this.method == null) {
            if (that.method != null)
                return false;
        } else if (!this.method.equals(that.method))
            return false;
        if (this.sipVersion == null) {
            if (that.sipVersion != null)
                return false;
        } else if (!this.sipVersion.equals(that.sipVersion))
            return false;
        if (this.uri == null) {
            if (that.uri != null)
                return false;
        } else if (!this.uri.equals(that.uri))
            return false;
        return true;
    }

    public Object clone() {
        RequestLine retval = (RequestLine) super.clone();
        if (this.uri != null)
            retval.uri = (GenericURI) this.uri.clone();
        return retval;
    }
}
