package com.jswitch.sip.header;

public interface PServedUserHeader {

    public static final String NAME = "P-Served-User";

    public void setSessionCase(String sessionCase);

    public String getSessionCase();

    public void setRegistrationState(String registrationState);

    public String getRegistrationState();


}
