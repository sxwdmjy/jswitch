package com.jswitch.sip.header;

import com.jswitch.common.exception.InvalidArgumentException;
import com.jswitch.sip.header.Header;
import com.jswitch.sip.header.Parameters;

import java.text.ParseException;

public interface ViaHeader extends Parameters, Header {

    public void setHost(String host) throws ParseException;

    public String getHost();

    public void setPort(int port) throws InvalidArgumentException;

    public int getPort();


    public String getTransport();


    public void setTransport(String transport) throws ParseException;


    public String getProtocol();


    public void setProtocol(String protocol) throws ParseException;


    public int getTTL();


    public void setTTL(int ttl) throws InvalidArgumentException;


    public String getMAddr();


    public void setMAddr(String mAddr) throws ParseException;


    public String getReceived();

    public void setReceived(String received) throws ParseException;


    public String getBranch();


    public void setBranch(String branch) throws ParseException;


    public void setRPort(String rport) throws InvalidArgumentException;


    public int getRPort();


    public String getSentByField();

    public String getSentProtocolField();


    public boolean equals(Object obj);

    /**
     * Name of ViaHeader
     */
    public final static String NAME = "Via";

}
