
package com.jswitch.sip.parse;

import com.jswitch.sip.header.SIPDateHeader;
import com.jswitch.sip.header.SIPHeader;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.util.Calendar;

@Slf4j
public class DateParser extends HeaderParser {


    public DateParser(String date) {
        super(date);
    }

    protected DateParser(Lexer lexer) {
        super(lexer);
    }


    public SIPHeader parse() throws ParseException {
        if (log.isDebugEnabled())
            dbg_enter("DateParser.parse");
        try {
            headerName(TokenTypes.DATE);
            wkday();
            lexer.match(',');
            lexer.match(' ');
            Calendar cal = date();
            lexer.match(' ');
            time(cal);
            lexer.match(' ');
            String tzone = this.lexer.ttoken().toLowerCase();
            if (!"gmt".equals(tzone))
                throw createParseException("Bad Time Zone " + tzone);
            this.lexer.match('\n');
            SIPDateHeader retval = new SIPDateHeader();
            retval.setDate(cal);
            return retval;
        } finally {
            if (log.isDebugEnabled())
                dbg_leave("DateParser.parse");

        }

    }


}
