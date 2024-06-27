package com.jswitch.sip;

import cn.hutool.core.util.IdUtil;
import com.jswitch.common.constant.Separators;
import com.jswitch.common.exception.InvalidArgumentException;
import com.jswitch.common.exception.SipException;
import com.jswitch.sip.exception.SIPDuplicateHeaderException;
import com.jswitch.sip.header.*;
import com.jswitch.sip.message.*;
import com.jswitch.sip.parse.HeaderParser;
import com.jswitch.sip.parse.ParserFactory;
import com.jswitch.sip.utils.SDPParser;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.InetAddress;
import java.text.ParseException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import static com.jswitch.common.constant.Separators.COLON;
import static com.jswitch.common.constant.Separators.NEWLINE;

@Slf4j
public abstract class SipMessage extends MessageObject implements Message {

    @Getter
    protected boolean nullRequest;

    protected LinkedList<String> unrecognizedHeaders;

    protected ConcurrentLinkedQueue<SIPHeader> headers;

    protected From fromHeader;

    protected To toHeader;

    protected CSeq cSeqHeader;

    protected CallID callIdHeader;

    protected ContentLength contentLengthHeader;

    protected MaxForwards maxForwardsHeader;

    protected int size;

    protected String messageContent;

    protected byte[] messageContentBytes;

    protected Object messageContentObject;

    protected Map<String, SIPHeader> headerTable;


    @Getter
    protected Object applicationData;

    protected String forkId;


    @Getter
    private InetAddress remoteAddress;


    @Getter
    private int remotePort;

    @Getter
    private InetAddress localAddress;

    @Getter
    private int localPort;

    @Getter
    private InetAddress peerPacketSourceAddress;

    @Getter
    private int peerPacketSourcePort;

    public LinkedList<String> getMessageAsEncodedStrings() {
        LinkedList<String> retval = new LinkedList<String>();
        Iterator<SIPHeader> li = headers.iterator();
        while (li.hasNext()) {
            SIPHeader sipHeader = (SIPHeader) li.next();
            if (sipHeader instanceof SIPHeaderList) {
                SIPHeaderList<?> shl = (SIPHeaderList<?>) sipHeader;
                retval.addAll(shl.getHeadersAsEncodedStrings());
            } else {
                retval.add(sipHeader.encode());
            }
        }

        return retval;
    }


    protected StringBuilder encodeSIPHeaders(StringBuilder encoding) {
        for (SIPHeader siphdr : this.headers) {
            if (!(siphdr instanceof ContentLength))
                siphdr.encode(encoding);
        }

        return contentLengthHeader.encode(encoding).append(NEWLINE);
    }


    public abstract StringBuilder encodeMessage(StringBuilder retval);


    public final String getDialogId(boolean isServer) {
        To to = (To) this.getTo();
        return this.getDialogId(isServer, to.getTag());
    }


    public final String getDialogId(boolean isServer, String toTag) {
        From from = (From) this.getFrom();
        CallID cid = (CallID) this.getCallId();
        StringBuffer retval = new StringBuffer(cid.getCallId());
        if (!isServer) {
            if (from.getTag() != null) {
                retval.append(COLON);
                retval.append(from.getTag());
            }
            if (toTag != null) {
                retval.append(COLON);
                retval.append(toTag);
            }
        } else {
            if (toTag != null) {
                retval.append(COLON);
                retval.append(toTag);
            }
            if (from.getTag() != null) {
                retval.append(COLON);
                retval.append(from.getTag());
            }
        }
        return retval.toString().toLowerCase();
    }


    public boolean match(Object other) {
        if (other == null)
            return true;
        if (!other.getClass().equals(this.getClass()))
            return false;
        SipMessage matchObj = (SipMessage) other;
        Iterator<SIPHeader> li = matchObj.getHeaders();
        while (li.hasNext()) {
            SIPHeader hisHeaders = (SIPHeader) li.next();
            List<SIPHeader> myHeaders = this.getHeaderList(hisHeaders.getHeaderName());

            if (myHeaders == null || myHeaders.size() == 0)
                return false;

            if (hisHeaders instanceof SIPHeaderList) {
                ListIterator<?> outerIterator = ((SIPHeaderList<?>) hisHeaders)
                        .listIterator();
                while (outerIterator.hasNext()) {
                    SIPHeader hisHeader = (SIPHeader) outerIterator.next();
                    if (hisHeader instanceof ContentLength)
                        continue;
                    ListIterator<?> innerIterator = myHeaders.listIterator();
                    boolean found = false;
                    while (innerIterator.hasNext()) {
                        SIPHeader myHeader = (SIPHeader) innerIterator.next();
                        if (myHeader.match(hisHeader)) {
                            found = true;
                            break;
                        }
                    }
                    if (!found)
                        return false;
                }
            } else {
                SIPHeader hisHeader = hisHeaders;
                ListIterator<SIPHeader> innerIterator = myHeaders.listIterator();
                boolean found = false;
                while (innerIterator.hasNext()) {
                    SIPHeader myHeader = (SIPHeader) innerIterator.next();
                    if (myHeader.match(hisHeader)) {
                        found = true;
                        break;
                    }
                }
                if (!found)
                    return false;
            }
        }
        return true;

    }

