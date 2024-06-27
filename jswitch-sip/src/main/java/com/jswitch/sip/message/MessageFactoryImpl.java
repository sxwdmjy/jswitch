package com.jswitch.sip.message;


import com.jswitch.sip.*;
import com.jswitch.sip.header.*;
import com.jswitch.sip.parse.ParseExceptionListener;
import com.jswitch.sip.parse.StringMsgParser;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Service
public class MessageFactoryImpl implements MessageFactory {

    private boolean testing = false;

    private boolean strict = true;

    private static String defaultContentEncodingCharset = "UTF-8";


    private static UserAgentHeader userAgent;


    private static ServerHeader server;


    public void setStrict(boolean strict) {
        this.strict = strict;
    }


    public void setTest(boolean flag) {
        this.testing = flag;
    }


    public MessageFactoryImpl() {
    }


    public SipRequest createRequest(URI requestURI,
                                 String method, CallIdHeader callId, CSeqHeader cSeq,
                                 FromHeader from, ToHeader to, List via,
                                 MaxForwardsHeader maxForwards, ContentTypeHeader contentType,
                                 Object content) throws ParseException {
        if (requestURI == null || method == null || callId == null
                || cSeq == null || from == null || to == null || via == null
                || maxForwards == null || content == null
                || contentType == null)
            throw new NullPointerException("Null parameters");

        SipRequest sipRequest = new SipRequest();
        sipRequest.setRequestURI(requestURI);
        sipRequest.setMethod(method);
        sipRequest.setCallId(callId);
        sipRequest.setCSeq(cSeq);
        sipRequest.setFrom(from);
        sipRequest.setTo(to);
        sipRequest.setVia(via);
        sipRequest.setMaxForwards(maxForwards);
        sipRequest.setContent(content, contentType);
        if (userAgent != null) {
            sipRequest.setHeader(userAgent);
        }
        return sipRequest;
    }

    public SipRequest createRequest(URI requestURI, String method,
                                 CallIdHeader callId, CSeqHeader cSeq, FromHeader from, ToHeader to,
                                 List via, MaxForwardsHeader maxForwards, byte[] content,
                                 ContentTypeHeader contentType) throws ParseException {
        if (requestURI == null || method == null || callId == null
                || cSeq == null || from == null || to == null || via == null
                || maxForwards == null || content == null
                || contentType == null)
            throw new ParseException("SIP Exception, some parameters are missing, unable to create the request", 0);

        SipRequest sipRequest = new SipRequest();
        sipRequest.setRequestURI(requestURI);
        sipRequest.setMethod(method);
        sipRequest.setCallId(callId);
        sipRequest.setCSeq(cSeq);
        sipRequest.setFrom(from);
        sipRequest.setTo(to);
        sipRequest.setVia(via);
        sipRequest.setMaxForwards(maxForwards);
        sipRequest.setHeader((ContentType) contentType);
        sipRequest.setMessageContent(content);
        if (userAgent != null) {
            sipRequest.setHeader(userAgent);
        }
        return sipRequest;
    }


    public SipRequest createRequest(URI requestURI, String method,
                                 CallIdHeader callId, CSeqHeader cSeq, FromHeader from, ToHeader to,
                                 List via, MaxForwardsHeader maxForwards) throws ParseException {
        if (requestURI == null || method == null || callId == null
                || cSeq == null || from == null || to == null || via == null
                || maxForwards == null)
            throw new ParseException("SIP Exception, some parameters are missing, unable to create the request", 0);

        SipRequest sipRequest = new SipRequest();
        sipRequest.setRequestURI(requestURI);
        sipRequest.setMethod(method);
        sipRequest.setCallId(callId);
        sipRequest.setCSeq(cSeq);
        sipRequest.setFrom(from);
        sipRequest.setTo(to);
        sipRequest.setVia(via);
        sipRequest.setMaxForwards(maxForwards);
        if (userAgent != null) {
            sipRequest.setHeader(userAgent);
        }

        return sipRequest;
    }


