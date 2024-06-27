
package com.jswitch.sip.parse;

import com.jswitch.sip.core.Token;
import com.jswitch.sip.header.ContentLanguage;
import com.jswitch.sip.header.ContentLanguageList;
import com.jswitch.sip.header.SIPHeader;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;

@Slf4j
public class ContentLanguageParser extends HeaderParser {


    public ContentLanguageParser(String contentLanguage) {
        super(contentLanguage);
    }


    protected ContentLanguageParser(Lexer lexer) {
        super(lexer);
    }

    public SIPHeader parse() throws ParseException {

        if (log.isDebugEnabled())
            dbg_enter("ContentLanguageParser.parse");
        ContentLanguageList list = new ContentLanguageList();

        try {
            headerName(TokenTypes.CONTENT_LANGUAGE);

            while (lexer.lookAhead(0) != '\n') {
                this.lexer.SPorHT();
                this.lexer.match(TokenTypes.ID);

                Token token = lexer.getNextToken();
                ContentLanguage cl = new ContentLanguage( token.getTokenValue() );
                this.lexer.SPorHT();
                list.add(cl);

                while (lexer.lookAhead(0) == ',') {
                    this.lexer.match(',');
                    this.lexer.SPorHT();
                    this.lexer.match(TokenTypes.ID);
                    this.lexer.SPorHT();
                    token = lexer.getNextToken();
                    cl = new ContentLanguage( token.getTokenValue() );
                    this.lexer.SPorHT();
                    list.add(cl);
                }
            }

            return list;
        } catch (ParseException ex) {
            throw createParseException(ex.getMessage());
        } finally {
            if (log.isDebugEnabled())
                dbg_leave("ContentLanguageParser.parse");
        }
    }


}