    public void merge(Object template) {
        if (!template.getClass().equals(this.getClass()))
            throw new IllegalArgumentException("Bad class " + template.getClass());
        SipMessage templateMessage = (SipMessage) template;
        Object[] templateHeaders = templateMessage.headers.toArray();
        for (int i = 0; i < templateHeaders.length; i++) {
            SIPHeader hdr = (SIPHeader) templateHeaders[i];
            String hdrName = hdr.getHeaderName();
            List<SIPHeader> myHdrs = this.getHeaderList(hdrName);
            if (myHdrs == null) {
                this.attachHeader(hdr);
            } else {
                ListIterator<SIPHeader> it = myHdrs.listIterator();
                while (it.hasNext()) {
                    SIPHeader sipHdr = (SIPHeader) it.next();
                    sipHdr.merge(hdr);
                }
            }
        }

    }


    public String encode() {
        StringBuilder encoding = new StringBuilder();
        Iterator<SIPHeader> it = this.headers.iterator();
        while (it.hasNext()) {
            SIPHeader siphdr = (SIPHeader) it.next();
            if (!(siphdr instanceof ContentLength))
                siphdr.encode(encoding);
        }
        if (unrecognizedHeaders != null) {
            for (String unrecognized : unrecognizedHeaders) {
                encoding.append(unrecognized).append(NEWLINE);
            }
        }

        contentLengthHeader.encode(encoding).append(NEWLINE);

        if (this.messageContentObject != null) {
            String mbody = this.getContent().toString();

            encoding.append(mbody);
        } else if (this.messageContent != null || this.messageContentBytes != null) {

            String content = null;
            try {
                if (messageContent != null)
                    content = messageContent;
                else {
                    content = new String(messageContentBytes, getCharset());
                }
            } catch (UnsupportedEncodingException ex) {
                log.error("Unsupported encoding :{},{}", getCharset(), ex.getMessage(), ex);
            }

            encoding.append(content);
        }
        return encoding.toString();
    }


    public byte[] encodeAsBytes(String transport) {
        if (this instanceof SipRequest && ((SipRequest) this).isNullRequest()) {
            return "\r\n\r\n".getBytes();
        }

        ViaHeader topVia = (ViaHeader) this.getHeader(ViaHeader.NAME);
        try {
            topVia.setTransport(transport);
        } catch (ParseException e) {
            log.error("Exception setting transport :{}", e.getMessage(), e);
        }

        StringBuilder encoding = new StringBuilder();
        synchronized (this.headers) {
            Iterator<SIPHeader> it = this.headers.iterator();
            while (it.hasNext()) {
                SIPHeader siphdr = (SIPHeader) it.next();
                if (!(siphdr instanceof ContentLength))
                    siphdr.encode(encoding);
            }
        }
        contentLengthHeader.encode(encoding);
        encoding.append(NEWLINE);

        byte[] retval = null;
        byte[] content = this.getRawContent();
        if (content != null) {
            byte[] msgarray = null;
            try {
                msgarray = encoding.toString().getBytes(getCharset());
            } catch (UnsupportedEncodingException ex) {
                log.error("Unsupported encoding :{},{}", getCharset(), ex.getMessage(), ex);
            }
            retval = new byte[msgarray.length + content.length];
            System.arraycopy(msgarray, 0, retval, 0, msgarray.length);
            System.arraycopy(content, 0, retval, msgarray.length, content.length);
        } else {
            try {
                retval = encoding.toString().getBytes(getCharset());
            } catch (UnsupportedEncodingException ex) {
                log.error("Unsupported encoding :{},{}", getCharset(), ex.getMessage(), ex);
            }
        }
        return retval;
    }


    public Object clone() {
        SipMessage retval = (SipMessage) super.clone();
        retval.headerTable = new ConcurrentHashMap<String, SIPHeader>();
        retval.fromHeader = null;
        retval.toHeader = null;
        retval.cSeqHeader = null;
        retval.callIdHeader = null;
        retval.contentLengthHeader = null;
        retval.maxForwardsHeader = null;
        retval.forkId = null;
        if (this.headers != null) {
            retval.headers = new ConcurrentLinkedQueue<SIPHeader>();
            for (Iterator<SIPHeader> iter = headers.iterator(); iter.hasNext(); ) {
                SIPHeader hdr = (SIPHeader) iter.next();
                retval.attachHeader((SIPHeader) hdr.clone());
            }

        }
        if (this.messageContentBytes != null)
            retval.messageContentBytes = (byte[]) this.messageContentBytes.clone();
        if (this.messageContentObject != null)
            retval.messageContentObject = makeClone(messageContentObject);
        retval.unrecognizedHeaders = this.unrecognizedHeaders;
        retval.remoteAddress = this.remoteAddress;
        retval.remotePort = this.remotePort;
        return retval;
    }


