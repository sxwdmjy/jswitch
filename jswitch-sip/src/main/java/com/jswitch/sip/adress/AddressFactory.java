package com.jswitch.sip.adress;

import com.jswitch.sip.ISipURI;
import com.jswitch.sip.TelURL;
import com.jswitch.sip.URI;

import java.text.ParseException;


public interface AddressFactory {


    public URI createURI(String uri) throws ParseException;


    public ISipURI createSipURI(String user, String host)
            throws ParseException;


    public TelURL createTelURL(String phoneNumber) throws ParseException;


    public Address createAddress(String address) throws ParseException;


    public Address createAddress(URI uri);


    public Address createAddress(String displayName, URI uri) throws ParseException;

    public ISipURI createSipURI(String sipUri) throws ParseException;


}

