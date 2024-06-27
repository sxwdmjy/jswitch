
package com.jswitch.sip.header;

import com.jswitch.common.exception.InvalidArgumentException;
import lombok.Getter;

import static com.jswitch.common.constant.Separators.DOT;
import static com.jswitch.sip.SIPHeaderNames.MIME_VERSION;

@Getter
public class MimeVersion extends SIPHeader implements MimeVersionHeader {


    private static final long serialVersionUID = -7951589626435082068L;


    protected int minorVersion;


    protected int majorVersion;


    public MimeVersion() {
        super(MIME_VERSION);
    }


    public void setMinorVersion(int minorVersion)
        throws InvalidArgumentException {
        if (minorVersion < 0)
            throw new InvalidArgumentException("SIP Exception, MimeVersion, setMinorVersion(), the minorVersion parameter is null");
        this.minorVersion = minorVersion;
    }


    public void setMajorVersion(int majorVersion)
        throws InvalidArgumentException {
        if (majorVersion < 0)
            throw new InvalidArgumentException("SIP Exception, MimeVersion, setMajorVersion(), the majorVersion parameter is null");
        this.majorVersion = majorVersion;
    }


    public StringBuilder encodeBody(StringBuilder buffer) {
        return buffer.append(Integer.toString(majorVersion))
            .append(DOT)
            .append(Integer.toString(minorVersion));
    }

}

