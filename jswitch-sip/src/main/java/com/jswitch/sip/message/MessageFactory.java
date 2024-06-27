package com.jswitch.sip.message;


import com.jswitch.sip.*;
import com.jswitch.sip.header.*;

import java.text.ParseException;
import java.util.List;

public interface MessageFactory {

    public SipRequest createRequest(URI requestURI, String method, CallIdHeader
            callId, CSeqHeader cSeq, FromHeader from, ToHeader to, List via,
                                 MaxForwardsHeader maxForwards, ContentTypeHeader contentType,
                                 Object content) throws ParseException;


    public SipRequest createRequest(URI requestURI, String method, CallIdHeader
            callId, CSeqHeader cSeq, FromHeader from, ToHeader to, List via,
                                 MaxForwardsHeader maxForwards, ContentTypeHeader contentType,
                                 byte[] content) throws ParseException;


    public SipRequest createRequest(URI requestURI, String method, CallIdHeader
            callId, CSeqHeader cSeq, FromHeader from, ToHeader to, List via,
                                 MaxForwardsHeader maxForwards) throws ParseException;


    public SipRequest createRequest(String request) throws ParseException;


    public SipResponse createResponse(int statusCode, CallIdHeader callId,
                                   CSeqHeader cSeq, FromHeader from, ToHeader to, List via,
                                   MaxForwardsHeader maxForwards, ContentTypeHeader contentType,
                                   Object content) throws ParseException;


    public SipResponse createResponse(int statusCode, CallIdHeader callId,
                                   CSeqHeader cSeq, FromHeader from, ToHeader to, List via,
                                   MaxForwardsHeader maxForwards, ContentTypeHeader contentType,
                                   byte[] content) throws ParseException;

    public SipResponse createResponse(int statusCode, CallIdHeader callId,
                                   CSeqHeader cSeq, FromHeader from, ToHeader to, List via,
                                   MaxForwardsHeader maxForwards) throws ParseException;


    public SipResponse createResponse(int statusCode, Request request,
                                   ContentTypeHeader contentType, Object content) throws ParseException;


    public SipResponse createResponse(int statusCode, Request request,
                                   ContentTypeHeader contentType, byte[] content) throws ParseException;


    public SipResponse createResponse(int statusCode, Request request)
            throws ParseException;


    public SipResponse createResponse(String response) throws ParseException;

    public void setDefaultUserAgentHeader(UserAgentHeader userAgent);


    public void setDefaultServerHeader(ServerHeader userAgent);


    public void setDefaultContentEncodingCharset(String charset) throws NullPointerException, IllegalArgumentException;

    public MultipartMimeContent createMultipartMimeContent(ContentTypeHeader multipartMimeContentTypeHeader, String[] contentType, String[] contentSubtype, String[] contentBody);

}