    public SipResponse createResponse(int statusCode, CallIdHeader callId,
                                   CSeqHeader cSeq, FromHeader from, ToHeader to, List via,
                                   MaxForwardsHeader maxForwards, Object content,
                                   ContentTypeHeader contentType) throws ParseException {
        if (callId == null || cSeq == null || from == null || to == null
                || via == null || maxForwards == null || content == null
                || contentType == null)
            throw new NullPointerException(" unable to create the response");

        SipResponse sipResponse = new SipResponse();
        StatusLine statusLine = new StatusLine();
        statusLine.setStatusCode(statusCode);
        String reasonPhrase = SipResponse.getReasonPhrase(statusCode);
        statusLine.setReasonPhrase(reasonPhrase);
        sipResponse.setStatusLine(statusLine);
        sipResponse.setCallId(callId);
        sipResponse.setCSeq(cSeq);
        sipResponse.setFrom(from);
        sipResponse.setTo(to);
        sipResponse.setVia(via);
        sipResponse.setMaxForwards(maxForwards);
        sipResponse.setContent(content, contentType);
        if (userAgent != null) {
            sipResponse.setHeader(userAgent);
        }
        return sipResponse;
    }

    public Response createResponse(int statusCode, CallIdHeader callId,
                                   CSeqHeader cSeq, FromHeader from, ToHeader to, List via,
                                   MaxForwardsHeader maxForwards, byte[] content,
                                   ContentTypeHeader contentType) throws ParseException {
        if (callId == null || cSeq == null || from == null || to == null
                || via == null || maxForwards == null || content == null
                || contentType == null)
            throw new NullPointerException("Null params ");

        SipResponse sipResponse = new SipResponse();
        sipResponse.setStatusCode(statusCode);
        sipResponse.setCallId(callId);
        sipResponse.setCSeq(cSeq);
        sipResponse.setFrom(from);
        sipResponse.setTo(to);
        sipResponse.setVia(via);
        sipResponse.setMaxForwards(maxForwards);
        sipResponse.setHeader((ContentType) contentType);
        sipResponse.setMessageContent(content);
        if (userAgent != null) {
            sipResponse.setHeader(userAgent);
        }
        return sipResponse;
    }

    public SipResponse createResponse(int statusCode, CallIdHeader callId,
                                   CSeqHeader cSeq, FromHeader from, ToHeader to, List via,
                                   MaxForwardsHeader maxForwards) throws ParseException {
        if (callId == null || cSeq == null || from == null || to == null
                || via == null || maxForwards == null)
            throw new ParseException("SIP Exception, some parameters are missing, unable to create the response", 0);

        SipResponse sipResponse = new SipResponse();
        sipResponse.setStatusCode(statusCode);
        sipResponse.setCallId(callId);
        sipResponse.setCSeq(cSeq);
        sipResponse.setFrom(from);
        sipResponse.setTo(to);
        sipResponse.setVia(via);
        sipResponse.setMaxForwards(maxForwards);
        if (userAgent != null) {
            sipResponse.setHeader(userAgent);
        }
        return sipResponse;
    }


    public SipResponse createResponse(int statusCode, Request request,
                                   ContentTypeHeader contentType, Object content)
            throws ParseException {
        if (request == null || content == null || contentType == null)
            throw new NullPointerException("null parameters");

        SipRequest sipRequest = (SipRequest) request;
        SipResponse sipResponse = sipRequest.createResponse(statusCode);
        sipResponse.setContent(content, contentType);
        if (server != null) {
            sipResponse.setHeader(server);
        }
        return sipResponse;
    }


