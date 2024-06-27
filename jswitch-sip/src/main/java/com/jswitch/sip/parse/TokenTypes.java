
package com.jswitch.sip.parse;


import com.jswitch.sip.core.LexerCore;

public interface TokenTypes {

    public static final int START = LexerCore.START;
    public static final int END = LexerCore.END;

    public static final int SIP = START + 3;
    public static final int REGISTER = START + 4;
    public static final int INVITE = START + 5;
    public static final int ACK = START + 6;
    public static final int BYE = START + 7;
    public static final int OPTIONS = START + 8;
    public static final int CANCEL = START + 9;
    public static final int ERROR_INFO = START + 10;
    public static final int IN_REPLY_TO = START + 11;
    public static final int MIME_VERSION = START + 12;
    public static final int ALERT_INFO = START + 13;
    public static final int FROM = START + 14;
    public static final int TO = START + 15;
    public static final int VIA = START + 16;
    public static final int USER_AGENT = START + 17;
    public static final int SERVER = START + 18;
    public static final int ACCEPT_ENCODING = START + 19;
    public static final int ACCEPT = START + 20;
    public static final int ALLOW = START + 21;
    public static final int ROUTE = START + 22;
    public static final int AUTHORIZATION = START + 23;
    public static final int PROXY_AUTHORIZATION = START + 24;
    public static final int RETRY_AFTER = START + 25;
    public static final int PROXY_REQUIRE = START + 26;
    public static final int CONTENT_LANGUAGE = START + 27;
    public static final int UNSUPPORTED = START + 28;
    public static final int SUPPORTED = START + 20;
    public static final int WARNING = START + 30;
    public static final int MAX_FORWARDS = START + 31;
    public static final int DATE = START + 32;
    public static final int PRIORITY = START + 33;
    public static final int PROXY_AUTHENTICATE = START + 34;
    public static final int CONTENT_ENCODING = START + 35;
    public static final int CONTENT_LENGTH = START + 36;
    public static final int SUBJECT = START + 37;
    public static final int CONTENT_TYPE = START + 38;
    public static final int CONTACT = START + 39;
    public static final int CALL_ID = START + 40;
    public static final int REQUIRE = START + 41;
    public static final int EXPIRES = START + 42;
    public static final int ENCRYPTION = START + 43;
    public static final int RECORD_ROUTE = START + 44;
    public static final int ORGANIZATION = START + 45;
    public static final int CSEQ = START + 46;
    public static final int ACCEPT_LANGUAGE = START + 47;
    public static final int WWW_AUTHENTICATE = START + 48;
    public static final int RESPONSE_KEY = START + 49;
    public static final int HIDE = START + 50;
    public static final int CALL_INFO = START + 51;
    public static final int CONTENT_DISPOSITION = START + 52;
    public static final int SUBSCRIBE = START + 53;
    public static final int NOTIFY = START + 54;
    public static final int TIMESTAMP = START + 55;
    public static final int SUBSCRIPTION_STATE = START + 56;
    public static final int TEL = START + 57;
    public static final int REPLY_TO = START + 58;
    public static final int REASON = START + 59;
    public static final int RSEQ = START + 60;
    public static final int RACK = START + 61;
    public static final int MIN_EXPIRES = START + 62;
    public static final int EVENT = START + 63;
    public static final int AUTHENTICATION_INFO = START + 64;
    public static final int ALLOW_EVENTS = START + 65;
    public static final int REFER_TO = START + 66;

    // JvB: added to support RFC3903
    public static final int PUBLISH = START + 67;
    public static final int SIP_ETAG = START + 68;
    public static final int SIP_IF_MATCH = START + 69;




    public static final int MESSAGE = START + 70;

    // IMS Headers
    public static final int PATH = START + 71;
    public static final int SERVICE_ROUTE = START + 72;
    public static final int P_ASSERTED_IDENTITY = START + 73;
    public static final int P_PREFERRED_IDENTITY = START + 74;
    public static final int P_VISITED_NETWORK_ID = START + 75;
    public static final int P_CHARGING_FUNCTION_ADDRESSES = START + 76;
    public static final int P_VECTOR_CHARGING = START + 77;



