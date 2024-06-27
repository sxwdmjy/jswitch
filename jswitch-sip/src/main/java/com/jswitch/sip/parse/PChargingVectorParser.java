package com.jswitch.sip.parse;


import com.jswitch.sip.NameValue;
import com.jswitch.sip.header.PChargingVector;
import com.jswitch.sip.header.ParameterNamesIms;
import com.jswitch.sip.header.SIPHeader;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;

@Slf4j
public class PChargingVectorParser extends ParametersParser implements TokenTypes {

    public PChargingVectorParser(String chargingVector) {

        super(chargingVector);

    }

    protected PChargingVectorParser(Lexer lexer) {

        super(lexer);

    }



    public SIPHeader parse() throws ParseException {


        if (log.isDebugEnabled())
            dbg_enter("parse");
        try {
            headerName(TokenTypes.P_VECTOR_CHARGING);
            PChargingVector chargingVector = new PChargingVector();

            try {
                while (lexer.lookAhead(0) != '\n') {
                    this.parseParameter(chargingVector);
                    this.lexer.SPorHT();
                    char la = lexer.lookAhead(0);
                    if (la == '\n' || la == '\0')
                        break;
                    this.lexer.match(';');
                    this.lexer.SPorHT();
                }

            } catch (ParseException ex) {
                throw ex;
            }


            super.parse(chargingVector);
            if ( chargingVector.getParameter(ParameterNamesIms.ICID_VALUE) == null )
                throw new ParseException("Missing a required Parameter : " + ParameterNamesIms.ICID_VALUE, 0);
            return chargingVector;
        } finally {
            if (log.isDebugEnabled())
                dbg_leave("parse");
        }
    }

    protected void parseParameter(PChargingVector chargingVector) throws ParseException {

        if (log.isDebugEnabled())
            dbg_enter("parseParameter");
        try {
            NameValue nv = this.nameValue('=');
            chargingVector.setParameter(nv);
        } finally {
            if (log.isDebugEnabled())
                dbg_leave("parseParameter");
        }



    }



}
