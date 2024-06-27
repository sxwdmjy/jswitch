package com.jswitch.sip.header;

import com.jswitch.sip.DuplicateNameValueList;
import com.jswitch.sip.GenericURI;
import com.jswitch.sip.NameValue;
import com.jswitch.sip.NameValueList;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Iterator;

/**
 * @author danmo
 * @date 2024-06-18 13:55
 **/
public abstract class ParametersHeader extends SIPHeader implements Parameters, Serializable {

    protected NameValueList parameters;

    protected DuplicateNameValueList duplicates;

    protected ParametersHeader() {
        this.parameters = new NameValueList();
        this.duplicates = new DuplicateNameValueList();
    }

    protected ParametersHeader(String hdrName) {
        super(hdrName);
        this.parameters = new NameValueList();
        this.duplicates = new DuplicateNameValueList();
    }

    protected ParametersHeader(String hdrName, boolean sync) {
        super(hdrName);
        this.parameters = new NameValueList(sync);
        this.duplicates = new DuplicateNameValueList();
    }


    public String getParameter(String name) {
        return this.parameters.getParameter(name);

    }


    public String getParameter(String name, boolean stripQuotes) {
        return this.parameters.getParameter(name, stripQuotes);

    }


    public Object getParameterValue(String name) {
        return this.parameters.getValue(name);
    }


    public Iterator<String> getParameterNames() {
        return parameters.getNames();
    }


    public boolean hasParameters() {
        return parameters != null && !parameters.isEmpty();
    }


    public void removeParameter(String name) {
        this.parameters.delete(name);
    }


    public void setParameter(String name, String value) throws ParseException {
        NameValue nv = parameters.getNameValue(name);
        if (nv != null) {
            nv.setValueAsObject(value);
        } else {
            nv = new NameValue(name, value);
            this.parameters.set(nv);
        }
    }


    public void setQuotedParameter(String name, String value)
            throws ParseException {
        NameValue nv = parameters.getNameValue(name);
        if (nv != null) {
            nv.setValueAsObject(value);
            nv.setQuotedValue();
        } else {
            nv = new NameValue(name, value);
            nv.setQuotedValue();
            this.parameters.set(nv);
        }
    }


    protected void setParameter(String name, int value) {
        Integer val = Integer.valueOf(value);
        this.parameters.set(name, val);

    }


    protected void setParameter(String name, boolean value) {
        Boolean val = Boolean.valueOf(value);
        this.parameters.set(name, val);
    }


    protected void setParameter(String name, float value) {
        Float val = Float.valueOf(value);
        NameValue nv = parameters.getNameValue(name);
        if (nv != null) {
            nv.setValueAsObject(val);
        } else {
            nv = new NameValue(name, val);
            this.parameters.set(nv);
        }
    }


    protected void setParameter(String name, Object value) {
        this.parameters.set(name, value);
    }


    public boolean hasParameter(String parameterName) {
        return this.parameters.hasNameValue(parameterName);
    }


    public void removeParameters() {
        this.parameters = new NameValueList();
    }


    public NameValueList getParameters() {
        return parameters;
    }


    public void setParameter(NameValue nameValue) {
        this.parameters.set(nameValue);
    }


    public void setParameters(NameValueList parameters) {
        this.parameters = parameters;
    }

    protected int getParameterAsInt(String parameterName) {
        if (this.getParameterValue(parameterName) != null) {
            try {
                if (this.getParameterValue(parameterName) instanceof String) {
                    return Integer.parseInt(this.getParameter(parameterName));
                } else {
                    return ((Integer) getParameterValue(parameterName))
                            .intValue();
                }
            } catch (NumberFormatException ex) {
                return -1;
            }
        } else
            return -1;
    }


    protected int getParameterAsHexInt(String parameterName) {
        if (this.getParameterValue(parameterName) != null) {
            try {
                if (this.getParameterValue(parameterName) instanceof String) {
                    return Integer.parseInt(
                            this.getParameter(parameterName),
                            16);
                } else {
                    return ((Integer) getParameterValue(parameterName))
                            .intValue();
                }
            } catch (NumberFormatException ex) {
                return -1;
            }
        } else
            return -1;
    }


