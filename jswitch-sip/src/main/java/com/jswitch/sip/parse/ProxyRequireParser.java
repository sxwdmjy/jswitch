package com.jswitch.sip.parse;

import com.jswitch.sip.SIPHeaderNames;
import com.jswitch.sip.core.Token;
import com.jswitch.sip.header.ProxyRequire;
import com.jswitch.sip.header.ProxyRequireList;
import com.jswitch.sip.header.SIPHeader;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;

@Slf4j
public class ProxyRequireParser extends HeaderParser {


    public ProxyRequireParser(String require) {
        super(require);
    }


    protected ProxyRequireParser(Lexer lexer) {
        super(lexer);
    }


    public SIPHeader parse() throws ParseException {
        ProxyRequireList list = new ProxyRequireList();
        if (log.isDebugEnabled())
            dbg_enter("ProxyRequireParser.parse");

        try {
            headerName(TokenTypes.PROXY_REQUIRE);

            while (lexer.lookAhead(0) != '\n') {
                ProxyRequire r = new ProxyRequire();
                r.setHeaderName(SIPHeaderNames.PROXY_REQUIRE);

                // Parsing the option tag
                this.lexer.match(TokenTypes.ID);
                Token token = lexer.getNextToken();
                r.setOptionTag(token.getTokenValue());
                this.lexer.SPorHT();

                list.add(r);

                while (lexer.lookAhead(0) == ',') {
                    this.lexer.match(',');
                    this.lexer.SPorHT();

                    r = new ProxyRequire();

                    // Parsing the option tag
                    this.lexer.match(TokenTypes.ID);
                    token = lexer.getNextToken();
                    r.setOptionTag(token.getTokenValue());
                    this.lexer.SPorHT();

                    list.add(r);
                }

            }
        } finally {
            if (log.isDebugEnabled())
                dbg_leave("ProxyRequireParser.parse");
        }

        return list;
    }


}

