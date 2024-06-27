package com.jswitch.sip.header;

import java.text.ParseException;

/**
 * @author danmo
 * @date 2024-06-18 15:21
 **/
public interface MediaType {

    public void setContentType(String contentType) throws ParseException;


    public String getContentType();


    public void setContentSubType(String contentSubType) throws ParseException;


    public String getContentSubType();
}
