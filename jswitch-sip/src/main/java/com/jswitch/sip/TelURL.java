package com.jswitch.sip;

import com.jswitch.sip.header.Parameters;

import java.text.ParseException;

public interface TelURL extends URI, Parameters {


    public boolean isGlobal();


    public void setGlobal(boolean global);


    public void setPostDial(String postDial) throws ParseException;


    public String getPostDial();


    public void setPhoneNumber(String phoneNumber) throws ParseException;


    public String getPhoneNumber();


    public void setIsdnSubAddress(String isdnSubAddress) throws ParseException;


    public String getIsdnSubAddress();


    public void setPhoneContext(String phoneContext) throws ParseException;


    public String getPhoneContext();


    public String toString();
}
