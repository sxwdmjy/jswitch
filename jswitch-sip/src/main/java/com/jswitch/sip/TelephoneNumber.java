package com.jswitch.sip;

import lombok.Data;

import java.util.Iterator;

import static com.jswitch.common.constant.Separators.SEMICOLON;

/**
 * @author danmo
 * @date 2024-06-18 11:17
 **/
@Data
public class TelephoneNumber extends NetObject {

    public static final String POSTDIAL = ParameterNames.POSTDIAL;
    public static final String PHONE_CONTEXT_TAG = ParameterNames.PHONE_CONTEXT_TAG;
    public static final String ISUB = ParameterNames.ISUB;
    public static final String PROVIDER_TAG = ParameterNames.PROVIDER_TAG;

    protected boolean global;

    protected String phoneNumber;


    protected NameValueList parameters;


    public TelephoneNumber() {
        parameters = new NameValueList();
    }


    public void deleteParm(String name) {
        parameters.delete(name);
    }


    public String getPostDial() {
        return (String) parameters.getValue(POSTDIAL);
    }


    public String getIsdnSubaddress() {
        return (String) parameters.getValue(ISUB);
    }


    public boolean hasPostDial() {
        return parameters.getValue(POSTDIAL) != null;
    }


    public boolean hasParm(String pname) {
        return parameters.hasNameValue(pname);
    }


    public boolean hasIsdnSubaddress() {
        return hasParm(ISUB);
    }



    public void removePostDial() {
        parameters.delete(POSTDIAL);
    }

    public void removeIsdnSubaddress() {
        deleteParm(ISUB);
    }



    public void setPostDial(String p) {
        NameValue nv = new NameValue(POSTDIAL, p);
        parameters.set(nv);
    }

    public void setParm(String name, Object value) {
        NameValue nv = new NameValue(name, value);
        parameters.set(nv);
    }


    public void setIsdnSubaddress(String isub) {
        setParm(ISUB, isub);
    }


    public void setPhoneNumber(String num) {
        phoneNumber = num;
    }

    public String encode() {
        return encode(new StringBuilder()).toString();
    }

    public StringBuilder encode(StringBuilder buffer) {
        if (global)
            buffer.append('+');
        buffer.append(phoneNumber);
        if (!parameters.isEmpty()) {
            buffer.append(SEMICOLON);
            parameters.encode(buffer);
        }
        return buffer;
    }


    public String getParameter(String name) {
        Object val = parameters.getValue(name);
        if (val == null)
            return null;
        if (val instanceof GenericObject)
            return ((GenericObject) val).encode();
        else
            return val.toString();
    }


    public Iterator<String> getParameterNames() {
        return this.parameters.getNames();
    }

    public void removeParameter(String parameter) {
        this.parameters.delete(parameter);
    }

    public void setParameter(String name, String value) {
        NameValue nv = new NameValue(name, value);
        this.parameters.set(nv);
    }

    public Object clone() {
        TelephoneNumber retval = (TelephoneNumber) super.clone();
        if (this.parameters != null)
            retval.parameters = (NameValueList) this.parameters.clone();
        return retval;
    }

}
