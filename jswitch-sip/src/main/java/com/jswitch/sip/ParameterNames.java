package com.jswitch.sip;

/**
 * @author danmo
 * @date 2024-06-18 10:52
 **/
public interface ParameterNames {
    public static final String SIP_URI_SCHEME = "sip";
    public static final String SIPS_URI_SCHEME = "sips";
    public static final String TEL_URI_SCHEME = "tel";
    public static final String POSTDIAL = "postdial";
    public static final String PHONE_CONTEXT_TAG = "context-tag";
    public static final String ISUB = "isub";
    public static final String PROVIDER_TAG = "provider-tag";

    public static final String UDP = GenericURI.UDP;
    public static final String TCP = GenericURI.TCP;
    public static final String TLS = GenericURI.TLS;

    public static final String NEXT_NONCE = "nextnonce";

    public static final String TAG = "tag";

    public static final String USERNAME = "username";

    public static final String URI = "uri";

    public static final String DOMAIN = "domain";

    public static final String CNONCE = "cnonce";

    public static final String PASSWORD = "password";

    public static final String RESPONSE = "response";

    public static final String RESPONSE_AUTH = "rspauth";

    public static final String OPAQUE = "opaque";

    public static final String ALGORITHM = "algorithm";

    public static final String DIGEST = "Digest";

    public static final String SIGNED_BY = "signed-by";

    public static final String SIGNATURE = "signature";

    public static final String NONCE = "nonce";

    public static final String SRAND = "srand";

    public static final String SNUM = "snum";

    public static final String TARGET_NAME = "targetname";

    // Issue reported by larryb
    public static final String NONCE_COUNT = "nc";

    public static final String PUBKEY = "pubkey";

    public static final String COOKIE = "cookie";

    public static final String REALM = "realm";

    public static final String VERSION = "version";

    public static final String STALE = "stale";

    public static final String QOP = "qop";

    public static final String NC = "nc";

    public static final String PURPOSE = "purpose";

    public static final String CARD = "card";

    public static final String INFO = "info";

    public static final String ACTION = "action";

    public static final String PROXY = "proxy";

    public static final String REDIRECT = "redirect";

    public static final String EXPIRES = "expires";

    public static final String Q = "q";

    public static final String RENDER = "render";

    public static final String SESSION = "session";

    public static final String ICON = "icon";

    public static final String ALERT = "alert";

    public static final String HANDLING = "handling";

    public static final String REQUIRED = "required";

    public static final String OPTIONAL = "optional";

    public static final String EMERGENCY = "emergency";

    public static final String URGENT = "urgent";

    public static final String NORMAL = "normal";

    public static final String NON_URGENT = "non-urgent";

    public static final String DURATION = "duration";

    public static final String BRANCH = "branch";

    public static final String HIDDEN = "hidden";

    public static final String RECEIVED = "received";

    public static final String MADDR = "maddr";

    public static final String TTL = "ttl";

    public static final String TRANSPORT = "transport";

    public static final String TEXT = "text";

    public static final String CAUSE = "cause";

    public static final String ID = "id";

    public static final String RPORT = "rport";

    public static final String TO_TAG = "to-tag";
    public static final String FROM_TAG = "from-tag";

    public static final String SIP_INSTANCE = "+sip.instance";
    public static final String PUB_GRUU = "pub-gruu";
    public static final String TEMP_GRUU = "temp-gruu";
    public static final String GRUU = "gruu";
}
