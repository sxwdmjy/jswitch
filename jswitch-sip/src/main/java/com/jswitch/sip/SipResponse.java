package com.jswitch.sip;

import com.jswitch.sip.header.*;
import com.jswitch.sip.message.ResponseExt;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.LinkedList;

@Slf4j
public class SipResponse extends SipMessage implements ResponseExt {

    @Getter
    protected StatusLine statusLine;

    private boolean isRetransmission = true;


    public static String getReasonPhrase(int rc) {
        String retval = null;
        switch (rc) {
            case TRYING:
                retval = "Trying";
                break;
            case RINGING:
                retval = "Ringing";
                break;
            case CALL_IS_BEING_FORWARDED:
                retval = "Call is being forwarded";
                break;
            case QUEUED:
                retval = "Queued";
                break;
            case SESSION_PROGRESS:
                retval = "Session progress";
                break;
            case OK:
                retval = "OK";
                break;
            case ACCEPTED:
                retval = "Accepted";
                break;
            case MULTIPLE_CHOICES:
                retval = "Multiple choices";
                break;
            case MOVED_PERMANENTLY:
                retval = "Moved permanently";
                break;
            case MOVED_TEMPORARILY:
                retval = "Moved Temporarily";
                break;
            case USE_PROXY:
                retval = "Use proxy";
                break;
            case ALTERNATIVE_SERVICE:
                retval = "Alternative service";
                break;
            case BAD_REQUEST:
                retval = "Bad request";
                break;
            case UNAUTHORIZED:
                retval = "Unauthorized";
                break;
            case PAYMENT_REQUIRED:
                retval = "Payment required";
                break;
            case FORBIDDEN:
                retval = "Forbidden";
                break;
            case NOT_FOUND:
                retval = "Not found";
                break;
            case METHOD_NOT_ALLOWED:
                retval = "Method not allowed";
                break;
            case NOT_ACCEPTABLE:
                retval = "Not acceptable";
                break;
            case PROXY_AUTHENTICATION_REQUIRED:
                retval = "Proxy Authentication required";
                break;
            case REQUEST_TIMEOUT:
                retval = "Request timeout";
                break;
            case GONE:
                retval = "Gone";
                break;
            case TEMPORARILY_UNAVAILABLE:
                retval = "Temporarily Unavailable";
                break;
            case REQUEST_ENTITY_TOO_LARGE:
                retval = "Request entity too large";
                break;
            case REQUEST_URI_TOO_LONG:
                retval = "Request-URI too large";
                break;
            case UNSUPPORTED_MEDIA_TYPE:
                retval = "Unsupported media type";
                break;
            case UNSUPPORTED_URI_SCHEME:
                retval = "Unsupported URI Scheme";
                break;
            case BAD_EXTENSION:
                retval = "Bad extension";
                break;
            case EXTENSION_REQUIRED:
                retval = "Etension Required";
                break;
            case INTERVAL_TOO_BRIEF:
                retval = "Interval too brief";
                break;
            case CALL_OR_TRANSACTION_DOES_NOT_EXIST:
                retval = "Call leg/Transaction does not exist";
                break;
            case LOOP_DETECTED:
                retval = "Loop detected";
                break;
            case TOO_MANY_HOPS:
                retval = "Too many hops";
                break;
            case ADDRESS_INCOMPLETE:
                retval = "Address incomplete";
                break;
            case AMBIGUOUS:
                retval = "Ambiguous";
                break;
            case BUSY_HERE:
                retval = "Busy here";
                break;
            case REQUEST_TERMINATED:
                retval = "Request Terminated";
                break;
            case NOT_ACCEPTABLE_HERE:
                retval = "Not Acceptable here";
                break;
            case BAD_EVENT:
                retval = "Bad Event";
                break;
            case REQUEST_PENDING:
                retval = "Request Pending";
                break;
            case SERVER_INTERNAL_ERROR:
                retval = "Server Internal Error";
                break;
            case UNDECIPHERABLE:
                retval = "Undecipherable";
                break;
            case NOT_IMPLEMENTED:
                retval = "Not implemented";
                break;
            case BAD_GATEWAY:
                retval = "Bad gateway";
                break;
            case SERVICE_UNAVAILABLE:
                retval = "Service unavailable";
                break;
            case SERVER_TIMEOUT:
                retval = "Gateway timeout";
                break;
            case VERSION_NOT_SUPPORTED:
                retval = "SIP version not supported";
                break;
            case MESSAGE_TOO_LARGE:
                retval = "Message Too Large";
                break;
            case BUSY_EVERYWHERE:
                retval = "Busy everywhere";
                break;
            case DECLINE:
                retval = "Decline";
                break;
            case DOES_NOT_EXIST_ANYWHERE:
                retval = "Does not exist anywhere";
                break;
            case SESSION_NOT_ACCEPTABLE:
                retval = "Session Not acceptable";
                break;
            case CONDITIONAL_REQUEST_FAILED:
                retval = "Conditional request failed";
                break;
            default:
                retval = "Unknown Status";
        }
        return retval;

    }


