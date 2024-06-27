package com.jswitch.sip.parse;


import com.jswitch.sip.SIPHeaderNames;
import com.jswitch.sip.core.Token;
import com.jswitch.sip.header.ContentDisposition;
import com.jswitch.sip.header.SIPHeader;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;

@Slf4j
public class ContentDispositionParser extends ParametersParser {

    public ContentDispositionParser(String contentDisposition) {
        super(contentDisposition);
    }


    protected ContentDispositionParser(Lexer lexer) {
        super(lexer);
    }

    public SIPHeader parse() throws ParseException {

        if (log.isDebugEnabled())
            dbg_enter("ContentDispositionParser.parse");

        try {
            headerName(TokenTypes.CONTENT_DISPOSITION);

            ContentDisposition cd = new ContentDisposition();
            cd.setHeaderName(SIPHeaderNames.CONTENT_DISPOSITION);

            this.lexer.SPorHT();
            this.lexer.match(TokenTypes.ID);

            Token token = lexer.getNextToken();
            cd.setDispositionType(token.getTokenValue());
            this.lexer.SPorHT();
            super.parse(cd);

            this.lexer.SPorHT();
            this.lexer.match('\n');

            return cd;
        } catch (ParseException ex) {
            throw createParseException(ex.getMessage());
        } finally {
            if (log.isDebugEnabled())
                dbg_leave("ContentDispositionParser.parse");
        }
    }


}
