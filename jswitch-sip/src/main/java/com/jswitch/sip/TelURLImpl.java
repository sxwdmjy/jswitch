package com.jswitch.sip;

import java.text.ParseException;
import java.util.Iterator;


public class TelURLImpl extends GenericURI implements TelURL {


    private static final long serialVersionUID = 5873527320305915954L;

    protected TelephoneNumber telephoneNumber;

    public TelURLImpl() {
        this.scheme = "tel";
    }

    public void setTelephoneNumber(TelephoneNumber telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }


    public String getIsdnSubAddress() {
        return telephoneNumber.getIsdnSubaddress();
    }


    public String getPostDial() {
        return telephoneNumber.getPostDial();
    }


    public String getScheme() {
        return this.scheme;
    }


    public boolean isGlobal() {
        return telephoneNumber.isGlobal();
    }


    public boolean isSipURI() {
        return false;
    }

    public void setGlobal(boolean global) {
        this.telephoneNumber.setGlobal(global);
    }

    public void setIsdnSubAddress(String isdnSubAddress) {
        this.telephoneNumber.setIsdnSubaddress(isdnSubAddress);
    }

    public void setPostDial(String postDial) {
        this.telephoneNumber.setPostDial(postDial);
    }

    public void setPhoneNumber(String telephoneNumber) {
        this.telephoneNumber.setPhoneNumber(telephoneNumber);
    }

    public String getPhoneNumber() {
        return this.telephoneNumber.getPhoneNumber();
    }

    public String toString() {
        return this.scheme + ":" + telephoneNumber.encode();
    }

    public String encode() {
        return encode(new StringBuilder()).toString();
    }

    public StringBuilder encode(StringBuilder buffer) {
        buffer.append(this.scheme).append(':');
        telephoneNumber.encode(buffer);
        return buffer;
    }

    public Object clone() {
        TelURLImpl retval = (TelURLImpl) super.clone();
        if (this.telephoneNumber != null)
            retval.telephoneNumber = (TelephoneNumber) this.telephoneNumber.clone();
        return retval;
    }

    public String getParameter(String parameterName) {
        return telephoneNumber.getParameter(parameterName);
    }

    public void setParameter(String name, String value) {
        telephoneNumber.setParameter(name, value);
    }

    public Iterator<String> getParameterNames() {
        return telephoneNumber.getParameterNames();
    }

    public NameValueList getParameters() {
        return telephoneNumber.getParameters();
    }

    public void removeParameter(String name) {
        telephoneNumber.removeParameter(name);
    }

    public void setPhoneContext(String phoneContext) throws ParseException {
        if (phoneContext == null) {
            this.removeParameter("phone-context");
        } else {
            this.setParameter("phone-context", phoneContext);
        }
    }


    public String getPhoneContext() {

        return this.getParameter("phone-context");
    }
}
