package com.jswitch.sip.parse;


import com.jswitch.common.exception.InvalidArgumentException;
import com.jswitch.sip.header.PAssertedService;
import com.jswitch.sip.header.ParameterNamesIms;
import com.jswitch.sip.header.SIPHeader;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;

@Slf4j
public class PAssertedServiceParser extends HeaderParser implements TokenTypes{

    protected PAssertedServiceParser(Lexer lexer) {
        super(lexer);
    }

    public PAssertedServiceParser(String pas)
    {
        super(pas);
    }

    public SIPHeader parse() throws ParseException {
        if(log.isDebugEnabled())
            dbg_enter("PAssertedServiceParser.parse");
        try
        {
        this.lexer.match(TokenTypes.P_ASSERTED_SERVICE);
        this.lexer.SPorHT();
        this.lexer.match(':');
        this.lexer.SPorHT();

        PAssertedService pps = new PAssertedService();
        String urn = this.lexer.getBuffer();
        if(urn.contains(ParameterNamesIms.SERVICE_ID)){

           if(urn.contains(ParameterNamesIms.SERVICE_ID_LABEL))
                   {
                    String serviceID = urn.split(ParameterNamesIms.SERVICE_ID_LABEL+".")[1];

                     if(serviceID.trim().equals(""))
                        try {
                            throw new InvalidArgumentException("URN should atleast have one sub-service");
                        } catch (InvalidArgumentException e) {

                            e.printStackTrace();
                        }
                        else
                    pps.setSubserviceIdentifiers(urn.split(ParameterNamesIms.SERVICE_ID_LABEL)[1]);
                   }
           else if(urn.contains(ParameterNamesIms.APPLICATION_ID_LABEL))
              {
               String appID = urn.split(ParameterNamesIms.APPLICATION_ID_LABEL+".")[1];
               if(appID.trim().equals(""))
                    try {
                        throw new InvalidArgumentException("URN should atleast have one sub-application");
                    } catch (InvalidArgumentException e) {
                        e.printStackTrace();
                    }
                    else
                  pps.setApplicationIdentifiers(urn.split(ParameterNamesIms.APPLICATION_ID_LABEL)[1]);
              }
           else
           {
               try {
                throw new InvalidArgumentException("URN is not well formed");

            } catch (InvalidArgumentException e) {
                e.printStackTrace();
                    }
                  }
          }

            super.parse();
            return pps;
        }
        finally{
            if(log.isDebugEnabled())
                dbg_enter("PAssertedServiceParser.parse");
        }

    }
}
