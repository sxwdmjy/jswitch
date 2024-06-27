
package com.jswitch.sip.parse;

import com.jswitch.sip.GenericURI;
import com.jswitch.sip.RequestLine;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;

@Slf4j
public class RequestLineParser extends Parser {
    public RequestLineParser(String requestLine) {
        this.lexer = new Lexer("method_keywordLexer", requestLine);
    }

    public RequestLineParser(Lexer lexer) {
        this.lexer = lexer;
        this.lexer.selectLexer("method_keywordLexer");
    }

    public RequestLine parse() throws ParseException {
        if (log.isDebugEnabled())
            dbg_enter("parse");
        try {
            RequestLine retval = new RequestLine();
            String m = method();
            lexer.SPorHT();
            retval.setMethod(m);
            this.lexer.selectLexer("sip_urlLexer");
            URLParser urlParser = new URLParser(this.getLexer());
            GenericURI url = urlParser.uriReference(true);
            lexer.SPorHT();
            retval.setUri(url);
            this.lexer.selectLexer("request_lineLexer");
            String v = sipVersion();
            retval.setSipVersion(v);
            lexer.SPorHT();
            lexer.match('\n');
            return retval;
        } finally {
            if (log.isDebugEnabled())
                dbg_leave("parse");
        }
    }

}

