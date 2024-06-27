package com.jswitch.sip.parse;

import com.jswitch.common.exception.InvalidArgumentException;
import com.jswitch.sip.SIPHeaderNames;
import com.jswitch.sip.header.SIPHeader;
import com.jswitch.sip.header.TimeStamp;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;

@Slf4j
public class TimeStampParser extends HeaderParser {


    public TimeStampParser(String timeStamp) {
        super(timeStamp);
    }


    protected TimeStampParser(Lexer lexer) {
        super(lexer);
    }


    public SIPHeader parse() throws ParseException {

        if (log.isDebugEnabled())
            dbg_enter("TimeStampParser.parse");
        TimeStamp timeStamp = new TimeStamp();
        try {
            headerName(TokenTypes.TIMESTAMP);

            timeStamp.setHeaderName(SIPHeaderNames.TIMESTAMP);

            this.lexer.SPorHT();
            String firstNumber = this.lexer.number();

            try {

                if (lexer.lookAhead(0) == '.') {
                    this.lexer.match('.');
                    String secondNumber = this.lexer.number();

                    String s = firstNumber + "." + secondNumber;
                    float ts = Float.parseFloat(s);
                    timeStamp.setTimeStamp(ts);
                } else {
                    long ts = Long.parseLong(firstNumber);
                    timeStamp.setTime(ts);
                }


            } catch (NumberFormatException | InvalidArgumentException ex) {
                throw createParseException(ex.getMessage());
            }

            this.lexer.SPorHT();
            if (lexer.lookAhead(0) != '\n') {
                firstNumber = this.lexer.number();

                try {

                    if (lexer.lookAhead(0) == '.') {
                        this.lexer.match('.');
                        String secondNumber = this.lexer.number();

                        String s = firstNumber + "." + secondNumber;
                        float ts = Float.parseFloat(s);
                        timeStamp.setDelay(ts);
                    } else {
                        int ts = Integer.parseInt(firstNumber);
                        timeStamp.setDelay(ts);
                    }


                } catch (NumberFormatException | InvalidArgumentException ex) {
                    throw createParseException(ex.getMessage());
                }
            }

        } finally {
            if (log.isDebugEnabled())
                dbg_leave("TimeStampParser.parse");
        }
        return timeStamp;
    }

}