    public SipResponse createResponse(int statusCode, Request request,
                                   ContentTypeHeader contentType, byte[] content)
            throws ParseException {
        if (request == null || content == null || contentType == null)
            throw new NullPointerException("null Parameters");

        SipRequest sipRequest = (SipRequest) request;
        SipResponse sipResponse = sipRequest.createResponse(statusCode);
        sipResponse.setHeader((ContentType) contentType);
        sipResponse.setMessageContent(content);
        if (server != null) {
            sipResponse.setHeader(server);
        }
        return sipResponse;
    }

    public SipResponse createResponse(int statusCode, Request request)
            throws ParseException {
        if (request == null)
            throw new NullPointerException("null parameters");
        SipRequest sipRequest = (SipRequest) request;
        SipResponse sipResponse = sipRequest.createResponse(statusCode);
        sipResponse.removeContent();
        sipResponse.removeHeader(ContentTypeHeader.NAME);
        if (server != null) {
            sipResponse.setHeader(server);
        }
        return sipResponse;
    }


    public SipRequest createRequest(URI requestURI,
                                 String method, CallIdHeader callId, CSeqHeader cSeq,
                                 FromHeader from, ToHeader to, List via,
                                 MaxForwardsHeader maxForwards, ContentTypeHeader contentType,
                                 byte[] content) throws ParseException {
        if (requestURI == null || method == null || callId == null
                || cSeq == null || from == null || to == null || via == null
                || maxForwards == null || content == null
                || contentType == null)
            throw new NullPointerException("missing parameters");

        SipRequest sipRequest = new SipRequest();
        sipRequest.setRequestURI(requestURI);
        sipRequest.setMethod(method);
        sipRequest.setCallId(callId);
        sipRequest.setCSeq(cSeq);
        sipRequest.setFrom(from);
        sipRequest.setTo(to);
        sipRequest.setVia(via);
        sipRequest.setMaxForwards(maxForwards);
        sipRequest.setContent(content, contentType);
        if (userAgent != null) {
            sipRequest.setHeader(userAgent);
        }
        return sipRequest;
    }


    public SipResponse createResponse(int statusCode, CallIdHeader callId,
                                   CSeqHeader cSeq, FromHeader from, ToHeader to, List via,
                                   MaxForwardsHeader maxForwards, ContentTypeHeader contentType,
                                   Object content) throws ParseException {
        if (callId == null || cSeq == null || from == null || to == null
                || via == null || maxForwards == null || content == null
                || contentType == null)
            throw new NullPointerException("missing parameters");
        SipResponse sipResponse = new SipResponse();
        StatusLine statusLine = new StatusLine();
        statusLine.setStatusCode(statusCode);
        String reason = SipResponse.getReasonPhrase(statusCode);
        if (reason == null)
            throw new ParseException(statusCode + " Unknown", 0);
        statusLine.setReasonPhrase(reason);
        sipResponse.setStatusLine(statusLine);
        sipResponse.setCallId(callId);
        sipResponse.setCSeq(cSeq);
        sipResponse.setFrom(from);
        sipResponse.setTo(to);
        sipResponse.setVia(via);
        sipResponse.setContent(content, contentType);
        if (userAgent != null) {
            sipResponse.setHeader(userAgent);
        }
        return sipResponse;

    }


    public SipResponse createResponse(int statusCode, CallIdHeader callId,
                                   CSeqHeader cSeq, FromHeader from, ToHeader to, List via,
                                   MaxForwardsHeader maxForwards, ContentTypeHeader contentType,
                                   byte[] content) throws ParseException {
        if (callId == null || cSeq == null || from == null || to == null
                || via == null || maxForwards == null || content == null
                || contentType == null)
            throw new NullPointerException("missing parameters");
        SipResponse sipResponse = new SipResponse();
        StatusLine statusLine = new StatusLine();
        statusLine.setStatusCode(statusCode);
        String reason = SipResponse.getReasonPhrase(statusCode);
        if (reason == null)
            throw new ParseException(statusCode + " : Unknown", 0);
        statusLine.setReasonPhrase(reason);
        sipResponse.setStatusLine(statusLine);
        sipResponse.setCallId(callId);
        sipResponse.setCSeq(cSeq);
        sipResponse.setFrom(from);
        sipResponse.setTo(to);
        sipResponse.setVia(via);
        sipResponse.setContent(content, contentType);
        if (userAgent != null) {
            sipResponse.setHeader(userAgent);
        }
        return sipResponse;
    }

