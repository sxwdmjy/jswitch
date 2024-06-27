package com.jswitch.sip.parse;

import com.jswitch.common.exception.InvalidArgumentException;
import com.jswitch.sip.header.PPreferredService;
import com.jswitch.sip.header.ParameterNamesIms;
import com.jswitch.sip.header.SIPHeader;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;

@Slf4j
public class PPreferredServiceParser extends HeaderParser implements TokenTypes{

    protected PPreferredServiceParser(Lexer lexer) {
        super(lexer);
    }

    public PPreferredServiceParser(String pps)
    {
        super(pps);
    }


    public SIPHeader parse() throws ParseException {
        if(log.isDebugEnabled())
            dbg_enter("PPreferredServiceParser.parse");
        try
        {

        this.lexer.match(TokenTypes.P_PREFERRED_SERVICE);
        this.lexer.SPorHT();
        this.lexer.match(':');
        this.lexer.SPorHT();

        PPreferredService pps = new PPreferredService();
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
                    pps.setSubserviceIdentifiers(serviceID);
                   }
           else if(urn.contains(ParameterNamesIms.APPLICATION_ID_LABEL))
              {
               String appID = urn.split(ParameterNamesIms.APPLICATION_ID_LABEL)[1];
               if(appID.trim().equals(""))
                    try {
                        throw new InvalidArgumentException("URN should atleast have one sub-application");
                    } catch (InvalidArgumentException e) {
                        e.printStackTrace();
                    }
                    else
                  pps.setApplicationIdentifiers(appID);
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
                dbg_enter("PPreferredServiceParser.parse");
        }

    }
}
