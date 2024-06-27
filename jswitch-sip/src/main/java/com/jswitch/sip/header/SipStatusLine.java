package com.jswitch.sip.header;


public interface SipStatusLine {


    public abstract String getSipVersion();


    public abstract int getStatusCode();


    public abstract String getReasonPhrase();


    public abstract void setSipVersion(String sipVersion);


    public abstract void setStatusCode(int statusCode);


    public abstract void setReasonPhrase(String reasonPhrase);


    public abstract String getVersionMajor();


    public abstract String getVersionMinor();

}
