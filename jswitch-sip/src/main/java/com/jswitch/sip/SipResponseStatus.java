package com.jswitch.sip;

import lombok.Getter;

@Getter
public enum SipResponseStatus {

    TRYING(100,"Trying"),
    RINGING(180,"Ringing"),
    CALL_IS_BEING_FORWARDED(181,"Call is being forwarded"),
    QUEUED(182,"Queued"),
    SESSION_PROGRESS(183,"Session progress"),
    OK(200,"OK"),
    ACCEPTED(202,"Accepted"),
    MULTIPLE_CHOICES(300,"Multiple choices"),
    MOVED_PERMANENTLY(301,"Moved permanently"),
    MOVED_TEMPORARILY(302,"Moved Temporarily"),
    USE_PROXY(305,"Use proxy"),
    ALTERNATIVE_SERVICE(380,"Alternative service"),
    BAD_REQUEST(400,"Bad request"),
    UNAUTHORIZED(401,"Unauthorized"),
    PAYMENT_REQUIRED(402,"Payment required"),
    FORBIDDEN(403,"Forbidden"),
    NOT_FOUND(404,"Not found"),
    METHOD_NOT_ALLOWED(405,"Method not allowed"),
    NOT_ACCEPTABLE(406,"Not acceptable"),
    PROXY_AUTHENTICATION_REQUIRED(407,"Proxy Authentication required"),
    REQUEST_TIMEOUT(408,"Request timeout"),
    GONE(410,"Gone"),
    CONDITIONAL_REQUEST_FAILED(412,"Conditional request failed"),
    REQUEST_ENTITY_TOO_LARGE(413,"Request entity too large"),
    REQUEST_URI_TOO_LONG(414,"Request-URI too large"),
    UNSUPPORTED_MEDIA_TYPE(415,"Unsupported media type"),
    UNSUPPORTED_URI_SCHEME(416,"Unsupported URI Scheme"),
    BAD_EXTENSION(420,"Bad extension"),
    EXTENSION_REQUIRED(421,"Etension Required"),
    INTERVAL_TOO_BRIEF(423,"Interval too brief"),
    TEMPORARILY_UNAVAILABLE(480,"Temporarily Unavailable"),
    CALL_OR_TRANSACTION_DOES_NOT_EXIST(481,"Call leg/Transaction does not exist"),
    LOOP_DETECTED(482,"Loop detected"),
    TOO_MANY_HOPS(483,"Too many hops"),
    ADDRESS_INCOMPLETE(484,"Address incomplete"),
    AMBIGUOUS(485,"Ambiguous"),
    BUSY_HERE(486,"Busy here"),
    REQUEST_TERMINATED(487,"Request Terminated"),
    NOT_ACCEPTABLE_HERE(488,"Not Acceptable here"),
    BAD_EVENT(489,"Bad Event"),
    REQUEST_PENDING(491,"Request Pending"),
    UNDECIPHERABLE(493,"Undecipherable"),
    SERVER_INTERNAL_ERROR(500,"Server Internal Error"),
    NOT_IMPLEMENTED(501,"Not implemented"),
    BAD_GATEWAY(502,"Bad gateway"),
    SERVICE_UNAVAILABLE(503,"Service unavailable"),
    SERVER_TIMEOUT(504,"Gateway timeout"),
    VERSION_NOT_SUPPORTED(505,"SIP version not supported"),
    MESSAGE_TOO_LARGE(513,"Message Too Large"),
    BUSY_EVERYWHERE(600,"Busy everywhere"),
    DECLINE(603,"Decline"),
    DOES_NOT_EXIST_ANYWHERE(604,"Does not exist anywhere"),
    SESSION_NOT_ACCEPTABLE(606,"Session Not acceptable"),
    ;
    private int statusCode;
    private String reasonPhrase;

    SipResponseStatus(int statusCode,String reasonPhrase) {
        this.statusCode = statusCode;
        this.reasonPhrase = reasonPhrase;
    }

    public static SipResponseStatus getStatus(int statusCode) {
        for (SipResponseStatus status : SipResponseStatus.values()) {
            if (status.getStatusCode() == statusCode) {
                return status;
            }
        }
        return null;
    }

}
