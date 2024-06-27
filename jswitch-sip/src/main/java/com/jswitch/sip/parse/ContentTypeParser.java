package com.jswitch.sip.parse;


import com.jswitch.sip.core.Token;
import com.jswitch.sip.header.SIPHeader;
import lombok.extern.slf4j.Slf4j;
import com.jswitch.sip.header.ContentType;
import java.text.ParseException;

@Slf4j
public class ContentTypeParser extends ParametersParser {

    public ContentTypeParser(String contentType) {
        super(contentType);
    }

    protected ContentTypeParser(Lexer lexer) {
        super(lexer);
    }

    public SIPHeader parse() throws ParseException {

        ContentType contentType = new ContentType();
        if (log.isDebugEnabled())
            dbg_enter("ContentTypeParser.parse");

        try {
            this.headerName(TokenTypes.CONTENT_TYPE);

            // The type:
            lexer.match(TokenTypes.ID);
            Token type = lexer.getNextToken();
            this.lexer.SPorHT();
            contentType.setContentType(type.getTokenValue());

            // The sub-type:
            lexer.match('/');
            lexer.match(TokenTypes.ID);
            Token subType = lexer.getNextToken();
            this.lexer.SPorHT();
            contentType.setContentSubType(subType.getTokenValue());
            super.parse(contentType);
            this.lexer.match('\n');
        } finally {
            if (log.isDebugEnabled())
                dbg_leave("ContentTypeParser.parse");
        }
        return contentType;

    }


}

