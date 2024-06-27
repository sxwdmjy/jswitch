package com.jswitch.sip;

import com.jswitch.common.constant.Separators;
import lombok.Data;

import java.util.Map;

/**
 * @author danmo
 * @date 2024-06-18 11:07
 **/
@Data
public class NameValue extends GenericObject implements Map.Entry<String,String> {

    protected boolean isQuotedString;

    protected final boolean isFlagParameter;

    private String separator;

    private String quotes;

    private String name;

    private Object value;

    public NameValue() {
        name = null;
        value = "";
        separator = Separators.EQUALS;
        this.quotes = "";
        this.isFlagParameter = false;
    }


    public NameValue(String n, Object v, boolean isFlag) {
        name = n;
        value = v;
        separator = Separators.EQUALS;
        quotes = "";
        this.isFlagParameter = isFlag;
    }


    public NameValue(String name, Object value) {
        this(name,value, false);
    }


    public void setSeparator(String sep) {
        separator = sep;
    }


    public void setQuotedValue() {
        isQuotedString = true;
        this.quotes = Separators.DOUBLE_QUOTE;
    }

    public void setValueAsObject(Object value) {
        this.value = value;
    }

    public boolean isValueQuoted() {
        return isQuotedString;
    }


    public Object getValueAsObject() {
        return getValueAsObject(true);
    }

    public Object getValueAsObject(boolean stripQuotes) {
        if(isFlagParameter)
            return "";

        if(!stripQuotes && isQuotedString)
            return quotes + value.toString() + quotes;

        return value;
    }


    public String encode() {
        return encode(new StringBuilder()).toString();
    }

    public StringBuilder encode(StringBuilder buffer) {
        if (name != null && value != null && !isFlagParameter) {
            if (GenericObject.isMySubclass(value.getClass())) {
                GenericObject gv = (GenericObject) value;
                buffer.append(name).append(separator).append(quotes);
                gv.encode(buffer);
                buffer.append(quotes);
                return buffer;
            } else if (GenericObjectList.isMySubclass(value.getClass())) {
                GenericObjectList gvlist = (GenericObjectList) value;
                buffer.append(name).append(separator).append(gvlist.encode());
                return buffer;
            } else if (value.toString().isEmpty()) {
                if ( this.isQuotedString ) {
                    buffer.append(name).append(separator).append(quotes).append(quotes);
                    return buffer;
                } else {
                    buffer.append(name).append(separator);
                    return buffer;
                }
            } else {
                buffer.append(name).append(separator).append(quotes).append(value.toString()).append(quotes);
                return buffer;
            }
        } else if (name == null && value != null) {
            if (GenericObject.isMySubclass(value.getClass())) {
                GenericObject gv = (GenericObject) value;
                gv.encode(buffer);
                return buffer;
            } else if (GenericObjectList.isMySubclass(value.getClass())) {
                GenericObjectList gvlist = (GenericObjectList) value;
                buffer.append(gvlist.encode());
                return buffer;
            } else {
                buffer.append(quotes).append(value.toString()).append(quotes);
                return buffer;
            }
        } else if (name != null && (value == null || isFlagParameter)) {
            buffer.append(name);
            return buffer;
        } else {
            return buffer;
        }
    }

    public Object clone() {
        NameValue retval = (NameValue) super.clone();
        if (value != null)
            retval.value = makeClone(value);
        return retval;
    }


    public boolean equals(Object other) {
        if (other == null ) return false;
        if (!other.getClass().equals(this.getClass()))
            return false;
        NameValue that = (NameValue) other;
        if (this == that)
            return true;
        if (this.name == null && that.name != null || this.name != null
                && that.name == null)
            return false;
        if (this.name != null && this.name.compareToIgnoreCase(that.name) != 0)
            return false;
        if (this.value != null && that.value == null || this.value == null
                && that.value != null)
            return false;
        if (this.value == that.value)
            return true;
        if (value instanceof String) {
            if (isQuotedString)
                return this.value.equals(that.value);
            String val = (String) this.value;
            String val1 = (String) that.value;
            return val.compareToIgnoreCase(val1) == 0;
        } else
            return this.value.equals(that.value);
    }


    public String getKey() {

        return this.name;
    }


    public String getValue() {

        if(value == null)
            return null;

        return value.toString();
    }


    public String setValue(String value) {
        String retval = this.value == null ? null : value;
        this.value = value;
        return retval;

    }

    @Override
    public int hashCode() {
        return this.encode().toLowerCase().hashCode();
    }
}
