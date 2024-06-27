package com.jswitch.sip.header;

import java.io.Serializable;

/**
 * @author danmo
 * @date 2024-06-13 18:51
 **/
public interface Header extends Cloneable, Serializable {

    public String getName();

    public boolean equals(Object obj);

    public int hashCode();

    public String toString();
}
