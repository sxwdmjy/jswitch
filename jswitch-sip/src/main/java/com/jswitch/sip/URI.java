package com.jswitch.sip;

import java.io.Serializable;

/**
 * @author danmo
 * @date 2024-06-13 17:31
 **/
public interface URI extends Cloneable, Serializable {


    public String getScheme();

    public boolean isSipURI();

    public Object clone();

    public String toString();
}
