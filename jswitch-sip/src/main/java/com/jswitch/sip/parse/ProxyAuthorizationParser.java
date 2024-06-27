
package com.jswitch.sip.parse;

import com.jswitch.sip.header.ProxyAuthorization;
import com.jswitch.sip.header.SIPHeader;

import java.text.ParseException;

public class ProxyAuthorizationParser extends ChallengeParser {
    public ProxyAuthorizationParser(String proxyAuthorization) {
        super(proxyAuthorization);
    }


    protected ProxyAuthorizationParser(Lexer lexer) {
        super(lexer);
    }

    public SIPHeader parse() throws ParseException {
        headerName(TokenTypes.PROXY_AUTHORIZATION);
        ProxyAuthorization proxyAuth = new ProxyAuthorization();
        super.parse(proxyAuth);
        return proxyAuth;
    }
}

