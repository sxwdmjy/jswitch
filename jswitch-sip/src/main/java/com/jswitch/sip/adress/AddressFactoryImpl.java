package com.jswitch.sip.adress;

import com.jswitch.sip.*;
import com.jswitch.sip.parse.StringMsgParser;
import com.jswitch.sip.parse.URLParser;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.regex.Pattern;

/**
 * @author danmo
 * @date 2024-06-25 17:27
 **/
@Service
public class AddressFactoryImpl implements AddressFactory {

    public static final Pattern SCHEME_PATTERN = Pattern.compile("\\p{Alpha}[{\\p{Alpha}\\d+-.]*");

    public Address createAddress() {
        return new AddressImpl();
    }

    public Address createAddress(
            String displayName,
            URI uri) {
        if (uri == null)
            throw new NullPointerException("null  URI");
        AddressImpl addressImpl = new AddressImpl();
        if (displayName != null)
            addressImpl.setDisplayName(displayName);
        addressImpl.setURI(uri);
        return addressImpl;

    }


    public Address createAddress(URI uri) {
        if (uri == null)
            throw new NullPointerException("null address");
        AddressImpl addressImpl = new AddressImpl();
        addressImpl.setURI(uri);
        return addressImpl;
    }


    public Address createAddress(String address)
            throws java.text.ParseException {
        if (address == null)
            throw new NullPointerException("null address");

        if (address.equals("*")) {
            AddressImpl addressImpl = new AddressImpl();
            addressImpl.setAddressType(AddressImpl.WILD_CARD);
            ISipURI uri = new SipUri();
            uri.setUser("*");
            addressImpl.setURI(uri);
            return addressImpl;
        } else {
            StringMsgParser smp = new StringMsgParser();
            return smp.parseAddress(address);
        }
    }


    public ISipURI createSipURI(String uri) throws ParseException {
        if (uri == null)
            throw new NullPointerException("null URI");
        try {
            StringMsgParser smp = new StringMsgParser();
            SipUri sipUri = smp.parseSIPUrl(uri);
            return (ISipURI) sipUri;
        } catch (ParseException ex) {
            throw new ParseException(ex.getMessage(), 0);
        }

    }

    public ISipURI createSipURI(String user, String host) throws ParseException {
        if (host == null)
            throw new NullPointerException("null host");

        StringBuilder uriString = new StringBuilder("sip:");
        if (user != null) {
            uriString.append(user);
            uriString.append("@");
        }

        //if host is an IPv6 string we should enclose it in sq brackets
        if (host.indexOf(':') != host.lastIndexOf(':')
                && host.trim().charAt(0) != '[')
            host = '[' + host + ']';

        uriString.append(host);


        try {

            return this.createSipURI(uriString.toString());
        } catch (ParseException ex) {
            throw new ParseException(ex.getMessage(), 0);
        }
    }


    public TelURL createTelURL(String uri)
            throws ParseException {
        if (uri == null)
            throw new NullPointerException("null url");
        String telUrl = null;
        if (uri.startsWith("tel:")) {
            telUrl = uri;
        } else {
            telUrl = "tel:" + uri;
        }
        try {
            StringMsgParser smp = new StringMsgParser();
            TelURLImpl timp = (TelURLImpl) smp.parseUrl(telUrl);
            return (TelURL) timp;
        } catch (ParseException ex) {
            throw new ParseException(ex.getMessage(), 0);
        }
    }


    public URI createURI(String uri) throws ParseException {
        if (uri == null)
            throw new NullPointerException("null arg");
        try {
            URLParser urlParser = new URLParser(uri);
            String scheme = urlParser.peekScheme();
            if (scheme == null)
                throw new ParseException("bad scheme", 0);
            if (scheme.equalsIgnoreCase("sip") || scheme.equalsIgnoreCase("sips")) {
                return this.createSipURI(uri);
            } else if (scheme.equalsIgnoreCase("tel")) {
                return this.createTelURL(uri);
            }
            if (!SCHEME_PATTERN.matcher(scheme).matches()) {
                throw new ParseException("the scheme " + scheme + " from the following uri " + uri + " doesn't match ALPHA *(ALPHA / DIGIT / \"+\" / \"-\" / \".\" ) from RFC3261", 0);
            }
        } catch (ParseException ex) {
            throw new ParseException(ex.getMessage(), 0);
        }
        return new GenericURI(uri);
    }
}
