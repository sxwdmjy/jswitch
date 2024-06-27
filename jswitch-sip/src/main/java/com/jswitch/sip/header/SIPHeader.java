package com.jswitch.sip.header;

import com.jswitch.sip.SIPHeaderNames;
import com.jswitch.sip.SIPObject;
import lombok.Getter;
import lombok.Setter;

import static com.jswitch.common.constant.Separators.*;

@Getter
public abstract class SIPHeader extends SIPObject implements SIPHeaderNames, Header {

    @Setter
    protected String headerName;

    protected SIPHeader(String hname) {
        headerName = hname.intern();
    }


    public SIPHeader() {
    }


    public String getName() {
        return this.headerName;
    }


    public String getHeaderValue() {
        return encodeBody(new StringBuilder()).toString();
    }


    public boolean isHeaderList() {
        return false;
    }


    public String encode() {
        return encode(new StringBuilder()).toString();
    }

    public StringBuilder encode(StringBuilder buffer) {
        buffer.append(this.headerName).append(COLON).append(SP);
        this.encodeBody(buffer);
        buffer.append(NEWLINE);
        return buffer;
    }

    public abstract StringBuilder encodeBody(StringBuilder buffer);


    public String getValue() {
        return this.getHeaderValue();
    }


    public int hashCode() {
        return this.headerName.hashCode();
    }

    public final String toString() {
        return this.encode();
    }
}
