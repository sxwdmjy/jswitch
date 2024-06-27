package com.jswitch.sip.parse;

import com.jswitch.sip.header.ToHeader;
import com.jswitch.sip.header.ViaHeader;
import com.jswitch.sip.core.LexerCore;
import com.jswitch.sip.header.*;

import java.util.concurrent.ConcurrentHashMap;

public class Lexer extends LexerCore {
    public static String getHeaderName(String line) {
        if (line == null)
            return null;
        String headerName = null;
        try {
            int begin = line.indexOf(":");
            headerName = null;
            if (begin >= 1)
                headerName = line.substring(0, begin).trim();
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
        return headerName;
    }

    public Lexer(String lexerName, String buffer) {
        super(lexerName, buffer);
        this.selectLexer(lexerName);
    }

    public static String getHeaderValue(String line) {
        if (line == null)
            return null;
        String headerValue = null;
        try {
            int begin = line.indexOf(":");
            headerValue = line.substring(begin + 1);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
        return headerValue;
    }

    public void selectLexer(String lexerName) {
        ConcurrentHashMap<String, Integer> lexer = lexerTables.get(lexerName);
        this.currentLexerName = lexerName;
        if (lexer == null) {
            ConcurrentHashMap<String, Integer> newLexer = new ConcurrentHashMap<String, Integer>();
            currentLexer = newLexer;
            if (lexerName.equals("method_keywordLexer")) {
                addKeyword(TokenNames.REGISTER, TokenTypes.REGISTER);
                addKeyword(TokenNames.ACK, TokenTypes.ACK);
                addKeyword(TokenNames.OPTIONS, TokenTypes.OPTIONS);
                addKeyword(TokenNames.BYE, TokenTypes.BYE);
                addKeyword(TokenNames.INVITE, TokenTypes.INVITE);
                addKeyword(TokenNames.SIP, TokenTypes.SIP);
                addKeyword(TokenNames.SIPS, TokenTypes.SIPS);
                addKeyword(TokenNames.SUBSCRIBE, TokenTypes.SUBSCRIBE);
                addKeyword(TokenNames.NOTIFY, TokenTypes.NOTIFY);
                addKeyword(TokenNames.MESSAGE, TokenTypes.MESSAGE);
                addKeyword(TokenNames.PUBLISH, TokenTypes.PUBLISH);
            } else if (lexerName.equals("command_keywordLexer")) {
                addKeyword(ErrorInfoHeader.NAME,
                        TokenTypes.ERROR_INFO);
                addKeyword(AllowEventsHeader.NAME,
                        TokenTypes.ALLOW_EVENTS);
                addKeyword(AuthenticationInfoHeader.NAME,
                        TokenTypes.AUTHENTICATION_INFO);
                addKeyword(EventHeader.NAME, TokenTypes.EVENT);
                addKeyword(MinExpiresHeader.NAME,
                        TokenTypes.MIN_EXPIRES);
                addKeyword(RSeqHeader.NAME, TokenTypes.RSEQ);
                addKeyword(RAckHeader.NAME, TokenTypes.RACK);
                addKeyword(ReasonHeader.NAME,
                        TokenTypes.REASON);
                addKeyword(ReplyToHeader.NAME,
                        TokenTypes.REPLY_TO);
                addKeyword(SubscriptionStateHeader.NAME,
                        TokenTypes.SUBSCRIPTION_STATE);
                addKeyword(TimeStampHeader.NAME,
                        TokenTypes.TIMESTAMP);
                addKeyword(InReplyToHeader.NAME,
                        TokenTypes.IN_REPLY_TO);
                addKeyword(MimeVersionHeader.NAME,
                        TokenTypes.MIME_VERSION);
                addKeyword(AlertInfoHeader.NAME,
                        TokenTypes.ALERT_INFO);
                addKeyword(FromHeader.NAME, TokenTypes.FROM);
                addKeyword(ToHeader.NAME, TokenTypes.TO);
                addKeyword(ReferToHeader.NAME,
                        TokenTypes.REFER_TO);
                addKeyword(ViaHeader.NAME, TokenTypes.VIA);
                addKeyword(UserAgentHeader.NAME,
                        TokenTypes.USER_AGENT);
                addKeyword(ServerHeader.NAME,
                        TokenTypes.SERVER);
                addKeyword(AcceptEncodingHeader.NAME,
                        TokenTypes.ACCEPT_ENCODING);
                addKeyword(AcceptHeader.NAME,
                        TokenTypes.ACCEPT);
                addKeyword(AllowHeader.NAME, TokenTypes.ALLOW);
                addKeyword(RouteHeader.NAME, TokenTypes.ROUTE);
                addKeyword(AuthorizationHeader.NAME,
                        TokenTypes.AUTHORIZATION);
                addKeyword(ProxyAuthorizationHeader.NAME,
                        TokenTypes.PROXY_AUTHORIZATION);
                addKeyword(RetryAfterHeader.NAME,
                        TokenTypes.RETRY_AFTER);
                addKeyword(ProxyRequireHeader.NAME,
                        TokenTypes.PROXY_REQUIRE);
                addKeyword(ContentLanguageHeader.NAME,
                        TokenTypes.CONTENT_LANGUAGE);
                addKeyword(UnsupportedHeader.NAME,
                        TokenTypes.UNSUPPORTED);
                addKeyword(SupportedHeader.NAME,
                        TokenTypes.SUPPORTED);
                addKeyword(WarningHeader.NAME,
                        TokenTypes.WARNING);
                addKeyword(MaxForwardsHeader.NAME,
                        TokenTypes.MAX_FORWARDS);
                addKeyword(DateHeader.NAME, TokenTypes.DATE);
                addKeyword(PriorityHeader.NAME,
                        TokenTypes.PRIORITY);
                addKeyword(ProxyAuthenticateHeader.NAME,
                        TokenTypes.PROXY_AUTHENTICATE);
                addKeyword(ContentEncodingHeader.NAME,
                        TokenTypes.CONTENT_ENCODING);
                addKeyword(ContentLengthHeader.NAME,
                        TokenTypes.CONTENT_LENGTH);
                addKeyword(SubjectHeader.NAME,
                        TokenTypes.SUBJECT);
                addKeyword(ContentTypeHeader.NAME,
                        TokenTypes.CONTENT_TYPE);
                addKeyword(ContactHeader.NAME,
                        TokenTypes.CONTACT);
                addKeyword(CallIdHeader.NAME,
                        TokenTypes.CALL_ID);
                addKeyword(RequireHeader.NAME,
                        TokenTypes.REQUIRE);
                addKeyword(ExpiresHeader.NAME,
                        TokenTypes.EXPIRES);
                addKeyword(RecordRouteHeader.NAME,
                        TokenTypes.RECORD_ROUTE);
                addKeyword(OrganizationHeader.NAME,
                        TokenTypes.ORGANIZATION);
                addKeyword(CSeqHeader.NAME, TokenTypes.CSEQ);
                addKeyword(AcceptLanguageHeader.NAME,
                        TokenTypes.ACCEPT_LANGUAGE);
                addKeyword(WWWAuthenticateHeader.NAME,
                        TokenTypes.WWW_AUTHENTICATE);
                addKeyword(CallInfoHeader.NAME,
                        TokenTypes.CALL_INFO);
                addKeyword(ContentDispositionHeader.NAME,
                        TokenTypes.CONTENT_DISPOSITION);
                // And now the dreaded short forms....
                addKeyword(TokenNames.K, TokenTypes.SUPPORTED);
                addKeyword(TokenNames.C,
                        TokenTypes.CONTENT_TYPE);
                addKeyword(TokenNames.E,
                        TokenTypes.CONTENT_ENCODING);
                addKeyword(TokenNames.F, TokenTypes.FROM);
                addKeyword(TokenNames.I, TokenTypes.CALL_ID);
                addKeyword(TokenNames.M, TokenTypes.CONTACT);
                addKeyword(TokenNames.L,
                        TokenTypes.CONTENT_LENGTH);
                addKeyword(TokenNames.S, TokenTypes.SUBJECT);
                addKeyword(TokenNames.T, TokenTypes.TO);
                addKeyword(TokenNames.U,
                        TokenTypes.ALLOW_EVENTS);
                addKeyword(TokenNames.V, TokenTypes.VIA);
                addKeyword(TokenNames.R, TokenTypes.REFER_TO);
                addKeyword(TokenNames.O, TokenTypes.EVENT);

                addKeyword(TokenNames.X, TokenTypes.SESSIONEXPIRES_TO);
                addKeyword(SIPETagHeader.NAME,
                        TokenTypes.SIP_ETAG);
                addKeyword(SIPIfMatchHeader.NAME,
                        TokenTypes.SIP_IF_MATCH);
                addKeyword(SessionExpiresHeader.NAME,
                        TokenTypes.SESSIONEXPIRES_TO);
                addKeyword(MinSEHeader.NAME,
                        TokenTypes.MINSE_TO);
                addKeyword(ReferredByHeader.NAME,
                        TokenTypes.REFERREDBY_TO);

                addKeyword(ReplacesHeader.NAME,
                        TokenTypes.REPLACES_TO);
                addKeyword(JoinHeader.NAME,
                        TokenTypes.JOIN_TO);
                addKeyword(PathHeader.NAME, TokenTypes.PATH);
                addKeyword(ServiceRouteHeader.NAME,
                        TokenTypes.SERVICE_ROUTE);
                addKeyword(PAssertedIdentityHeader.NAME,
                        TokenTypes.P_ASSERTED_IDENTITY);
                addKeyword(PPreferredIdentityHeader.NAME,
                        TokenTypes.P_PREFERRED_IDENTITY);
                addKeyword(PrivacyHeader.NAME,
                        TokenTypes.PRIVACY);
                addKeyword(PCalledPartyIDHeader.NAME,
                        TokenTypes.P_CALLED_PARTY_ID);
                addKeyword(PAssociatedURIHeader.NAME,
                        TokenTypes.P_ASSOCIATED_URI);
                addKeyword(PVisitedNetworkIDHeader.NAME,
                        TokenTypes.P_VISITED_NETWORK_ID);
                addKeyword(PChargingFunctionAddressesHeader.NAME,
                        TokenTypes.P_CHARGING_FUNCTION_ADDRESSES);
                addKeyword(PChargingVectorHeader.NAME,
                        TokenTypes.P_VECTOR_CHARGING);
                addKeyword(PAccessNetworkInfoHeader.NAME,
                        TokenTypes.P_ACCESS_NETWORK_INFO);
                addKeyword(PMediaAuthorizationHeader.NAME,
                        TokenTypes.P_MEDIA_AUTHORIZATION);

                addKeyword(SecurityServerHeader.NAME,
                        TokenTypes.SECURITY_SERVER);
                addKeyword(SecurityVerifyHeader.NAME,
                        TokenTypes.SECURITY_VERIFY);
                addKeyword(SecurityClientHeader.NAME,
                        TokenTypes.SECURITY_CLIENT);
                addKeyword(PUserDatabaseHeader.NAME,
                        TokenTypes.P_USER_DATABASE);
                addKeyword(PProfileKeyHeader.NAME,
                        TokenTypes.P_PROFILE_KEY);
                addKeyword(PServedUserHeader.NAME,
                        TokenTypes.P_SERVED_USER);
                addKeyword(PPreferredServiceHeader.NAME,
                        TokenTypes.P_PREFERRED_SERVICE);
                addKeyword(PAssertedServiceHeader.NAME,
                        TokenTypes.P_ASSERTED_SERVICE);
                addKeyword(ReferencesHeader.NAME, TokenTypes.REFERENCES);

            } else if (lexerName.equals("status_lineLexer")) {
                addKeyword(TokenNames.SIP, TokenTypes.SIP);
            } else if (lexerName.equals("request_lineLexer")) {
                addKeyword(TokenNames.SIP, TokenTypes.SIP);
            } else if (lexerName.equals("sip_urlLexer")) {
                addKeyword(TokenNames.TEL, TokenTypes.TEL);
                addKeyword(TokenNames.SIP, TokenTypes.SIP);
                addKeyword(TokenNames.SIPS, TokenTypes.SIPS);
            }

            lexer = lexerTables.putIfAbsent(lexerName, newLexer);
            if (lexer == null) {
                lexer = newLexer;
            }
            currentLexer = lexer;
        } else {
            currentLexer = lexer;
        }
    }
}

