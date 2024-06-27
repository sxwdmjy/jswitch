package com.jswitch.sip;

/**
 * @author danmo
 * @date 2024-06-18 14:42
 **/
public interface Hop {

    public String getHost();


    public int getPort();


    public String getTransport();


    public String toString();
}
