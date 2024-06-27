package com.jswitch.sip;

import com.jswitch.common.exception.SipException;
import com.jswitch.sip.exception.SIPDuplicateHeaderException;
import com.jswitch.sip.header.*;
import com.jswitch.sip.message.MessageFactoryImpl;
import com.jswitch.sip.header.Via;
import lombok.Data;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Data
public class SipRequest extends SipMessage implements Request {


    private static final String DEFAULT_USER = "ip";

    private static final String DEFAULT_TRANSPORT = "udp";

    private transient Object transactionPointer;

    @Getter
    protected RequestLine requestLine;

    private transient Object messageChannel;

    private transient Object inviteTransaction;

    private static final Set<String> targetRefreshMethods = new HashSet<String>();
    private static final Map<String, String> nameTable = new ConcurrentHashMap<String, String>(15);
    protected static final Set<String> headersToIncludeInResponse = new HashSet<String>(0);

    private static void putName(String name) {
        nameTable.put(name, name);
    }


    static {
        targetRefreshMethods.add(Request.INVITE);
        targetRefreshMethods.add(Request.UPDATE);
        targetRefreshMethods.add(Request.SUBSCRIBE);
        targetRefreshMethods.add(Request.NOTIFY);
        targetRefreshMethods.add(Request.REFER);

        putName(Request.INVITE);
        putName(Request.BYE);
        putName(Request.CANCEL);
        putName(Request.ACK);
        putName(Request.PRACK);
        putName(Request.INFO);
        putName(Request.MESSAGE);
        putName(Request.NOTIFY);
        putName(Request.OPTIONS);
        putName(Request.PRACK);
        putName(Request.PUBLISH);
        putName(Request.REFER);
        putName(Request.REGISTER);
        putName(Request.SUBSCRIBE);
        putName(Request.UPDATE);

        headersToIncludeInResponse.add(FromHeader.NAME.toLowerCase());
        headersToIncludeInResponse.add(ToHeader.NAME.toLowerCase());
        headersToIncludeInResponse.add(ViaHeader.NAME.toLowerCase());
        headersToIncludeInResponse.add(RecordRouteHeader.NAME.toLowerCase());
        headersToIncludeInResponse.add(CallIdHeader.NAME.toLowerCase());
        headersToIncludeInResponse.add(CSeqHeader.NAME.toLowerCase());
        headersToIncludeInResponse.add(TimeStampHeader.NAME.toLowerCase());
    }

    public static boolean isTargetRefresh(String ucaseMethod) {
        return targetRefreshMethods.contains(ucaseMethod);
    }


    public static boolean isDialogCreating(String ucaseMethod) {
        return true;//SIPTransactionStack.isDialogCreated(ucaseMethod);
    }


    public static String getCannonicalName(String method) {
        if (nameTable.containsKey(method))
            return (String) nameTable.get(method);
        else
            return method;
    }


    public void setRequestLine(RequestLine requestLine) {
        this.requestLine = requestLine;
    }


    public SipRequest() {
        super();
    }


    public String debugDump() {
        String superstring = super.debugDump();
        stringRepresentation = "";
        sprint(SipRequest.class.getName());
        sprint("{");
        if (requestLine != null)
            sprint(requestLine.debugDump());
        sprint(superstring);
        sprint("}");
        return stringRepresentation;
    }

