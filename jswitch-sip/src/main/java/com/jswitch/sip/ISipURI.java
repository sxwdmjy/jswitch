package com.jswitch.sip;

import com.jswitch.common.exception.InvalidArgumentException;
import com.jswitch.sip.header.Parameters;

import java.text.ParseException;
import java.util.Iterator;

/**
 * @author danmo
 * @date 2024-06-18 11:22
 **/
public interface ISipURI extends URI, Parameters {

    public void setUser(String user) throws ParseException;

    public String getUser();

    public void setUserPassword(String userPassword) throws ParseException;

    public String getUserPassword();

    public boolean isSecure();

    public void setSecure(boolean secure);

    public void setHost(String host) throws ParseException;

    public String getHost();

    public void setPort(int port);

    public int getPort();

    public void removePort();


    public String getHeader(String name);

    public void setHeader(String name, String value) throws ParseException;

    public Iterator getHeaderNames();


    public String getTransportParam();

    public void setTransportParam(String transport) throws ParseException;

    public int getTTLParam();

    public void setTTLParam(int ttl) throws InvalidArgumentException;

    public String getMethodParam();

    public void setMethodParam(String method) throws ParseException;

    public void setUserParam(String userParam) throws ParseException;

    public String getUserParam();


    public String getMAddrParam();

    public void setMAddrParam(String mAddr) throws ParseException;

    public boolean hasLrParam();

    public void setLrParam();


    public String toString();
}