    protected float getParameterAsFloat(String parameterName) {

        if (this.getParameterValue(parameterName) != null) {
            try {
                if (this.getParameterValue(parameterName) instanceof String) {
                    return Float.parseFloat(this.getParameter(parameterName));
                } else {
                    return ((Float) getParameterValue(parameterName))
                            .floatValue();
                }
            } catch (NumberFormatException ex) {
                return -1;
            }
        } else
            return -1;
    }


    protected long getParameterAsLong(String parameterName) {
        if (this.getParameterValue(parameterName) != null) {
            try {
                if (this.getParameterValue(parameterName) instanceof String) {
                    return Long.parseLong(this.getParameter(parameterName));
                } else {
                    return ((Long) getParameterValue(parameterName))
                            .longValue();
                }
            } catch (NumberFormatException ex) {
                return -1;
            }
        } else
            return -1;
    }


    protected GenericURI getParameterAsURI(String parameterName) {
        Object val = getParameterValue(parameterName);
        if (val instanceof GenericURI)
            return (GenericURI) val;
        else {
            try {
                return new GenericURI((String) val);
            } catch (ParseException ex) {
                return null;
            }
        }
    }


    protected boolean getParameterAsBoolean(String parameterName) {
        Object val = getParameterValue(parameterName);
        if (val == null) {
            return false;
        } else if (val instanceof Boolean) {
            return ((Boolean) val).booleanValue();
        } else if (val instanceof String) {
            return Boolean.valueOf((String) val).booleanValue();
        } else
            return false;
    }

    public NameValue getNameValue(String parameterName) {
        return parameters.getNameValue(parameterName);
    }


    public Object clone() {
        ParametersHeader retval = (ParametersHeader) super.clone();
        if (this.parameters != null)
            retval.parameters = (NameValueList) this.parameters.clone();
        return retval;
    }


    public void setMultiParameter(String name, String value) {
        NameValue nv = new NameValue();
        nv.setName(name);
        nv.setValue(value);
        duplicates.set(nv);
    }

    public void setMultiParameter(NameValue nameValue) {
        this.duplicates.set(nameValue);
    }


    public String getMultiParameter(String name) {
        return this.duplicates.getParameter(name);

    }


    public DuplicateNameValueList getMultiParameters() {
        return duplicates;
    }


    public Object getMultiParameterValue(String name) {
        return this.duplicates.getValue(name);
    }


    public Iterator<String> getMultiParameterNames() {
        return duplicates.getNames();
    }

    public boolean hasMultiParameters() {
        return duplicates != null && !duplicates.isEmpty();
    }


    public void removeMultiParameter(String name) {
        this.duplicates.delete(name);
    }


    public boolean hasMultiParameter(String parameterName) {
        return this.duplicates.hasNameValue(parameterName);
    }

    public void removeMultiParameters() {
        this.duplicates = new DuplicateNameValueList();
    }


    @SuppressWarnings("unchecked")
    protected final boolean equalParameters(Parameters other) {
        if (this == other) return true;

        for (Iterator i = this.getParameterNames(); i.hasNext(); ) {
            String pname = (String) i.next();

            String p1 = this.getParameter(pname);
            String p2 = other.getParameter(pname);

            if (p1 == null ^ p2 == null) return false;
            else if (p1 != null && !p1.equalsIgnoreCase(p2)) return false;
        }

        for (Iterator i = other.getParameterNames(); i.hasNext(); ) {
            String pname = (String) i.next();

            String p1 = other.getParameter(pname);
            String p2 = this.getParameter(pname);


            if (p1 == null ^ p2 == null) return false;
            else if (p1 != null && !p1.equalsIgnoreCase(p2)) return false;
        }

        return true;
    }


    public abstract StringBuilder encodeBody(StringBuilder buffer);
}
