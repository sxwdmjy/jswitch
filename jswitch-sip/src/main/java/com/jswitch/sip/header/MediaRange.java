package com.jswitch.sip.header;

import com.jswitch.sip.SIPObject;

import static com.jswitch.common.constant.Separators.SLASH;

/**
 * @author danmo
 * @date 2024-06-18 15:23
 **/
public class MediaRange extends SIPObject {

    private static final long serialVersionUID = -1297125815438079210L;

    protected String type;

    protected String subtype;

    public MediaRange() {
    }

    public String getType() {
        return type;
    }

    public String getSubtype() {
        return subtype;
    }

    public void setType(String t) {
        type = t;
    }


    public void setSubtype(String s) {
        subtype = s;
    }


    public String encode() {
        return encode(new StringBuilder()).toString();
    }

    public StringBuilder encode(StringBuilder buffer) {
        return buffer.append(type)
                .append(SLASH)
                .append(subtype);
    }
}