    public void setStatusCode(int statusCode) throws ParseException {
        if (statusCode < 100 || statusCode > 699)
            throw new ParseException("bad status code", 0);
        if (this.statusLine == null)
            this.statusLine = new StatusLine();
        this.statusLine.setStatusCode(statusCode);
    }


    public int getStatusCode() {
        return statusLine.getStatusCode();
    }


    public void setReasonPhrase(String reasonPhrase) {
        if (reasonPhrase == null)
            throw new IllegalArgumentException("Bad reason phrase");
        if (this.statusLine == null)
            this.statusLine = new StatusLine();
        this.statusLine.setReasonPhrase(reasonPhrase);
    }

    public String getReasonPhrase() {
        if (statusLine == null || statusLine.getReasonPhrase() == null)
            return "";
        else
            return statusLine.getReasonPhrase();
    }


    public static boolean isFinalResponse(int rc) {
        return rc >= 200 && rc < 700;
    }


    public boolean isFinalResponse() {
        return isFinalResponse(statusLine.getStatusCode());
    }


    public void setStatusLine(StatusLine sl) {
        statusLine = sl;
    }


    public SipResponse() {
        super();
    }

    public String debugDump() {
        String superstring = super.debugDump();
        stringRepresentation = "";
        sprint(SipResponse.class.getCanonicalName());
        sprint("{");
        if (statusLine != null) {
            sprint(statusLine.debugDump());
        }
        sprint(superstring);
        sprint("}");
        return stringRepresentation;
    }


    public void checkHeaders() throws ParseException {
        if (getCSeq() == null) {
            throw new ParseException(CSeq.NAME + " Is missing ", 0);
        }
        if (getTo() == null) {
            throw new ParseException(To.NAME + " Is missing ", 0);
        }
        if (getFrom() == null) {
            throw new ParseException(From.NAME + " Is missing ", 0);
        }
        if (getViaHeaders() == null) {
            throw new ParseException(Via.NAME + " Is missing ", 0);
        }
        if (getCallId() == null) {
            throw new ParseException(CallID.NAME + " Is missing ", 0);
        }

        if (getStatusCode() > 699) {
            throw new ParseException("Unknown error code!" + getStatusCode(), 0);
        }
    }

    public String encode() {
        String retval;
        if (statusLine != null)
            retval = statusLine.encode() + super.encode();
        else
            retval = super.encode();
        return retval;
    }

    public StringBuilder encodeMessage(StringBuilder retval) {
        if (statusLine != null) {
            statusLine.encode(retval);
            super.encodeSIPHeaders(retval);
        } else {
            retval = super.encodeSIPHeaders(retval);
        }
        return retval;
    }


    public LinkedList getMessageAsEncodedStrings() {
        LinkedList retval = super.getMessageAsEncodedStrings();
        if (statusLine != null)
            retval.addFirst(statusLine.encode());
        return retval;

    }

    public Object clone() {
        SipResponse retval = (SipResponse) super.clone();
        if (this.statusLine != null)
            retval.statusLine = (StatusLine) this.statusLine.clone();
        return retval;
    }


    public boolean equals(Object other) {
        if (!this.getClass().equals(other.getClass()))
            return false;
        SipResponse that = (SipResponse) other;
        return statusLine.equals(that.statusLine) && super.equals(other);
    }

    public boolean match(Object matchObj) {
        if (matchObj == null)
            return true;
        else if (!matchObj.getClass().equals(this.getClass())) {
            return false;
        } else if (matchObj == this)
            return true;
        SipResponse that = (SipResponse) matchObj;
        StatusLine rline = that.statusLine;
        if (this.statusLine == null && rline != null)
            return false;
        else if (this.statusLine == rline)
            return super.match(matchObj);
        else {
            return statusLine.match(that.statusLine) && super.match(matchObj);
        }

    }

    public byte[] encodeAsBytes(String transport) {
        byte[] slbytes = null;
        if (statusLine != null) {
            slbytes = statusLine.encode().getBytes(StandardCharsets.UTF_8);
        }
        byte[] superbytes = super.encodeAsBytes(transport);
        byte[] retval = new byte[slbytes.length + superbytes.length];
        System.arraycopy(slbytes, 0, retval, 0, slbytes.length);
        System.arraycopy(superbytes, 0, retval, slbytes.length,
                superbytes.length);
        return retval;
    }

    public String getFirstLine() {
        if (this.statusLine == null)
            return null;
        else
            return this.statusLine.encode();
    }

    public void setSIPVersion(String sipVersion) {
        this.statusLine.setSipVersion(sipVersion);
    }

    public String getSIPVersion() {
        return this.statusLine.getSipVersion();
    }

    public String toString() {
        if (statusLine == null) return "";
        else return statusLine.encode() + super.encode();
    }

    @Override
    public void cleanUp() {
        super.cleanUp();
    }


    public void setRetransmission(boolean isRetransmission) {

        this.isRetransmission = isRetransmission;
    }

    public boolean isRetransmission() {
        return isRetransmission;
    }

}
