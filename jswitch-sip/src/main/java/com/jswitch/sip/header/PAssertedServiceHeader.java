package com.jswitch.sip.header;

public interface PAssertedServiceHeader extends Header{

    public static final String NAME = "P-Asserted-Service";

    public void setSubserviceIdentifiers(String subservices);

    public String getSubserviceIdentifiers();

    public void setApplicationIdentifiers(String appids);

    public String getApplicationIdentifiers();
}