    public void checkHeaders() throws ParseException {
        String prefix = "Missing a required header : ";

        if (getCSeq() == null) {
            throw new ParseException(prefix + CSeqHeader.NAME, 0);
        }
        if (getTo() == null) {
            throw new ParseException(prefix + ToHeader.NAME, 0);
        }

        if (this.callIdHeader == null || this.callIdHeader.getCallId() == null
                || callIdHeader.getCallId().equals("")) {
            throw new ParseException(prefix + CallIdHeader.NAME, 0);
        }
        if (getFrom() == null) {
            throw new ParseException(prefix + FromHeader.NAME, 0);
        }
        if (getViaHeaders() == null) {
            throw new ParseException(prefix + ViaHeader.NAME, 0);
        }
        if (getMaxForwards() == null) {
            throw new ParseException(prefix + MaxForwardsHeader.NAME, 0);
        }

        if (getTopmostVia() == null)
            throw new ParseException("No via header in request! ", 0);

        if (getMethod().equals(Request.NOTIFY)) {
            if (getHeader(SubscriptionStateHeader.NAME) == null)
                throw new ParseException(prefix + SubscriptionStateHeader.NAME, 0);
            if (getFromHeader().getTag() != null &&
                    getToHeader().getTag() != null && getHeader(EventHeader.NAME) == null) {
                throw new ParseException(prefix + EventHeader.NAME, 0);
            }

        } else if (getMethod().equals(Request.PUBLISH)) {
            if (getHeader(EventHeader.NAME) == null)
                throw new ParseException(prefix + EventHeader.NAME, 0);
        }

        final String method = requestLine.getMethod();
        /*if (SIPTransactionStack.isDialogCreated(method)) {
            if (this.getContactHeader() == null) {
                if (this.getToTag() == null)
                    throw new ParseException(prefix + ContactHeader.NAME, 0);
            }
        }*/

        if (requestLine != null && method != null
                && getCSeq().getMethod() != null
                && method.compareTo(getCSeq().getMethod()) != 0) {
            throw new ParseException("CSEQ method mismatch with  Request-Line ", 0);

        }

    }


    protected void setDefaults() {
        if (requestLine == null)
            return;
        String method = requestLine.getMethod();
        if (method == null)
            return;
        GenericURI u = requestLine.getUri();
        if (u == null)
            return;
        if (method.compareTo(Request.REGISTER) == 0 || method.compareTo(Request.INVITE) == 0) {
            if (u instanceof SipUri) {
                SipUri sipUri = (SipUri) u;
                sipUri.setUserParam(DEFAULT_USER);
                try {
                    sipUri.setTransportParam(DEFAULT_TRANSPORT);
                } catch (ParseException ex) {
                }
            }
        }
    }


    protected void setRequestLineDefaults() {
        String method = requestLine.getMethod();
        if (method == null) {
            CSeq cseq = (CSeq) this.getCSeq();
            if (cseq != null) {
                method = getCannonicalName(cseq.getMethod());
                requestLine.setMethod(method);
            }
        }
    }


    public URI getRequestURI() {
        if (this.requestLine == null)
            return null;
        else
            return (URI) this.requestLine.getUri();
    }

    public void setRequestURI(URI uri) {
        if (uri == null) {
            throw new NullPointerException("Null request URI");
        }
        if (this.requestLine == null) {
            this.requestLine = new RequestLine();
        }
        this.requestLine.setUri((GenericURI) uri);
        this.nullRequest = false;
    }

    public void setMethod(String method) {
        if (method == null)
            throw new IllegalArgumentException("null method");
        if (this.requestLine == null) {
            this.requestLine = new RequestLine();
        }

        String meth = getCannonicalName(method);
        this.requestLine.setMethod(meth);

        if (this.cSeqHeader != null) {
            try {
                this.cSeqHeader.setMethod(meth);
            } catch (ParseException e) {
            }
        }
    }

    public String getMethod() {
        if (requestLine == null)
            return null;
        else
            return requestLine.getMethod();
    }

    public String encode() {
        String retval;
        if (requestLine != null) {
            this.setRequestLineDefaults();
            retval = requestLine.encode() + super.encode();
        } else if (this.isNullRequest()) {
            retval = "\r\n\r\n";
        } else {
            retval = super.encode();
        }
        return retval;
    }


    public StringBuilder encodeMessage(StringBuilder retval) {
        if (requestLine != null) {
            this.setRequestLineDefaults();
            requestLine.encode(retval);
            encodeSIPHeaders(retval);
        } else if (this.isNullRequest()) {
            retval.append("\r\n\r\n");
        } else
            retval = encodeSIPHeaders(retval);
        return retval;

    }


    public String toString() {
        return this.encode();
    }


    public Object clone() {
        SipRequest retval = (SipRequest) super.clone();

        retval.transactionPointer = null;
        if (this.requestLine != null)
            retval.requestLine = (RequestLine) this.requestLine.clone();

        return retval;
    }

    public boolean equals(Object other) {
        if (!this.getClass().equals(other.getClass()))
            return false;
        SipRequest that = (SipRequest) other;
        return requestLine.equals(that.requestLine) && super.equals(other);
    }

    public LinkedList getMessageAsEncodedStrings() {
        LinkedList retval = super.getMessageAsEncodedStrings();
        if (requestLine != null) {
            this.setRequestLineDefaults();
            retval.addFirst(requestLine.encode());
        }
        return retval;

    }