    public SipRequest createRequest(String requestString)
            throws ParseException {
        if (requestString == null || requestString.isEmpty()) {
            SipRequest retval = new SipRequest();
            retval.setNullRequest();
            return retval;
        }

        StringMsgParser smp = new StringMsgParser();
        ParseExceptionListener parseExceptionListener = new ParseExceptionListener() {

            public void handleException(ParseException ex,
                                        SipMessage sipMessage, Class headerClass,
                                        String headerText, String messageText)
                    throws ParseException {
                if (testing) {
                    if (headerClass == From.class || headerClass == To.class
                            || headerClass == CallID.class
                            || headerClass == MaxForwards.class
                            || headerClass == Via.class
                            || headerClass == RequestLine.class
                            || headerClass == StatusLine.class
                            || headerClass == CSeq.class)
                        throw ex;

                    sipMessage.addUnparsed(headerText);
                }

            }

        };

        ParseExceptionListener exHandler = null;
        if (this.testing)
            exHandler = parseExceptionListener;

        SipMessage sipMessage = smp.parseSIPMessage(requestString.getBytes(), true, this.strict, exHandler);

        if (!(sipMessage instanceof SipRequest))
            throw new ParseException(requestString, 0);

        return (SipRequest) sipMessage;
    }


    public SipResponse createResponse(String responseString)
            throws ParseException {
        if (responseString == null)
            return new SipResponse();

        StringMsgParser smp = new StringMsgParser();

        SipMessage sipMessage = smp.parseSIPMessage(responseString.getBytes(), true, false, null);

        if (!(sipMessage instanceof SipResponse))
            throw new ParseException(responseString, 0);

        return (SipResponse) sipMessage;
    }


    public void setDefaultUserAgentHeader(UserAgentHeader userAgent) {
        MessageFactoryImpl.userAgent = userAgent;
    }


    public void setDefaultServerHeader(ServerHeader server) {
        MessageFactoryImpl.server = server;
    }


    public static UserAgentHeader getDefaultUserAgentHeader() {
        return userAgent;
    }


    public static ServerHeader getDefaultServerHeader() {
        return server;
    }


    public void setDefaultContentEncodingCharset(String charset) throws NullPointerException,
            IllegalArgumentException {
        if (charset == null) throw new NullPointerException("Null argument!");
        MessageFactoryImpl.defaultContentEncodingCharset = charset;

    }

    public static String getDefaultContentEncodingCharset() {
        return MessageFactoryImpl.defaultContentEncodingCharset;
    }


    public MultipartMimeContent createMultipartMimeContent(ContentTypeHeader multipartMimeCth,
                                                           String[] contentType,
                                                           String[] contentSubtype,
                                                           String[] contentBody) {
        String boundary = multipartMimeCth.getParameter("boundary");
        MultipartMimeContentImpl retval = new MultipartMimeContentImpl(multipartMimeCth);
        for (int i = 0; i < contentType.length; i++) {
            ContentTypeHeader cth = new ContentType(contentType[i], contentSubtype[i]);
            ContentImpl contentImpl = new ContentImpl(contentBody[i]);
            contentImpl.setContentTypeHeader(cth);
            retval.add(contentImpl);
        }
        return retval;
    }

    @PostConstruct
    public void init() {
        Server server = new Server();
        List<String> product = new ArrayList<>();
        product.add("jswitch-sip");
        try {
            server.setProduct(product);
        } catch (ParseException e) {
        }
        setDefaultServerHeader(server);
    }

}
