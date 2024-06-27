
package com.jswitch.sip.header;

import com.jswitch.sip.SIPConstants;
import com.jswitch.sip.SIPObject;
import lombok.Getter;

import static com.jswitch.common.constant.Separators.NEWLINE;
import static com.jswitch.common.constant.Separators.SP;

public final class StatusLine extends SIPObject implements SipStatusLine {


    private static final long serialVersionUID = -4738092215519950414L;

    protected boolean matchStatusClass;


    @Getter
    protected String sipVersion;


    @Getter
    protected int statusCode;


    @Getter
    protected String reasonPhrase;


    public boolean match(Object matchObj) {
        if (!(matchObj instanceof StatusLine))
            return false;
        StatusLine sl = (StatusLine) matchObj;
        if (sl.matchExpression != null)
            return sl.matchExpression.match(this.encode());
        if (sl.sipVersion != null && !sl.sipVersion.equals(sipVersion))
            return false;
        if (sl.statusCode != 0) {
            if (matchStatusClass) {
                int hiscode = sl.statusCode;
                String codeString = Integer.toString(sl.statusCode);
                String mycode = Integer.toString(statusCode);
                if (codeString.charAt(0) != mycode.charAt(0))
                    return false;
            } else {
                if (statusCode != sl.statusCode)
                    return false;
            }
        }
        if (sl.reasonPhrase == null || reasonPhrase == sl.reasonPhrase)
            return true;
        return reasonPhrase.equals(sl.reasonPhrase);

    }


    public void setMatchStatusClass(boolean flag) {
        matchStatusClass = flag;
    }


    public StatusLine() {
        reasonPhrase = null;
        sipVersion = SIPConstants.SIP_VERSION_STRING;
    }


    public String encode() {
        String encoding = SIPConstants.SIP_VERSION_STRING + SP + statusCode;
        if (reasonPhrase != null)
            encoding += SP + reasonPhrase;
        encoding += NEWLINE;
        return encoding;
    }


    public void setSipVersion(String s) {
        sipVersion = s;
    }


    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }


    public void setReasonPhrase(String reasonPhrase) {
        this.reasonPhrase = reasonPhrase;
    }

    public String getVersionMajor() {
        if (sipVersion == null)
            return null;
        String major = null;
        boolean slash = false;
        for (int i = 0; i < sipVersion.length(); i++) {
            if (sipVersion.charAt(i) == '.')
                slash = false;
            if (slash) {
                if (major == null)
                    major = "" + sipVersion.charAt(i);
                else
                    major += sipVersion.charAt(i);
            }
            if (sipVersion.charAt(i) == '/')
                slash = true;
        }
        return major;
    }


    public String getVersionMinor() {
        if (sipVersion == null)
            return null;
        String minor = null;
        boolean dot = false;
        for (int i = 0; i < sipVersion.length(); i++) {
            if (dot) {
                if (minor == null)
                    minor = "" + sipVersion.charAt(i);
                else
                    minor += sipVersion.charAt(i);
            }
            if (sipVersion.charAt(i) == '.')
                dot = true;
        }
        return minor;
    }
}

