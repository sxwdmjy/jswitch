package com.jswitch.sip.adress;

import com.jswitch.sip.URI;

import java.io.Serializable;
import java.text.ParseException;

/**
 * @author danmo
 * @date 2024-06-18 14:53
 **/
public interface Address extends Cloneable, Serializable {
    public void setDisplayName(String displayName) throws ParseException;

    public String getDisplayName();

    public void setURI(URI uri);


    public URI getURI();


    public String toString();


    public boolean equals(Object obj);


    public int hashCode();


    public boolean isWildcard();


    public Object clone();
}
