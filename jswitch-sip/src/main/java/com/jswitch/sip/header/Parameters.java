package com.jswitch.sip.header;

import java.text.ParseException;
import java.util.Iterator;

/**
 * @author danmo
 * @date 2024-06-13 17:35
 **/
public interface Parameters {

    public String getParameter(String name);

    public void setParameter(String name, String value) throws ParseException;

    public Iterator<String> getParameterNames();

    public void removeParameter(String name);
}
