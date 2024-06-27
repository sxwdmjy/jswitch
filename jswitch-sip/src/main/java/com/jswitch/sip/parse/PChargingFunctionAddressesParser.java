package com.jswitch.sip.parse;

import com.jswitch.sip.NameValue;
import com.jswitch.sip.header.PChargingFunctionAddresses;
import com.jswitch.sip.header.SIPHeader;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;

@Slf4j
public class PChargingFunctionAddressesParser extends ParametersParser implements TokenTypes {


    public PChargingFunctionAddressesParser(String charging) {
        super(charging);
    }

    protected PChargingFunctionAddressesParser(Lexer lexer) {
        super(lexer);
    }



    public SIPHeader parse() throws ParseException {
        if (log.isDebugEnabled())
            dbg_enter("parse");
        try {
            headerName(TokenTypes.P_CHARGING_FUNCTION_ADDRESSES);
            PChargingFunctionAddresses chargingFunctionAddresses = new PChargingFunctionAddresses();

            try {
                while (lexer.lookAhead(0) != '\n') {

                    this.parseParameter(chargingFunctionAddresses);
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


            super.parse(chargingFunctionAddresses);
            return chargingFunctionAddresses;
        } finally {
            if (log.isDebugEnabled())
                dbg_leave("parse");
        }
    }

    protected void parseParameter(PChargingFunctionAddresses chargingFunctionAddresses) throws ParseException {

        if (log.isDebugEnabled())
            dbg_enter("parseParameter");
        try {

            NameValue nv = this.nameValue('=');
             
            chargingFunctionAddresses.setMultiParameter(nv);

        } finally {
            if (log.isDebugEnabled())
                dbg_leave("parseParameter");
        }



    }






    /** Test program */

    public static void main(String args[]) throws ParseException {
        String r[] = {
                "P-Charging-Function-Addresses: ccf=\"test str\"; ecf=token\n",
                "P-Charging-Function-Addresses: ccf=192.1.1.1; ccf=192.1.1.2; ecf=192.1.1.3; ecf=192.1.1.4\n",
                "P-Charging-Function-Addresses: ccf=[5555::b99:c88:d77:e66]; ccf=[5555::a55:b44:c33:d22]; " +
                     "ecf=[5555::1ff:2ee:3dd:4cc]; ecf=[5555::6aa:7bb:8cc:9dd]\n"

                };


        for (int i = 0; i < r.length; i++ )
        {

            PChargingFunctionAddressesParser parser =
              new PChargingFunctionAddressesParser(r[i]);

            System.out.println("original = " + r[i]);

            PChargingFunctionAddresses chargAddr= (PChargingFunctionAddresses) parser.parse();
            System.out.println("encoded = " + chargAddr.encode());
        }


    }







}