    // issued by Miguel Freitas - IMS headers
    public static final int PRIVACY = START + 78;
    public static final int P_ACCESS_NETWORK_INFO = START + 79;
    public static final int P_CALLED_PARTY_ID = START + 80;
    public static final int P_ASSOCIATED_URI = START + 81;
    public static final int P_MEDIA_AUTHORIZATION = START + 82;
    public static final int P_MEDIA_AUTHORIZATION_TOKEN = START + 83;


    // pmusgrave - additions
    public static final int REFERREDBY_TO = START + 84;

    // pmusgrave RFC4028
    public static final int SESSIONEXPIRES_TO = START + 85;
    public static final int MINSE_TO = START + 86;

    // pmusgrave RFC3891
    public static final int REPLACES_TO = START + 87;

    // pmusgrave sips bug fix
    public static final int SIPS = START + 88;


    // issued by Miguel Freitas - SIP Security Agreement (RFC3329)
    public static final int SECURITY_SERVER = START + 89;
    public static final int SECURITY_CLIENT = START + 90;
    public static final int SECURITY_VERIFY = START + 91;

    // jean deruelle RFC3911
    public static final int JOIN_TO = START + 92;

    // aayush.bhatnagar: RFC 4457 support.
    public static final int P_USER_DATABASE = START + 93;
    //aayush.bhatnagar: RFC 5002 support.
    public static final int P_PROFILE_KEY = START + 94;
    //aayush.bhatnagar: RFC 5502 support.
    public static final int P_SERVED_USER = START + 95;
    //aayush.bhatnaagr: P-Preferred-Service Header:
    public static final int P_PREFERRED_SERVICE = START + 96;
    //aayush.bhatnagar: P-Asserted-Service Header:
    public static final int P_ASSERTED_SERVICE = START + 97;
    //mranga - References header
    public static final int REFERENCES = START + 98;

    public static final int ALPHA = LexerCore.ALPHA;
    public static final int DIGIT = LexerCore.DIGIT;
    public static final int ID = LexerCore.ID;
    public static final int SAFE = LexerCore.SAFE;
    public static final int WHITESPACE = LexerCore.WHITESPACE;
    public static final int BACKSLASH = LexerCore.BACKSLASH;
    public static final int QUOTE = LexerCore.QUOTE;
    public static final int AT = LexerCore.AT;
    public static final int SP = LexerCore.SP;
    public static final int HT = LexerCore.HT;
    public static final int COLON = LexerCore.COLON;
    public static final int STAR = LexerCore.STAR;
    public static final int DOLLAR = LexerCore.DOLLAR;
    public static final int PLUS = LexerCore.PLUS;
    public static final int POUND = LexerCore.POUND;
    public static final int MINUS = LexerCore.MINUS;
    public static final int DOUBLEQUOTE = LexerCore.DOUBLEQUOTE;
    public static final int TILDE = LexerCore.TILDE;
    public static final int BACK_QUOTE = LexerCore.BACK_QUOTE;
    public static final int NULL = LexerCore.NULL;
    public static final int EQUALS = (int) '=';
    public static final int SEMICOLON = (int) ';';
    public static final int SLASH = (int) '/';
    public static final int L_SQUARE_BRACKET = (int) '[';
    public static final int R_SQUARE_BRACKET = (int) ']';
    public static final int R_CURLY = (int) '}';
    public static final int L_CURLY = (int) '{';
    public static final int HAT = (int) '^';
    public static final int BAR = (int) '|';
    public static final int DOT = (int) '.';
    public static final int EXCLAMATION = (int) '!';
    public static final int LPAREN = (int) '(';
    public static final int RPAREN = (int) ')';
    public static final int GREATER_THAN = (int) '>';
    public static final int LESS_THAN = (int) '<';
    public static final int PERCENT = (int) '%';
    public static final int QUESTION = (int) '?';
    public static final int AND = (int) '&';
    public static final int UNDERSCORE = (int) '_';

}