    public boolean match(Object matchObj) {
        if (matchObj == null)
            return true;
        else if (!matchObj.getClass().equals(this.getClass()))
            return false;
        else if (matchObj == this)
            return true;
        SipRequest that = (SipRequest) matchObj;
        RequestLine rline = that.requestLine;
        if (this.requestLine == null && rline != null)
            return false;
        else if (this.requestLine == rline)
            return super.match(matchObj);
        return requestLine.match(that.requestLine) && super.match(matchObj);

    }


    public byte[] encodeAsBytes(String transport) {
        if (this.isNullRequest()) {
            return "\r\n\r\n".getBytes();
        } else if (this.requestLine == null) {
            return new byte[0];
        }

        byte[] rlbytes = null;
        rlbytes = requestLine.encode().getBytes(StandardCharsets.UTF_8);
        byte[] superbytes = super.encodeAsBytes(transport);
        byte[] retval = new byte[rlbytes.length + superbytes.length];
        System.arraycopy(rlbytes, 0, retval, 0, rlbytes.length);
        System.arraycopy(superbytes, 0, retval, rlbytes.length, superbytes.length);
        return retval;
    }


    public SipResponse createResponse(int statusCode) {

        String reasonPhrase = SipResponse.getReasonPhrase(statusCode);
        return this.createResponse(statusCode, reasonPhrase);

    }


    public SipResponse createResponse(int statusCode, String reasonPhrase) {
        SipResponse newResponse = new SipResponse();
        try {
            newResponse.setStatusCode(statusCode);
        } catch (ParseException ex) {
            throw new IllegalArgumentException("Bad code " + statusCode);
        }
        if (reasonPhrase != null)
            newResponse.setReasonPhrase(reasonPhrase);
        else
            newResponse.setReasonPhrase(SipResponse.getReasonPhrase(statusCode));

        for (String headerName : headersToIncludeInResponse) {
            SIPHeader nextHeader = headerTable.get(headerName);
            if (nextHeader != null) {
                if (!(nextHeader instanceof RecordRouteList) || mustCopyRR(statusCode)) {
                    try {
                        newResponse.attachHeader((SIPHeader) nextHeader.clone(), false);
                    } catch (SIPDuplicateHeaderException e) {
                        log.error("Exception setting header " + nextHeader.getName() + " " + e.getMessage(), e);
                    }
                }
            }
        }

        if (MessageFactoryImpl.getDefaultServerHeader() != null) {
            newResponse.setHeader(MessageFactoryImpl.getDefaultServerHeader());
        }

        ServerHeader server = MessageFactoryImpl.getDefaultServerHeader();
        if (server != null) {
            newResponse.setHeader(server);
        }
        newResponse.setRemoteAddress(this.getRemoteAddress());
        newResponse.setRemotePort(this.getRemotePort());
        return newResponse;
    }

    protected final boolean mustCopyRR(int code) {
        if (code > 100 && code < 300) {
            return isDialogCreating(this.getMethod()) && getToTag() == null;
        } else return false;
    }

    public SipRequest createCancelRequest() throws SipException {
        if (!this.getMethod().equals(Request.INVITE))
            throw new SipException("Attempt to create CANCEL for " + this.getMethod());
        SipRequest cancel = new SipRequest();
        cancel.setRequestLine((RequestLine) this.requestLine.clone());
        cancel.setMethod(Request.CANCEL);
        cancel.setHeader((Header) this.callIdHeader.clone());
        cancel.setHeader((Header) this.toHeader.clone());
        cancel.setHeader((Header) cSeqHeader.clone());
        try {
            cancel.getCSeq().setMethod(Request.CANCEL);
        } catch (ParseException e) {
            e.printStackTrace(); // should not happen
        }
        cancel.setHeader((Header) this.fromHeader.clone());

        cancel.addFirst((Header) this.getTopmostVia().clone());
        cancel.setHeader((Header) this.maxForwardsHeader.clone());

        if (this.getRouteHeaders() != null) {
            cancel.setHeader((SIPHeaderList<?>) this.getRouteHeaders().clone());
        }
        if (MessageFactoryImpl.getDefaultUserAgentHeader() != null) {
            cancel.setHeader(MessageFactoryImpl.getDefaultUserAgentHeader());

        }
        cancel.setRemoteAddress(this.getRemoteAddress());
        cancel.setRemotePort(this.getRemotePort());
        return cancel;
    }


