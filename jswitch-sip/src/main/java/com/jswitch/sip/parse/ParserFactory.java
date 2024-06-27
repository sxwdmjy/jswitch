
package com.jswitch.sip.parse;

import com.jswitch.sip.header.ToHeader;
import com.jswitch.sip.header.ViaHeader;
import com.jswitch.sip.header.*;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Constructor;
import java.text.ParseException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class ParserFactory {
    private static Map<String, Class<? extends HeaderParser>> parserTable;
    private static Class[] constructorArgs;
    private static ConcurrentHashMap<Class, Constructor> parserConstructorCache;

    static {
        parserTable = new ConcurrentHashMap<String, Class<? extends HeaderParser>>(90);
        parserConstructorCache = new ConcurrentHashMap<Class, Constructor>();
        constructorArgs = new Class[1];
        constructorArgs[0] = String.class;
        parserTable.put(ReplyToHeader.NAME.toLowerCase(), ReplyToParser.class);

        parserTable.put(
                InReplyToHeader.NAME.toLowerCase(),
                InReplyToParser.class);

        parserTable.put(
                AcceptEncodingHeader.NAME.toLowerCase(),
                AcceptEncodingParser.class);

        parserTable.put(
                AcceptLanguageHeader.NAME.toLowerCase(),
                AcceptLanguageParser.class);

        parserTable.put("t", ToParser.class);
        parserTable.put(ToHeader.NAME.toLowerCase(), ToParser.class);

        parserTable.put(FromHeader.NAME.toLowerCase(), FromParser.class);
        parserTable.put("f", FromParser.class);

        parserTable.put(CSeqHeader.NAME.toLowerCase(), CSeqParser.class);

        parserTable.put(ViaHeader.NAME.toLowerCase(), ViaParser.class);
        parserTable.put("v", ViaParser.class);

        parserTable.put(ContactHeader.NAME.toLowerCase(), ContactParser.class);
        parserTable.put("m", ContactParser.class);

        parserTable.put(
                ContentTypeHeader.NAME.toLowerCase(),
                ContentTypeParser.class);
        parserTable.put("c", ContentTypeParser.class);

        parserTable.put(
                ContentLengthHeader.NAME.toLowerCase(),
                ContentLengthParser.class);
        parserTable.put("l", ContentLengthParser.class);

        parserTable.put(
                AuthorizationHeader.NAME.toLowerCase(),
                AuthorizationParser.class);

        parserTable.put(
                WWWAuthenticateHeader.NAME.toLowerCase(),
                WWWAuthenticateParser.class);

        parserTable.put(CallIdHeader.NAME.toLowerCase(), CallIDParser.class);
        parserTable.put("i", CallIDParser.class);

        parserTable.put(RouteHeader.NAME.toLowerCase(), RouteParser.class);

        parserTable.put(
                RecordRouteHeader.NAME.toLowerCase(),
                RecordRouteParser.class);

        parserTable.put(DateHeader.NAME.toLowerCase(), DateParser.class);

        parserTable.put(
                ProxyAuthorizationHeader.NAME.toLowerCase(),
                ProxyAuthorizationParser.class);

        parserTable.put(
                ProxyAuthenticateHeader.NAME.toLowerCase(),
                ProxyAuthenticateParser.class);

        parserTable.put(
                RetryAfterHeader.NAME.toLowerCase(),
                RetryAfterParser.class);

        parserTable.put(RequireHeader.NAME.toLowerCase(), RequireParser.class);

        parserTable.put(
                ProxyRequireHeader.NAME.toLowerCase(),
                ProxyRequireParser.class);

        parserTable.put(
                TimeStampHeader.NAME.toLowerCase(),
                TimeStampParser.class);

        parserTable.put(
                UnsupportedHeader.NAME.toLowerCase(),
                UnsupportedParser.class);

        parserTable.put(
                UserAgentHeader.NAME.toLowerCase(),
                UserAgentParser.class);

        parserTable.put(
                SupportedHeader.NAME.toLowerCase(),
                SupportedParser.class);
        parserTable.put("k", SupportedParser.class);

        parserTable.put(ServerHeader.NAME.toLowerCase(), ServerParser.class);

        parserTable.put(SubjectHeader.NAME.toLowerCase(), SubjectParser.class);
        parserTable.put("s", SubjectParser.class); // JvB: added

        parserTable.put(
                SubscriptionStateHeader.NAME.toLowerCase(),
                SubscriptionStateParser.class);

        parserTable.put(
                MaxForwardsHeader.NAME.toLowerCase(),
                MaxForwardsParser.class);

        parserTable.put(
                MimeVersionHeader.NAME.toLowerCase(),
                MimeVersionParser.class);

        parserTable.put(
                MinExpiresHeader.NAME.toLowerCase(),
                MinExpiresParser.class);

        parserTable.put(
                OrganizationHeader.NAME.toLowerCase(),
                OrganizationParser.class);

        parserTable.put(
                PriorityHeader.NAME.toLowerCase(),
                PriorityParser.class);

        parserTable.put(RAckHeader.NAME.toLowerCase(), RAckParser.class);

        parserTable.put(RSeqHeader.NAME.toLowerCase(), RSeqParser.class);

        parserTable.put(ReasonHeader.NAME.toLowerCase(), ReasonParser.class);

        parserTable.put(WarningHeader.NAME.toLowerCase(), WarningParser.class);

        parserTable.put(ExpiresHeader.NAME.toLowerCase(), ExpiresParser.class);

        parserTable.put(EventHeader.NAME.toLowerCase(), EventParser.class);
        parserTable.put("o", EventParser.class);

        parserTable.put(
                ErrorInfoHeader.NAME.toLowerCase(),
                ErrorInfoParser.class);

        parserTable.put(
                ContentLanguageHeader.NAME.toLowerCase(),
                ContentLanguageParser.class);

        parserTable.put(
                ContentEncodingHeader.NAME.toLowerCase(),
                ContentEncodingParser.class);
        parserTable.put("e", ContentEncodingParser.class);

        parserTable.put(
                ContentDispositionHeader.NAME.toLowerCase(),
                ContentDispositionParser.class);

        parserTable.put(
                CallInfoHeader.NAME.toLowerCase(),
                CallInfoParser.class);

        parserTable.put(
                AuthenticationInfoHeader.NAME.toLowerCase(),
                AuthenticationInfoParser.class);

        parserTable.put(AllowHeader.NAME.toLowerCase(), AllowParser.class);

        parserTable.put(
                AllowEventsHeader.NAME.toLowerCase(),
                AllowEventsParser.class);
        parserTable.put("u", AllowEventsParser.class);

        parserTable.put(
                AlertInfoHeader.NAME.toLowerCase(),
                AlertInfoParser.class);

        parserTable.put(AcceptHeader.NAME.toLowerCase(), AcceptParser.class);

        parserTable.put(ReferToHeader.NAME.toLowerCase(), ReferToParser.class);
        // Was missing (bug noticed by Steve Crossley)
        parserTable.put("r", ReferToParser.class);

        // JvB: added to support RFC3903 PUBLISH
        parserTable.put(SIPETagHeader.NAME.toLowerCase(), SIPETagParser.class);
        parserTable.put(SIPIfMatchHeader.NAME.toLowerCase(), SIPIfMatchParser.class);

        //IMS headers
        parserTable.put(PAccessNetworkInfoHeader.NAME.toLowerCase(), PAccessNetworkInfoParser.class);
        parserTable.put(PAssertedIdentityHeader.NAME.toLowerCase(), PAssertedIdentityParser.class);
        parserTable.put(PPreferredIdentityHeader.NAME.toLowerCase(), PPreferredIdentityParser.class);
        parserTable.put(PChargingVectorHeader.NAME.toLowerCase(), PChargingVectorParser.class);
        parserTable.put(PChargingFunctionAddressesHeader.NAME.toLowerCase(), PChargingFunctionAddressesParser.class);
        parserTable.put(PMediaAuthorizationHeader.NAME.toLowerCase(), PMediaAuthorizationParser.class);
        parserTable.put(PathHeader.NAME.toLowerCase(), PathParser.class);
        parserTable.put(PrivacyHeader.NAME.toLowerCase(), PrivacyParser.class);
        parserTable.put(ServiceRouteHeader.NAME.toLowerCase(), ServiceRouteParser.class);
        parserTable.put(PVisitedNetworkIDHeader.NAME.toLowerCase(), PVisitedNetworkIDParser.class);

        // added for more P-header extensions for IMS :
        parserTable.put(PServedUserHeader.NAME.toLowerCase(), PServedUserParser.class);
        parserTable.put(PPreferredServiceHeader.NAME.toLowerCase(), PPreferredServiceParser.class);
        parserTable.put(PAssertedServiceHeader.NAME.toLowerCase(), PAssertedServiceParser.class);
        parserTable.put(PProfileKeyHeader.NAME.toLowerCase(), PProfileKeyParser.class);
        parserTable.put(PUserDatabaseHeader.NAME.toLowerCase(), PUserDatabaseParser.class);


        parserTable.put(PAssociatedURIHeader.NAME.toLowerCase(), PAssociatedURIParser.class);
        parserTable.put(PCalledPartyIDHeader.NAME.toLowerCase(), PCalledPartyIDParser.class);

        parserTable.put(SecurityServerHeader.NAME.toLowerCase(), SecurityServerParser.class);
        parserTable.put(SecurityClientHeader.NAME.toLowerCase(), SecurityClientParser.class);
        parserTable.put(SecurityVerifyHeader.NAME.toLowerCase(), SecurityVerifyParser.class);


        // Per RFC 3892 (pmusgrave)
        parserTable.put(ReferredBy.NAME.toLowerCase(), ReferredByParser.class);
        parserTable.put("b", ReferToParser.class);

        // Per RFC4028 Session Timers (pmusgrave)
        parserTable.put(SessionExpires.NAME.toLowerCase(), SessionExpiresParser.class);
        parserTable.put("x", SessionExpiresParser.class);
        parserTable.put(MinSE.NAME.toLowerCase(), MinSEParser.class);
        // (RFC4028 does not give a short form header for MinSE)
        // Per RFC3891 (pmusgrave)
        parserTable.put(Replaces.NAME.toLowerCase(), ReplacesParser.class);
        // Per RFC3911 (jean deruelle)
        parserTable.put(Join.NAME.toLowerCase(), JoinParser.class);
        parserTable.put(References.NAME.toLowerCase(), ReferencesParser.class);

    }

    public static void addToParserTable(String headerName, Class<? extends HeaderParser> parserClass) {
        parserTable.put(headerName.toLowerCase(), parserClass);
    }

    public static HeaderParser createParser(String line)
            throws ParseException {
        String headerName = Lexer.getHeaderName(line);
        String headerValue = Lexer.getHeaderValue(line);
        if (headerName == null || headerValue == null)
            throw new ParseException("The header name or value is null", 0);

        Class parserClass = (Class) parserTable.get(SIPHeaderNamesCache.toLowerCase(headerName));
        if (parserClass != null) {
            try {
                Constructor cons = (Constructor) parserConstructorCache.get(parserClass);
                if (cons == null) {
                    cons = parserClass.getConstructor(constructorArgs);
                    parserConstructorCache.putIfAbsent(parserClass, cons);
                }
                Object[] args = new Object[1];
                args[0] = line;
                HeaderParser retval = (HeaderParser) cons.newInstance(args);
                return retval;

            } catch (Exception ex) {
                log.error("createParser Exception " + ex.getMessage(), ex);
                return null;
            }

        } else {
            return new HeaderParser(line);
        }
    }
}