    public String debugDump() {
        stringRepresentation = "";
        sprint("SIPMessage:");
        sprint("{");
        try {

            Field[] fields = this.getClass().getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                Field f = fields[i];
                Class<?> fieldType = f.getType();
                String fieldName = f.getName();
                if (f.get(this) != null && SIPHeader.class.isAssignableFrom(fieldType)
                        && fieldName.compareTo("headers") != 0) {
                    sprint(fieldName + Separators.EQUALS);
                    sprint(((SIPHeader) f.get(this)).debugDump());
                }
            }
        } catch (Exception ex) {
            log.error("Exception debugging toString()", ex);
        }

        sprint("List of headers : ");
        sprint(headers.toString());
        sprint("messageContent = ");
        sprint("{");
        sprint(messageContent);
        sprint("}");
        if (this.getContent() != null) {
            sprint(this.getContent().toString());
        }
        sprint("}");
        return stringRepresentation;
    }


    public SipMessage() {
        this.unrecognizedHeaders = new LinkedList<String>();
        this.headers = new ConcurrentLinkedQueue<SIPHeader>();
        headerTable = new ConcurrentHashMap<String, SIPHeader>();
        try {
            this.attachHeader(new ContentLength(0), false);
        } catch (Exception ex) {
        }
    }


    private void attachHeader(SIPHeader h) {
        if (h == null)
            throw new IllegalArgumentException("null header!");
        try {
            if (h instanceof SIPHeaderList) {
                SIPHeaderList<?> hl = (SIPHeaderList<?>) h;
                if (hl.isEmpty()) {
                    return;
                }
            }
            attachHeader(h, false, false);
        } catch (SIPDuplicateHeaderException ex) {
            log.error("Exception setting header :" + ex.getMessage(), ex);
        }
    }


    public void setHeader(Header sipHeader) {
        SIPHeader header = (SIPHeader) sipHeader;
        if (header == null)
            throw new IllegalArgumentException("null header!");
        try {
            if (header instanceof SIPHeaderList) {
                SIPHeaderList<?> hl = (SIPHeaderList<?>) header;
                if (hl.isEmpty())
                    return;
            }
            this.removeHeader(header.getHeaderName());
            attachHeader(header, true, false);
        } catch (SIPDuplicateHeaderException ex) {
            log.error("Exception setting header :" + ex.getMessage(), ex);
        }
    }


    public void setHeaders(java.util.List<SIPHeader> headers) {
        ListIterator<SIPHeader> listIterator = headers.listIterator();
        while (listIterator.hasNext()) {
            SIPHeader sipHeader = (SIPHeader) listIterator.next();
            try {
                this.attachHeader(sipHeader, false);
            } catch (SIPDuplicateHeaderException ex) {
            }
        }
    }

    public void attachHeader(SIPHeader h, boolean replaceflag) throws SIPDuplicateHeaderException {
        this.attachHeader(h, replaceflag, false);
    }


    public void attachHeader(SIPHeader header, boolean replaceFlag, boolean top)
            throws SIPDuplicateHeaderException {
        if (header == null) {
            throw new NullPointerException("null header");
        }

        SIPHeader h;

        if (ListMap.hasList(header) && !SIPHeaderList.class.isAssignableFrom(header.getClass())) {
            SIPHeaderList<SIPHeader> hdrList = ListMap.getList(header);
            hdrList.add(header);
            h = hdrList;
        } else {
            h = header;
        }

        String headerNameLowerCase = SIPHeaderNamesCache.toLowerCase(h.getName());
        if (replaceFlag) {
            headerTable.remove(headerNameLowerCase);
        } else if (headerTable.containsKey(headerNameLowerCase) && !(h instanceof SIPHeaderList)) {
            if (h instanceof ContentLength) {
                try {
                    ContentLength cl = (ContentLength) h;
                    contentLengthHeader.setContentLength(cl.getContentLength());
                } catch (InvalidArgumentException e) {
                }
            }
            return;
        }

        SIPHeader originalHeader = (SIPHeader) getHeader(header.getName());

        if (originalHeader != null) {
            Iterator<SIPHeader> li = headers.iterator();
            while (li.hasNext()) {
                SIPHeader next = (SIPHeader) li.next();
                if (next.equals(originalHeader)) {
                    li.remove();
                }
            }
        }

        if (!headerTable.containsKey(headerNameLowerCase)) {
            headerTable.put(headerNameLowerCase, h);
            headers.add(h);
        } else {
            if (h instanceof SIPHeaderList) {
                SIPHeaderList<?> hdrlist = (SIPHeaderList<?>) headerTable
                        .get(headerNameLowerCase);
                if (hdrlist != null)
                    hdrlist.concatenate((SIPHeaderList) h, top);
                else
                    headerTable.put(headerNameLowerCase, h);
            } else {
                headerTable.put(headerNameLowerCase, h);
            }
        }

        if (h instanceof From) {
            this.fromHeader = (From) h;
        } else if (h instanceof ContentLength) {
            this.contentLengthHeader = (ContentLength) h;
        } else if (h instanceof To) {
            this.toHeader = (To) h;
        } else if (h instanceof CSeq) {
            this.cSeqHeader = (CSeq) h;
        } else if (h instanceof CallID) {
            this.callIdHeader = (CallID) h;
        } else if (h instanceof MaxForwards) {
            this.maxForwardsHeader = (MaxForwards) h;
        }

    }

    public void removeHeader(String headerName, boolean top) {

        String headerNameLowerCase = SIPHeaderNamesCache.toLowerCase(headerName);
        SIPHeader toRemove = (SIPHeader) headerTable.get(headerNameLowerCase);
        // nothing to do then we are done.
        if (toRemove == null)
            return;
        if (toRemove instanceof SIPHeaderList) {
            SIPHeaderList<?> hdrList = (SIPHeaderList<?>) toRemove;
            if (top)
                hdrList.removeFirst();
            else
                hdrList.removeLast();
            if (hdrList.isEmpty()) {
                Iterator<SIPHeader> li = this.headers.iterator();
                while (li.hasNext()) {
                    SIPHeader sipHeader = (SIPHeader) li.next();
                    if (sipHeader.getName().equalsIgnoreCase(headerNameLowerCase))
                        li.remove();
                }

                headerTable.remove(headerNameLowerCase);
            }
        } else {
            this.headerTable.remove(headerNameLowerCase);
            if (toRemove instanceof From) {
                this.fromHeader = null;
            } else if (toRemove instanceof To) {
                this.toHeader = null;
            } else if (toRemove instanceof CSeq) {
                this.cSeqHeader = null;
            } else if (toRemove instanceof CallID) {
                this.callIdHeader = null;
            } else if (toRemove instanceof MaxForwards) {
                this.maxForwardsHeader = null;
            } else if (toRemove instanceof ContentLength) {
                this.contentLengthHeader = null;
            }
            Iterator<SIPHeader> li = this.headers.iterator();
            while (li.hasNext()) {
                SIPHeader sipHeader = (SIPHeader) li.next();
                if (sipHeader.getName().equalsIgnoreCase(headerName))
                    li.remove();
            }
        }

    }


    public void removeHeader(String headerName) {

        if (headerName == null)
            throw new NullPointerException("null arg");
        String headerNameLowerCase = SIPHeaderNamesCache.toLowerCase(headerName);
        SIPHeader removed = (SIPHeader) headerTable.remove(headerNameLowerCase);
        if (removed == null)
            return;
        if (removed instanceof From) {
            this.fromHeader = null;
        } else if (removed instanceof To) {
            this.toHeader = null;
        } else if (removed instanceof CSeq) {
            this.cSeqHeader = null;
        } else if (removed instanceof CallID) {
            this.callIdHeader = null;
        } else if (removed instanceof MaxForwards) {
            this.maxForwardsHeader = null;
        } else if (removed instanceof ContentLength) {
            this.contentLengthHeader = null;
        }

        Iterator<SIPHeader> li = this.headers.iterator();
        while (li.hasNext()) {
            SIPHeader sipHeader = (SIPHeader) li.next();
            if (sipHeader.getName().equalsIgnoreCase(headerNameLowerCase))
                li.remove();

        }
    }


    public String getTransactionId() {
        Via topVia = getTopmostVia();

        if (topVia != null
                && topVia.getBranch() != null
                && topVia.getBranch().toUpperCase().startsWith(
                SIPConstants.BRANCH_MAGIC_COOKIE_UPPER_CASE)) {
            if (this.getCSeq().getMethod().equals(Request.CANCEL))
                return (topVia.getBranch() + ":" + this.getCSeq().getMethod()).toLowerCase();
            else
                return topVia.getBranch().toLowerCase();
        } else {
            StringBuilder retval = new StringBuilder();
            From from = (From) this.getFrom();
            To to = (To) this.getTo();
            if (from.hasTag())
                retval.append(from.getTag()).append("-");
            String cid = this.callIdHeader.getCallId();
            retval.append(cid).append("-");
            retval.append(this.cSeqHeader.getSequenceNumber()).append("-").append(
                    this.cSeqHeader.getMethod());
            if (topVia != null) {
                retval.append("-").append(topVia.getSentBy().encode());
                if (!topVia.getSentBy().hasPort()) {
                    retval.append("-").append(5060);
                }
            }
            if (this.getCSeq().getMethod().equals(Request.CANCEL)) {
                retval.append(Request.CANCEL);
            }
            return retval.toString().toLowerCase().replace(":", "-").replace("@", "-")
                    + IdUtil.fastSimpleUUID();
        }
    }

    public int hashCode() {
        if (this.callIdHeader == null)
            throw new RuntimeException("Invalid message! Cannot compute hashcode! call-id header is missing !");
        else
            return this.callIdHeader.getCallId().hashCode();
    }


    public boolean hasContent() {
        return messageContent != null || messageContentBytes != null;
    }


    public Iterator<SIPHeader> getHeaders() {
        return headers.iterator();
    }


    public Header getHeader(String headerName) {
        return getHeaderLowerCase(SIPHeaderNamesCache.toLowerCase(headerName));
    }

    protected Header getHeaderLowerCase(String lowerCaseHeaderName) {
        if (lowerCaseHeaderName == null)
            throw new NullPointerException("bad name");
        SIPHeader sipHeader = (SIPHeader) headerTable.get(lowerCaseHeaderName);
        if (sipHeader instanceof SIPHeaderList)
            return (Header) ((SIPHeaderList) sipHeader).getFirst();
        else
            return (Header) sipHeader;
    }

    public ContentType getContentTypeHeader() {
        return (ContentType) getHeaderLowerCase(CONTENT_TYPE_LOWERCASE);
    }

    private static final String CONTENT_TYPE_LOWERCASE = SIPHeaderNamesCache.toLowerCase(ContentTypeHeader.NAME);


    public ContentLengthHeader getContentLengthHeader() {
        return this.getContentLength();
    }


    public FromHeader getFrom() {
        return (FromHeader) fromHeader;
    }

    public ErrorInfoList getErrorInfoHeaders() {
        return (ErrorInfoList) getSIPHeaderListLowerCase(ERROR_LOWERCASE);
    }

    private static final String ERROR_LOWERCASE = SIPHeaderNamesCache.toLowerCase(ErrorInfo.NAME);


    public ContactList getContactHeaders() {
        return (ContactList) this.getSIPHeaderListLowerCase(CONTACT_LOWERCASE);
    }

    private static final String CONTACT_LOWERCASE = SIPHeaderNamesCache.toLowerCase(ContactHeader.NAME);

    public Contact getContactHeader() {
        ContactList clist = this.getContactHeaders();
        if (clist != null) {
            return (Contact) clist.getFirst();

        } else {
            return null;
        }
    }


    public ViaList getViaHeaders() {
        return (ViaList) getSIPHeaderListLowerCase(VIA_LOWERCASE);
    }


    private static final String EVENT_LOWERCASE = SIPHeaderNamesCache.toLowerCase(EventHeader.NAME);


    public EventHeader getEventHeader() {
        return (EventHeader) getHeaderLowerCase(EVENT_LOWERCASE);
    }

    private static final String VIA_LOWERCASE = SIPHeaderNamesCache.toLowerCase(ViaHeader.NAME);


    public void setVia(List viaList) {
        ViaList vList = new ViaList();
        ListIterator it = viaList.listIterator();
        while (it.hasNext()) {
            Via via = (Via) it.next();
            vList.add(via);
        }
        this.setHeader(vList);
    }

    public void setHeader(SIPHeaderList<Via> sipHeaderList) {
        this.setHeader((Header) sipHeaderList);
    }


    public Via getTopmostVia() {
        if (this.getViaHeaders() == null)
            return null;
        else
            return (Via) (getViaHeaders().getFirst());
    }

    public CSeqHeader getCSeq() {
        return (CSeqHeader) cSeqHeader;
    }


    public Authorization getAuthorization() {
        return (Authorization) getHeaderLowerCase(AUTHORIZATION_LOWERCASE);
    }

    private static final String AUTHORIZATION_LOWERCASE = SIPHeaderNamesCache.toLowerCase(AuthorizationHeader.NAME);


    public MaxForwardsHeader getMaxForwards() {
        return maxForwardsHeader;
    }


    public void setMaxForwards(MaxForwardsHeader maxForwards) {
        this.setHeader(maxForwards);
    }


    public RouteList getRouteHeaders() {
        return (RouteList) getSIPHeaderListLowerCase(ROUTE_LOWERCASE);
    }

    private static final String ROUTE_LOWERCASE = SIPHeaderNamesCache
            .toLowerCase(RouteHeader.NAME);


    public CallIdHeader getCallId() {
        return callIdHeader;
    }


    public void setCallId(CallIdHeader callId) {
        this.setHeader(callId);
    }


    public void setCallId(String callId) throws ParseException {
        if (callIdHeader == null) {
            this.setHeader(new CallID());
        }
        callIdHeader.setCallId(callId);
    }


    public RecordRouteList getRecordRouteHeaders() {
        return (RecordRouteList) this.getSIPHeaderListLowerCase(RECORDROUTE_LOWERCASE);
    }

    private static final String RECORDROUTE_LOWERCASE = SIPHeaderNamesCache.toLowerCase(RecordRouteHeader.NAME);


    public ToHeader getTo() {
        return (ToHeader) toHeader;
    }

    public void setTo(ToHeader to) {
        this.setHeader(to);
    }

    public void setFrom(FromHeader from) {
        this.setHeader(from);

    }

    public ContentLengthHeader getContentLength() {
        return this.contentLengthHeader;
    }


    public SDPMessage getMessageContent() throws UnsupportedEncodingException {
        if (this.messageContent == null && this.messageContentBytes == null)
            return null;
        else if (this.messageContent == null) {
            this.messageContent = new String(messageContentBytes, getCharset());
        }
        return SDPParser.parse(this.messageContent);
    }


    public byte[] getRawContent() {
        try {
            if (this.messageContentBytes != null) {
                 return messageContentBytes;
            } else if (this.messageContentObject != null) {
                String messageContent = this.messageContentObject.toString();
                this.messageContentBytes = messageContent.getBytes(getCharset());
            } else if (this.messageContent != null) {
                this.messageContentBytes = messageContent.getBytes(getCharset());
            }
            return this.messageContentBytes;
        } catch (UnsupportedEncodingException ex) {
            log.error("UnsupportedEncodingException {}", ex.getMessage(), ex);
            return null;
        }
    }


    public void setMessageContent(String type, String subType, String messageContent) {
        if (messageContent == null)
            throw new IllegalArgumentException("messgeContent is null");
        ContentType ct = new ContentType(type, subType);
        this.setHeader(ct);
        this.messageContent = messageContent;
        this.messageContentBytes = null;
        this.messageContentObject = null;
        computeContentLength(messageContent);
    }


    public void setContent(Object content, ContentTypeHeader contentTypeHeader)
            throws ParseException {
        if (content == null)
            throw new NullPointerException("null content");
        this.setHeader(contentTypeHeader);

        this.messageContent = null;
        this.messageContentBytes = null;
        this.messageContentObject = null;

        if (content instanceof String) {
            this.messageContent = (String) content;
        } else if (content instanceof byte[]) {
            this.messageContentBytes = (byte[]) content;
        } else
            this.messageContentObject = content;

        computeContentLength(content);
    }


    public Object getContent() {
        if (this.messageContentObject != null)
            return messageContentObject;
        else if (this.messageContent != null)
            return this.messageContent;
        else if (this.messageContentBytes != null)
            return this.messageContentBytes;
        else
            return null;
    }


    public void setMessageContent(String type, String subType, byte[] messageContent) {
        ContentType ct = new ContentType(type, subType);
        this.setHeader(ct);
        this.setMessageContent(messageContent);

        computeContentLength(messageContent);
    }


    public void setMessageContent(byte[] content, boolean strict, boolean computeContentLength, int givenLength)
            throws ParseException {
        computeContentLength(content);
        if ((!computeContentLength)) {
            if ((!strict && this.contentLengthHeader.getContentLength() != givenLength)
                    || this.contentLengthHeader.getContentLength() < givenLength) {
                throw new ParseException("Invalid content length "
                        + this.contentLengthHeader.getContentLength() + " / " + givenLength, 0);
            }
        }
        messageContent = null;
        messageContentBytes = content;
        messageContentObject = null;
    }


    public void setMessageContent(byte[] content) {
        computeContentLength(content);

        messageContentBytes = content;
        messageContent = null;
        messageContentObject = null;
    }


    public void setMessageContent(byte[] content, boolean computeContentLength, int givenLength)
            throws ParseException {
        computeContentLength(content);
        if ((!computeContentLength) && this.contentLengthHeader.getContentLength() < givenLength) {
            throw new ParseException("Invalid content length " + this.contentLengthHeader.getContentLength() + " / " + givenLength, 0);
        }
        messageContentBytes = content;
        messageContent = null;
        messageContentObject = null;
    }


    private void computeContentLength(Object content) {
        int length = 0;
        if (content != null) {
            if (content instanceof String) {
                try {
                    length = ((String) content).getBytes(getCharset()).length;
                } catch (UnsupportedEncodingException ex) {
                    log.error("UnsupportedEncodingException {}", ex.getMessage(), ex);
                }
            } else if (content instanceof byte[]) {
                length = ((byte[]) content).length;
            } else {
                length = content.toString().length();
            }
        }

        try {
            contentLengthHeader.setContentLength(length);
        } catch (InvalidArgumentException e) {
        }
    }


    public void removeContent() {
        messageContent = null;
        messageContentBytes = null;
        messageContentObject = null;
        try {
            this.contentLengthHeader.setContentLength(0);
        } catch (InvalidArgumentException ex) {
        }
    }


    @SuppressWarnings("unchecked")
    public ListIterator<SIPHeader> getHeaders(String headerName) {
        if (headerName == null)
            throw new NullPointerException("null headerName");
        SIPHeader sipHeader = (SIPHeader) headerTable.get(SIPHeaderNamesCache
                .toLowerCase(headerName));
        if (sipHeader == null)
            return new LinkedList<SIPHeader>().listIterator();
        if (sipHeader instanceof SIPHeaderList) {
            return ((SIPHeaderList<SIPHeader>) sipHeader).listIterator();
        } else {
            return new HeaderIterator(this, sipHeader);
        }
    }


    public String getHeaderAsFormattedString(String name) {
        String lowerCaseName = SIPHeaderNamesCache.toLowerCase(name);
        if (this.headerTable.containsKey(lowerCaseName)) {
            return this.headerTable.get(lowerCaseName).toString();
        } else {
            return this.getHeader(name).toString();
        }
    }

    public SIPHeader getSIPHeaderListLowerCase(String lowerCaseHeaderName) {
        return headerTable.get(lowerCaseHeaderName);
    }


    @SuppressWarnings("unchecked")
    private List<SIPHeader> getHeaderList(String headerName) {
        SIPHeader sipHeader = (SIPHeader) headerTable.get(SIPHeaderNamesCache
                .toLowerCase(headerName));
        if (sipHeader == null)
            return null;
        else if (sipHeader instanceof SIPHeaderList)
            return (List<SIPHeader>) (((SIPHeaderList<?>) sipHeader).getHeaderList());
        else {
            LinkedList<SIPHeader> ll = new LinkedList<SIPHeader>();
            ll.add(sipHeader);
            return ll;
        }
    }


    public boolean hasHeader(String headerName) {
        return headerTable.containsKey(SIPHeaderNamesCache.toLowerCase(headerName));
    }


    public boolean hasFromTag() {
        return fromHeader != null && fromHeader.getTag() != null;
    }


    public boolean hasToTag() {
        return toHeader != null && toHeader.getTag() != null;
    }


    public String getFromTag() {
        return fromHeader == null ? null : fromHeader.getTag();
    }


    public void setFromTag(String tag) {
        try {
            fromHeader.setTag(tag);
        } catch (ParseException e) {
        }
    }


    public void setToTag(String tag) {
        try {
            toHeader.setTag(tag);
        } catch (ParseException e) {
        }
    }


    public String getToTag() {
        return toHeader == null ? null : toHeader.getTag();
    }


    public abstract String getFirstLine();


    public void addHeader(Header sipHeader) {
        SIPHeader sh = (SIPHeader) sipHeader;
        try {
            if ((sipHeader instanceof ViaHeader) || (sipHeader instanceof RecordRouteHeader)) {
                attachHeader(sh, false, true);
            } else {
                attachHeader(sh, false, false);
            }
        } catch (SIPDuplicateHeaderException ex) {
            try {
                if (sipHeader instanceof ContentLength) {
                    ContentLength cl = (ContentLength) sipHeader;
                    contentLengthHeader.setContentLength(cl.getContentLength());
                }
            } catch (InvalidArgumentException e) {
            }
        }
    }

    public void addUnparsed(String unparsed) {
        this.getUnrecognizedHeadersList().add(unparsed);
    }


    public void addHeader(String sipHeader) {
        try {
            HeaderParser parser = ParserFactory.createParser(sipHeader + "\n");
            SIPHeader sh = parser.parse();
            this.attachHeader(sh, false);
        } catch (ParseException ex) {
            this.getUnrecognizedHeadersList().add(sipHeader);
        }
    }


    public ListIterator<String> getUnrecognizedHeaders() {
        return this.getUnrecognizedHeadersList().listIterator();
    }


    public ListIterator<String> getHeaderNames() {
        Iterator<SIPHeader> li = this.headers.iterator();
        LinkedList<String> retval = new LinkedList<String>();
        while (li.hasNext()) {
            SIPHeader sipHeader = (SIPHeader) li.next();
            String name = sipHeader.getName();
            retval.add(name);
        }
        return retval.listIterator();
    }


    public boolean equals(Object other) {
        if (!other.getClass().equals(this.getClass())) {
            return false;
        }
        SipMessage otherMessage = (SipMessage) other;
        Collection<SIPHeader> values = this.headerTable.values();
        Iterator<SIPHeader> it = values.iterator();
        if (headerTable.size() != otherMessage.headerTable.size()) {
            return false;
        }

        while (it.hasNext()) {
            SIPHeader mine = (SIPHeader) it.next();
            SIPHeader his = (SIPHeader) (otherMessage.headerTable.get(SIPHeaderNamesCache
                    .toLowerCase(mine.getName())));
            if (his == null) {
                return false;
            } else if (!his.equals(mine)) {
                return false;
            }
        }
        return true;
    }


    public ContentDispositionHeader getContentDisposition() {
        return (ContentDispositionHeader) getHeaderLowerCase(CONTENT_DISPOSITION_LOWERCASE);
    }

    private static final String CONTENT_DISPOSITION_LOWERCASE = SIPHeaderNamesCache
            .toLowerCase(ContentDispositionHeader.NAME);


    public ContentEncodingHeader getContentEncoding() {
        return (ContentEncodingHeader) getHeaderLowerCase(CONTENT_ENCODING_LOWERCASE);
    }

    private static final String CONTENT_ENCODING_LOWERCASE = SIPHeaderNamesCache
            .toLowerCase(ContentEncodingHeader.NAME);


    public ContentLanguageHeader getContentLanguage() {
        return (ContentLanguageHeader) getHeaderLowerCase(CONTENT_LANGUAGE_LOWERCASE);
    }

    private static final String CONTENT_LANGUAGE_LOWERCASE = SIPHeaderNamesCache
            .toLowerCase(ContentLanguageHeader.NAME);


    public ExpiresHeader getExpires() {
        return (ExpiresHeader) getHeaderLowerCase(EXPIRES_LOWERCASE);
    }

    private static final String EXPIRES_LOWERCASE = SIPHeaderNamesCache
            .toLowerCase(ExpiresHeader.NAME);


    public void setExpires(ExpiresHeader expiresHeader) {
        this.setHeader(expiresHeader);
    }


    public void setContentDisposition(ContentDispositionHeader contentDispositionHeader) {
        this.setHeader(contentDispositionHeader);

    }

    public void setContentEncoding(ContentEncodingHeader contentEncodingHeader) {
        this.setHeader(contentEncodingHeader);

    }

    public void setContentLanguage(ContentLanguageHeader contentLanguageHeader) {
        this.setHeader(contentLanguageHeader);
    }


    public void setContentLength(ContentLengthHeader contentLength) {
        try {
            this.contentLengthHeader.setContentLength(contentLength.getContentLength());
        } catch (InvalidArgumentException ex) {
        }
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getSize() {
        return this.size;
    }


    public void addLast(Header header) throws SipException, NullPointerException {
        if (header == null)
            throw new NullPointerException("null arg!");

        try {
            this.attachHeader((SIPHeader) header, false, false);
        } catch (SIPDuplicateHeaderException ex) {
            throw new SipException("Cannot add header - header already exists");
        }

    }


    public void addFirst(Header header) throws SipException, NullPointerException {

        if (header == null)
            throw new NullPointerException("null arg!");

        try {
            this.attachHeader((SIPHeader) header, false, true);
        } catch (SIPDuplicateHeaderException ex) {
            throw new SipException("Cannot add header - header already exists");
        }

    }


    public void removeFirst(String headerName) throws NullPointerException {
        if (headerName == null)
            throw new NullPointerException("Null argument Provided!");
        this.removeHeader(headerName, true);

    }


    public void removeLast(String headerName) {
        if (headerName == null)
            throw new NullPointerException("Null argument Provided!");
        this.removeHeader(headerName, false);

    }


    public void setCSeq(CSeqHeader cseqHeader) {
        this.setHeader(cseqHeader);
    }


    public void setApplicationData(Object applicationData) {
        this.applicationData = applicationData;
    }


    public MultipartMimeContent getMultipartMimeContent() throws ParseException {
        if (this.contentLengthHeader.getContentLength() == 0) {
            return null;
        }
        MultipartMimeContentImpl retval = new MultipartMimeContentImpl(this
                .getContentTypeHeader());
        byte[] rawContent = getRawContent();
        try {
            String body = new String(rawContent, getCharset());
            retval.createContentList(body);
            return retval;
        } catch (UnsupportedEncodingException e) {
            log.error("Get MultipartMimeContent error:{}", e.getMessage(), e);
            return null;
        }
    }

    public CallIdHeader getCallIdHeader() {
        return this.callIdHeader;
    }


    public FromHeader getFromHeader() {
        return this.fromHeader;
    }


    public ToHeader getToHeader() {
        return this.toHeader;
    }


    public ViaHeader getTopmostViaHeader() {
        return this.getTopmostVia();
    }

    public CSeqHeader getCSeqHeader() {
        return this.cSeqHeader;
    }


    protected final String getCharset() {
        ContentType ct = getContentTypeHeader();
        String contentEncodingCharset = "UTF-8";
        if (ct != null) {
            String c = ct.getCharset();
            return c != null ? c : contentEncodingCharset;
        } else return contentEncodingCharset;
    }


    public void setNullRequest() {
        this.nullRequest = true;
    }

    public String getForkId() {
        if (this.forkId != null) {
            return forkId;
        } else {
            String callId = this.getCallId().getCallId();
            String fromTag = this.getFromTag();
            if (fromTag == null) {
                throw new IllegalStateException("From tag is not yet set. Cannot compute forkId");
            }
            this.forkId = (callId + ":" + fromTag).toLowerCase();
            return this.forkId;
        }
    }

    public abstract void setSIPVersion(String sipVersion) throws ParseException;

    public abstract String getSIPVersion();

    public abstract String toString();

    public void cleanUp() {
    }


    protected void setUnrecognizedHeadersList(LinkedList<String> unrecognizedHeaders) {
        this.unrecognizedHeaders = unrecognizedHeaders;
    }

    protected LinkedList<String> getUnrecognizedHeadersList() {
        if (unrecognizedHeaders == null) {
            unrecognizedHeaders = new LinkedList<String>();
        }
        return unrecognizedHeaders;
    }

    public void setRemoteAddress(InetAddress remoteAddress) {
        this.remoteAddress = remoteAddress;
    }

    public void setRemotePort(int remotePort) {
        this.remotePort = remotePort;
    }

    public void setLocalAddress(InetAddress localAddress) {
        this.localAddress = localAddress;
    }

    public void setLocalPort(int localPort) {
        this.localPort = localPort;
    }

    public void setPeerPacketSourceAddress(InetAddress peerPacketSourceAddress) {
        this.peerPacketSourceAddress = peerPacketSourceAddress;
    }

    public void setPeerPacketSourcePort(int peerPacketSourcePort) {
        this.peerPacketSourcePort = peerPacketSourcePort;
    }

}
