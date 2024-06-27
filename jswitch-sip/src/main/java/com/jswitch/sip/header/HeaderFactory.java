/**
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Unpublished - rights reserved under the Copyright Laws of the United States.
 * Copyright � 2003 Sun Microsystems, Inc. All rights reserved.
 * Copyright � 2005 BEA Systems, Inc. All rights reserved.
 * <p>
 * Use is subject to license terms.
 * <p>
 * This distribution may include materials developed by third parties.
 * <p>
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * <p>
 * Module Name   : JSIP Specification
 * File Name     : HeaderFactory.java
 * Author        : Phelim O'Doherty
 * <p>
 * HISTORY
 * Version   Date      Author              Comments
 * 1.1     08/10/2002  Phelim O'Doherty
 * 1.2     20/12/2005    Jereon Van Bemmel Added create methods for PUBLISH
 * headers
 * 1.2     20/12/2006    Phelim O'Doherty  Added new createCseqHeader with long
 * sequence number
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */
package com.jswitch.sip.header;


import com.jswitch.common.exception.InvalidArgumentException;
import com.jswitch.sip.*;
import com.jswitch.sip.adress.Address;

import java.text.ParseException;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public interface HeaderFactory {


    public AcceptEncodingHeader createAcceptEncodingHeader(String encoding)
            throws ParseException;


    public AcceptHeader createAcceptHeader(String contentType, String contentSubType)
            throws ParseException;


    public AcceptLanguageHeader createAcceptLanguageHeader(Locale language);


    public AlertInfoHeader createAlertInfoHeader(URI alertInfo);


    public AllowEventsHeader createAllowEventsHeader(String eventType)
            throws ParseException;


    public AllowHeader createAllowHeader(String method)
            throws ParseException;


    public AuthenticationInfoHeader createAuthenticationInfoHeader(String response)
            throws ParseException;


    public AuthorizationHeader createAuthorizationHeader(String scheme)
            throws ParseException;


    public CSeqHeader createCSeqHeader(int sequenceNumber, String method)
            throws ParseException, InvalidArgumentException;


    public CSeqHeader createCSeqHeader(long sequenceNumber, String method)
            throws ParseException, InvalidArgumentException;


    public CallIdHeader createCallIdHeader(String callId) throws ParseException;


    public CallInfoHeader createCallInfoHeader(URI callInfo);


    public ContactHeader createContactHeader(Address address);


    public ContactHeader createContactHeader();


    public ContentDispositionHeader createContentDispositionHeader(String contentDispositionType)
            throws ParseException;

    public ContentEncodingHeader createContentEncodingHeader(String encoding)
            throws ParseException;


    public ContentLanguageHeader createContentLanguageHeader(Locale contentLanguage);


    public ContentLengthHeader createContentLengthHeader(int contentLength)
            throws InvalidArgumentException;


    public ContentTypeHeader createContentTypeHeader(String contentType, String contentSubType)
            throws ParseException;

    public DateHeader createDateHeader(Calendar date);


    public ErrorInfoHeader createErrorInfoHeader(URI errorInfo);

    public EventHeader createEventHeader(String eventType) throws ParseException;


    public ExpiresHeader createExpiresHeader(int expires)
            throws InvalidArgumentException;


    public Header createHeader(String name, String value) throws ParseException;


    public List createHeaders(String headers) throws ParseException;


    public FromHeader createFromHeader(Address address, String tag)
            throws ParseException;


    public InReplyToHeader createInReplyToHeader(String callId)
            throws ParseException;

    public MaxForwardsHeader createMaxForwardsHeader(int maxForwards)
            throws InvalidArgumentException;


    public MimeVersionHeader createMimeVersionHeader(int majorVersion, int minorVersion)
            throws InvalidArgumentException;


    public MinExpiresHeader createMinExpiresHeader(int minExpires)
            throws InvalidArgumentException;

    public OrganizationHeader createOrganizationHeader(String organization)
            throws ParseException;

    public PriorityHeader createPriorityHeader(String priority)
            throws ParseException;


    public ProxyAuthenticateHeader createProxyAuthenticateHeader(String scheme)
            throws ParseException;


    public ProxyAuthorizationHeader createProxyAuthorizationHeader(String scheme)
            throws ParseException;


    public ProxyRequireHeader createProxyRequireHeader(String optionTag)
            throws ParseException;


    public RAckHeader createRAckHeader(int rSeqNumber, int cSeqNumber, String method)
            throws InvalidArgumentException, ParseException;


    public RSeqHeader createRSeqHeader(int sequenceNumber)
            throws InvalidArgumentException;

    public ReasonHeader createReasonHeader(String protocol, int cause, String text)
            throws InvalidArgumentException, ParseException;

    public RecordRouteHeader createRecordRouteHeader(Address address);


    public ReplyToHeader createReplyToHeader(Address address);

    public ReferToHeader createReferToHeader(Address address);

    public RequireHeader createRequireHeader(String optionTag)
            throws ParseException;

    public RetryAfterHeader createRetryAfterHeader(int retryAfter)
            throws InvalidArgumentException;

    public RouteHeader createRouteHeader(Address address);

    public ServerHeader createServerHeader(List<String> product) throws ParseException;

    public SubjectHeader createSubjectHeader(String subject)
            throws ParseException;

    public SubscriptionStateHeader createSubscriptionStateHeader(String subscriptionState)
            throws ParseException;


    public SupportedHeader createSupportedHeader(String optionTag)
            throws ParseException;


    public TimeStampHeader createTimeStampHeader(float timeStamp)
            throws InvalidArgumentException;


    public ToHeader createToHeader(Address address, String tag) throws ParseException;


    public UnsupportedHeader createUnsupportedHeader(String optionTag)
            throws ParseException;


    public UserAgentHeader createUserAgentHeader(List product)
            throws ParseException;


    public ViaHeader createViaHeader(String host, int port, String transport,
                                     String branch) throws ParseException, InvalidArgumentException;


    public WWWAuthenticateHeader createWWWAuthenticateHeader(String scheme)
            throws ParseException;

    public WarningHeader createWarningHeader(String agent, int code, String comment)
            throws InvalidArgumentException, ParseException;


    public SIPETagHeader createSIPETagHeader(String etag) throws ParseException;


    public SIPIfMatchHeader createSIPIfMatchHeader(String etag) throws ParseException;

    public SipRequestLine createRequestLine(String requestLine) throws ParseException;


    public SipStatusLine createStatusLine(String statusLine) throws ParseException;


    public ReferredByHeader createReferredByHeader(Address address);

    public ReplacesHeader createReplacesHeader(String callId, String toTag, String fromTag) throws ParseException;


    public PAccessNetworkInfoHeader createPAccessNetworkInfoHeader();


    public PAssertedIdentityHeader createPAssertedIdentityHeader(Address address) throws NullPointerException, ParseException;


    public PAssociatedURIHeader createPAssociatedURIHeader(Address assocURI);


    public PCalledPartyIDHeader createPCalledPartyIDHeader(Address address);


    public PChargingFunctionAddressesHeader createPChargingFunctionAddressesHeader();


    public PChargingVectorHeader createChargingVectorHeader(String icid) throws ParseException;


    public PMediaAuthorizationHeader createPMediaAuthorizationHeader(String token)
            throws InvalidArgumentException, ParseException;

    public PPreferredIdentityHeader createPPreferredIdentityHeader(Address address);


    public PVisitedNetworkIDHeader createPVisitedNetworkIDHeader();


    public PathHeader createPathHeader(Address address);


    public PrivacyHeader createPrivacyHeader(String privacyType);


    public ServiceRouteHeader createServiceRouteHeader(Address address);


    public SecurityServerHeader createSecurityServerHeader();


    public SecurityClientHeader createSecurityClientHeader();


    public SecurityVerifyHeader createSecurityVerifyHeader();


    public SessionExpiresHeader createSessionExpiresHeader(int expires) throws InvalidArgumentException;


    public JoinHeader createJoinHeader(String callId, String toTag,
                                       String fromTag) throws ParseException;


    public PUserDatabaseHeader createPUserDatabaseHeader(String databaseName);


    public PProfileKeyHeader createPProfileKeyHeader(Address address);


    public PServedUserHeader createPServedUserHeader(Address address);


    public PPreferredServiceHeader createPPreferredServiceHeader();


    public PAssertedServiceHeader createPAssertedServiceHeader();


    public ReferencesHeader createReferencesHeader(String callId, String rel) throws ParseException;


    public Header createHeader(String header) throws ParseException;
}

