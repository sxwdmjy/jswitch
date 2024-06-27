package com.jswitch.sip.parse;


import com.jswitch.common.exception.InvalidArgumentException;
import com.jswitch.sip.SIPHeaderNames;
import com.jswitch.sip.header.MimeVersion;
import com.jswitch.sip.header.SIPHeader;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;

@Slf4j
public class MimeVersionParser extends HeaderParser {


    public MimeVersionParser(String mimeVersion) {
        super(mimeVersion);
    }


    protected MimeVersionParser(Lexer lexer) {
        super(lexer);
    }


    public SIPHeader parse() throws ParseException {
        if (log.isDebugEnabled())
            dbg_enter("MimeVersionParser.parse");
        MimeVersion mimeVersion = new MimeVersion();
        try {
            headerName(TokenTypes.MIME_VERSION);

            mimeVersion.setHeaderName(SIPHeaderNames.MIME_VERSION);

            try {
                String majorVersion = this.lexer.number();
                mimeVersion.setMajorVersion(Integer.parseInt(majorVersion));
                this.lexer.match('.');
                String minorVersion = this.lexer.number();
                mimeVersion.setMinorVersion(Integer.parseInt(minorVersion));

            } catch (InvalidArgumentException ex) {
                throw createParseException(ex.getMessage());
            }
            this.lexer.SPorHT();

            this.lexer.match('\n');

            return mimeVersion;
        } finally {
            if (log.isDebugEnabled())
                dbg_leave("MimeVersionParser.parse");
        }
    }


}

