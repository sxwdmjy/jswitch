package com.jswitch.sip.parse;

import com.jswitch.common.exception.InvalidArgumentException;
import com.jswitch.sip.header.ContentLength;
import com.jswitch.sip.header.SIPHeader;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;

@Slf4j
public class ContentLengthParser extends HeaderParser {

    public ContentLengthParser(String contentLength) {
        super(contentLength);
    }

    protected ContentLengthParser(Lexer lexer) {
        super(lexer);
    }

    public SIPHeader parse() throws ParseException {
        if (log.isDebugEnabled())
            dbg_enter("ContentLengthParser.enter");
        try {
            ContentLength contentLength = new ContentLength();
            headerName(TokenTypes.CONTENT_LENGTH);
            String number = this.lexer.number();
            contentLength.setContentLength(Integer.parseInt(number));
            this.lexer.SPorHT();
            this.lexer.match('\n');
            return contentLength;
        } catch (InvalidArgumentException | NumberFormatException ex) {
            throw createParseException(ex.getMessage());
        } finally {
            if (log.isDebugEnabled())
                dbg_leave("ContentLengthParser.leave");
        }
    }


}