    public SipRequest createAckRequest(To responseToHeader) {

        SipRequest newRequest = (SipRequest) this.clone();
        newRequest.setMethod(Request.ACK);

        newRequest.removeHeader(RouteHeader.NAME);

        newRequest.removeHeader(ProxyAuthorizationHeader.NAME);

        newRequest.removeContent();

        newRequest.removeHeader(ContentTypeHeader.NAME);

        try {
            newRequest.getCSeq().setMethod(Request.ACK);
        } catch (ParseException e) {
        }
        if (responseToHeader != null) {
            newRequest.setTo(responseToHeader);
        }

        newRequest.removeHeader(ContactHeader.NAME);
        newRequest.removeHeader(ExpiresHeader.NAME);
        ViaList via = newRequest.getViaHeaders();

        if (via != null && via.size() > 1) {
            for (int i = 2; i < via.size(); i++) {
                via.remove(i);
            }
        }

        if (MessageFactoryImpl.getDefaultUserAgentHeader() != null) {
            newRequest.setHeader(MessageFactoryImpl.getDefaultUserAgentHeader());

        }
        newRequest.setRemoteAddress(this.getRemoteAddress());
        newRequest.setRemotePort(this.getRemotePort());
        return newRequest;
    }


    public final SipRequest createErrorAck(To responseToHeader) throws SipException,
            ParseException {


        SipRequest newRequest = new SipRequest();
        newRequest.setRequestLine((RequestLine) this.requestLine.clone());
        newRequest.setMethod(Request.ACK);
        newRequest.setHeader((Header) this.callIdHeader.clone());
        newRequest.setHeader((Header) this.maxForwardsHeader.clone());
        // 130
        // fix
        newRequest.setHeader((Header) this.fromHeader.clone());
        newRequest.setHeader((Header) responseToHeader.clone());
        newRequest.addFirst((Header) this.getTopmostVia().clone());
        newRequest.setHeader((Header) cSeqHeader.clone());
        newRequest.getCSeq().setMethod(Request.ACK);


        if (this.getRouteHeaders() != null) {
            newRequest.setHeader((SIPHeaderList) this.getRouteHeaders().clone());
        }
        if (MessageFactoryImpl.getDefaultUserAgentHeader() != null) {
            newRequest.setHeader(MessageFactoryImpl.getDefaultUserAgentHeader());

        }
        newRequest.setRemoteAddress(this.getRemoteAddress());
        newRequest.setRemotePort(this.getRemotePort());
        return newRequest;
    }

    public String getViaHost() {
        Via via = (Via) this.getViaHeaders().getFirst();
        return via.getHost();

    }

    public int getViaPort() {
        Via via = (Via) this.getViaHeaders().getFirst();
        if (via.hasPort())
            return via.getPort();
        else
            return 5060;
    }


    public String getFirstLine() {
        if (requestLine == null)
            return null;
        else
            return this.requestLine.encode();
    }


    public void setSIPVersion(String sipVersion) throws ParseException {
        if (sipVersion == null || !sipVersion.equalsIgnoreCase("SIP/2.0"))
            throw new ParseException("sipVersion", 0);
        this.requestLine.setSipVersion(sipVersion);
    }


    public String getSIPVersion() {
        return this.requestLine.getSipVersion();
    }


    public Object getTransaction() {
        return this.transactionPointer;
    }


    public void setTransaction(Object transaction) {
        this.transactionPointer = transaction;
    }


    public Object getMessageChannel() {
        return this.messageChannel;
    }


    public void setMessageChannel(Object messageChannel) {
        this.messageChannel = messageChannel;
    }


    public String getMergeId() {

        String fromTag = this.getFromTag();
        String cseq = this.cSeqHeader.toString();
        String callId = this.callIdHeader.getCallId();

        String requestUri = this.getRequestURI().toString();

        if (fromTag != null) {
            return new StringBuilder().append(requestUri).append(":").append(fromTag).append(":").append(cseq).append(":")
                    .append(callId).toString();
        } else
            return null;

    }


    public void setInviteTransaction(Object inviteTransaction) {
        this.inviteTransaction = inviteTransaction;
    }


    public Object getInviteTransaction() {
        return inviteTransaction;
    }

    @Override
    public void cleanUp() {
        super.cleanUp();
    }
}
