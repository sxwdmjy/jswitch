package com.jswitch.sip.parse;


import com.jswitch.sip.GenericURI;
import com.jswitch.sip.SIPHeaderNames;
import com.jswitch.sip.header.CallInfo;
import com.jswitch.sip.header.CallInfoList;
import com.jswitch.sip.header.SIPHeader;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;

@Slf4j
public class CallInfoParser extends ParametersParser{
    

    public CallInfoParser(String callInfo) {
        super(callInfo);
    }

    protected CallInfoParser(Lexer lexer) {
        super(lexer);
    }
    

    public SIPHeader parse() throws ParseException {
        
        if (log.isDebugEnabled()) dbg_enter("CallInfoParser.parse");
        CallInfoList list=new CallInfoList();
        
        try {
            headerName(TokenTypes.CALL_INFO);
            
            while (lexer.lookAhead(0) != '\n') {
                CallInfo callInfo= new CallInfo();
                callInfo.setHeaderName(SIPHeaderNames.CALL_INFO);
                
                this.lexer.SPorHT();
                this.lexer.match('<');
                URLParser urlParser=new URLParser((Lexer)this.lexer);
                GenericURI uri=urlParser.uriReference(true);
                callInfo.setInfo(uri);
                this.lexer.match('>');
                this.lexer.SPorHT();
                
                super.parse(callInfo);
                list.add(callInfo);
                
                while (lexer.lookAhead(0) == ',') {
                    this.lexer.match(',');
                    this.lexer.SPorHT();
                    
                    callInfo= new CallInfo();
                    
                    this.lexer.SPorHT();
                    this.lexer.match('<');
                    urlParser=new URLParser((Lexer)this.lexer);
                    uri=urlParser.uriReference(true);
                    callInfo.setInfo(uri);
                    this.lexer.match('>');
                    this.lexer.SPorHT();
                    
                    super.parse(callInfo);
                    list.add(callInfo);
                }
            }
            
            return list;
        }
        finally {
            if (log.isDebugEnabled()) dbg_leave("CallInfoParser.parse");
        }
    }
    
    
}

