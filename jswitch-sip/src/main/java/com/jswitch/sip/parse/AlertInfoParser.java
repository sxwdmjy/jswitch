
package com.jswitch.sip.parse;


import com.jswitch.sip.GenericURI;
import com.jswitch.sip.SIPHeaderNames;
import com.jswitch.sip.header.AlertInfo;
import com.jswitch.sip.header.AlertInfoList;
import com.jswitch.sip.header.SIPHeader;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;

@Slf4j
public class AlertInfoParser extends ParametersParser {


    public AlertInfoParser(String alertInfo) {
        super(alertInfo);
    }


    protected AlertInfoParser(Lexer lexer) {
        super(lexer);
    }


    public SIPHeader parse() throws ParseException {

        if (log.isDebugEnabled())
            dbg_enter("AlertInfoParser.parse");
        AlertInfoList list = new AlertInfoList();

        try {
            headerName(TokenTypes.ALERT_INFO);
            int lineCount = 0;
            while ( (lexer.lookAhead(0) != '\n') && (lineCount < 20) ) {
                do {
                	AlertInfo alertInfo = new AlertInfo();
                    alertInfo.setHeaderName(SIPHeaderNames.ALERT_INFO);
                    URLParser urlParser;
                    GenericURI uri;
	                this.lexer.SPorHT();
	                if (this.lexer.lookAhead(0) == '<') {
	                    this.lexer.match('<');
	                    urlParser = new URLParser((Lexer) this.lexer);
	                    uri = urlParser.uriReference( true );
	                    alertInfo.setAlertInfo(uri);
	                    this.lexer.match('>');
	                } else {
	                	String alertInfoStr = this.lexer.byteStringNoSemicolon();
	                	alertInfo.setAlertInfo(alertInfoStr);
	                }
	                	
	                this.lexer.SPorHT();
	
	                super.parse(alertInfo);
	                list.add(alertInfo);
	                
	                if ( lexer.lookAhead(0) == ',' ) {
	                	this.lexer.match(',');
	                } else break;
                } while (true);
                lineCount++;
             }
            return list;
        } finally {
            if (log.isDebugEnabled())
                dbg_leave("AlertInfoParser.parse");
        }
    }
}
