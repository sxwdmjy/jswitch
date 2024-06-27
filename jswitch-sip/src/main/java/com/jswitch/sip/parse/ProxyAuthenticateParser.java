
package com.jswitch.sip.parse;

import com.jswitch.sip.header.ProxyAuthenticate;
import com.jswitch.sip.header.SIPHeader;

import java.text.ParseException;

public class ProxyAuthenticateParser extends ChallengeParser {

    public ProxyAuthenticateParser(String proxyAuthenticate) {
        super(proxyAuthenticate);
    }


    protected ProxyAuthenticateParser(Lexer lexer) {
        super(lexer);
    }


    public SIPHeader parse() throws ParseException {
        headerName(TokenTypes.PROXY_AUTHENTICATE);
        ProxyAuthenticate proxyAuthenticate = new ProxyAuthenticate();
        super.parse(proxyAuthenticate);
        return proxyAuthenticate;
    }

}

