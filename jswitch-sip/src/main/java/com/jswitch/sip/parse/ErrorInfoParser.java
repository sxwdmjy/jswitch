package com.jswitch.sip.parse;

import com.jswitch.sip.GenericURI;
import com.jswitch.sip.SIPHeaderNames;
import com.jswitch.sip.header.ErrorInfo;
import com.jswitch.sip.header.ErrorInfoList;
import com.jswitch.sip.header.SIPHeader;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;

@Slf4j
public class ErrorInfoParser extends ParametersParser {

    public ErrorInfoParser(String errorInfo) {
        super(errorInfo);
    }


    protected ErrorInfoParser(Lexer lexer) {
        super(lexer);
    }

    public SIPHeader parse() throws ParseException {

        if (log.isDebugEnabled())
            dbg_enter("ErrorInfoParser.parse");
        ErrorInfoList list = new ErrorInfoList();

        try {
            headerName(TokenTypes.ERROR_INFO);

            while (lexer.lookAhead(0) != '\n') {
            	do {
	                ErrorInfo errorInfo = new ErrorInfo();
	                errorInfo.setHeaderName(SIPHeaderNames.ERROR_INFO);
	
	                this.lexer.SPorHT();
	                this.lexer.match('<');
	                URLParser urlParser = new URLParser((Lexer) this.lexer);
	                GenericURI uri = urlParser.uriReference( true );
	                errorInfo.setErrorInfo(uri);
	                this.lexer.match('>');
	                this.lexer.SPorHT();
	
	                super.parse(errorInfo);
	                list.add(errorInfo);
	                
	                if ( lexer.lookAhead(0) == ',' ) {
	                	this.lexer.match(',');
	                } else break;
            	} while (true);
            }

            return list;
        } finally {
            if (log.isDebugEnabled())
                dbg_leave("ErrorInfoParser.parse");
        }
    }


}
