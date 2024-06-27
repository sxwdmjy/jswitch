package com.jswitch.sip.header;

import com.jswitch.sip.core.Token;

public interface PVisitedNetworkIDHeader extends Parameters, Header {


    public final static String NAME = "P-Visited-Network-ID";


    public void setVisitedNetworkID(String networkID);


    public void setVisitedNetworkID(Token networkID);


    public String getVisitedNetworkID();

}
