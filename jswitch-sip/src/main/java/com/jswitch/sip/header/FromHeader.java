package com.jswitch.sip.header;

import java.text.ParseException;

/**
 * @author danmo
 * @date 2024-06-18 14:52
 **/
public interface FromHeader extends HeaderAddress, Parameters, Header {

    public void setTag(String tag) throws ParseException;

    public String getTag();

    public boolean equals(Object obj);

    public final static String NAME = "From";
}
