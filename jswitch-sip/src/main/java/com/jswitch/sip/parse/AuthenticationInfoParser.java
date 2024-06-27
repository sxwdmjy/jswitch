package com.jswitch.sip.parse;

import com.jswitch.sip.NameValue;
import com.jswitch.sip.SIPHeaderNames;
import com.jswitch.sip.header.AuthenticationInfo;
import com.jswitch.sip.header.SIPHeader;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;

@Slf4j
public class AuthenticationInfoParser extends ParametersParser {


    public AuthenticationInfoParser(String authenticationInfo) {
        super(authenticationInfo);
    }


    protected AuthenticationInfoParser(Lexer lexer) {
        super(lexer);
    }


    public SIPHeader parse() throws ParseException {

        if (log.isDebugEnabled())
            dbg_enter("AuthenticationInfoParser.parse");

        try {
            headerName(TokenTypes.AUTHENTICATION_INFO);

            AuthenticationInfo authenticationInfo = new AuthenticationInfo();
            authenticationInfo.setHeaderName(
                SIPHeaderNames.AUTHENTICATION_INFO);

            this.lexer.SPorHT();

            NameValue nv = super.nameValue();
            if ("".equals(nv.getValue()) && !nv.isValueQuoted()) {
                authenticationInfo.setScheme(nv.getKey());
                nv = super.nameValue();
            }
            authenticationInfo.setParameter(nv);
            this.lexer.SPorHT();
            while (lexer.lookAhead(0) == ',') {
                this.lexer.match(',');
                this.lexer.SPorHT();

                nv = super.nameValue();
                authenticationInfo.setParameter(nv);
                this.lexer.SPorHT();
            }
            this.lexer.SPorHT();
            this.lexer.match('\n');

            return authenticationInfo;
        } finally {
            if (log.isDebugEnabled())
                dbg_leave("AuthenticationInfoParser.parse");
        }
    }

}
