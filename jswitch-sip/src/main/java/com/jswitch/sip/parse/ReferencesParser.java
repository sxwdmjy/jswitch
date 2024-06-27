package com.jswitch.sip.parse;


import com.jswitch.sip.header.References;
import com.jswitch.sip.header.SIPHeader;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;

@Slf4j
public class ReferencesParser extends ParametersParser {


    public ReferencesParser(String references) {
        super(references);
    }


    protected ReferencesParser(Lexer lexer) {
        super(lexer);
    }
    

    public SIPHeader parse() throws ParseException {
       
        if (log.isDebugEnabled())
            dbg_enter("ReasonParser.parse");

        try {
            headerName(TokenTypes.REFERENCES);
            References references= new References();
            this.lexer.SPorHT();
               
            String callId = lexer.byteStringNoSemicolon();
           
            references.setCallId(callId);
            super.parse(references);
            return references;
       } finally {
            if (log.isDebugEnabled())
                dbg_leave("ReferencesParser.parse");
        }
    }

}
