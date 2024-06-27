
package com.jswitch.sip.parse;

import com.jswitch.sip.header.SIPHeader;
import com.jswitch.sip.header.Subject;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;

@Slf4j
public class SubjectParser extends HeaderParser {


    public SubjectParser(String subject) {
        super(subject);
    }


    protected SubjectParser(Lexer lexer) {
        super(lexer);
    }


    public SIPHeader parse() throws ParseException {
        Subject subject = new Subject();
        if (log.isDebugEnabled())
            dbg_enter("SubjectParser.parse");

        try {
            headerName(TokenTypes.SUBJECT);
            String s = this.lexer.getRest();
            subject.setSubject(s.trim());

        } finally {
            if (log.isDebugEnabled())
                dbg_leave("SubjectParser.parse");
        }

        return subject;
    }


}

