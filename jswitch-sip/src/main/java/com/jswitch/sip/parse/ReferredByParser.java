package com.jswitch.sip.parse;

import com.jswitch.sip.header.ReferredBy;
import com.jswitch.sip.header.SIPHeader;

import java.text.ParseException;

public class ReferredByParser extends AddressParametersParser {


    public ReferredByParser(String referBy) {
        super(referBy);
    }

    protected ReferredByParser(Lexer lexer) {
        super(lexer);
    }
    public SIPHeader parse() throws ParseException {

        headerName(TokenTypes.REFERREDBY_TO);
        ReferredBy referBy = new ReferredBy();
        super.parse(referBy);
        this.lexer.match('\n');
        return referBy;
    }

    public static void main(String args[]) throws ParseException {
        String to[] =
            {   "Referred-By: <sip:dave@denver.example.org?" +
                    "Replaces=12345%40192.168.118.3%3Bto-tag%3D12345%3Bfrom-tag%3D5FFE-3994>\n",
                "Referred-By: <sip:+1-650-555-2222@ss1.wcom.com;user=phone>;tag=5617\n",
                "Referred-By: T. A. Watson <sip:watson@bell-telephone.com>\n",
                "Referred-By: LittleGuy <sip:UserB@there.com>\n",
                "Referred-By: sip:mranga@120.6.55.9\n",
                "Referred-By: sip:mranga@129.6.55.9 ; tag=696928473514.129.6.55.9\n" };

        for (int i = 0; i < to.length; i++) {
            ReferredByParser tp = new ReferredByParser(to[i]);
            ReferredBy t = (ReferredBy) tp.parse();
            System.out.println("encoded = " + t.encode());

        }
    }
}



