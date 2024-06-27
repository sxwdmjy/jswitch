package com.jswitch.sip;

/**
 * @author danmo
 * @date 2024-06-18 10:32
 **/
public interface SipRequestLine {

    public abstract URI getUri();

    public abstract String getMethod();


    public abstract String getSipVersion();


    public abstract void setUri(URI uri);


    public abstract void setMethod(String method);


    public abstract void setSipVersion(String version);


    public abstract String getVersionMajor();


    public abstract String getVersionMinor();
}
