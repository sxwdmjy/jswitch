package com.jswitch.sip.header;


import java.text.ParseException;

public interface PChargingVectorHeader extends Header, Parameters {


    public final static String NAME = "P-Charging-Vector";



    public String getICID();



    public void setICID(String icid) throws ParseException;


    public String getICIDGeneratedAt();



    public void setICIDGeneratedAt(String host) throws ParseException;



    public String getOriginatingIOI();



    public void setOriginatingIOI(String origIOI) throws ParseException;



    public String getTerminatingIOI();



    public void setTerminatingIOI(String termIOI) throws ParseException;






}
