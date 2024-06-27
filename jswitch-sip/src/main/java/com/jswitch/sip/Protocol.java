package com.jswitch.sip;

import lombok.Getter;

import java.text.ParseException;
import java.util.Locale;

import static com.jswitch.common.constant.Separators.SLASH;

/**
 * @author danmo
 * @date 2024-06-18 14:38
 **/
@Getter
public class Protocol extends SIPObject {

    protected String protocolName;

    protected String protocolVersion;

    protected String transport;

    public String encode() {
        return encode(new StringBuilder()).toString();
    }

    public StringBuilder encode(StringBuilder buffer) {
        buffer.append(protocolName.toUpperCase(Locale.ENGLISH))
                .append(SLASH)
                .append(protocolVersion)
                .append(SLASH)
                .append(transport.toUpperCase());
        return buffer;
    }


    public String getProtocol() {
        return protocolName + '/' + protocolVersion;
    }

    public void setProtocol(String name_and_version) throws ParseException {
        int slash = name_and_version.indexOf('/');
        if (slash > 0) {
            this.protocolName = name_and_version.substring(0, slash);
            this.protocolVersion = name_and_version.substring(slash + 1);
        } else throw new ParseException("Missing '/' in protocol", 0);
    }


    public void setProtocolName(String p) {
        protocolName = p;
    }


    public void setProtocolVersion(String p) {
        protocolVersion = p;
    }

    public void setTransport(String t) {
        transport = t;
    }

    public Protocol() {
        protocolName = "SIP";
        protocolVersion = "2.0";
        transport = "UDP";
    }
}
