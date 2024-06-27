package com.jswitch.sip.parse;


import com.jswitch.sip.header.ReferTo;
import com.jswitch.sip.header.SIPHeader;

import java.text.ParseException;

public class ReferToParser extends AddressParametersParser {


    public ReferToParser(String referTo) {
        super(referTo);
    }

    protected ReferToParser(Lexer lexer) {
        super(lexer);
    }
    public SIPHeader parse() throws ParseException {

        headerName(TokenTypes.REFER_TO);
        ReferTo referTo = new ReferTo();
        super.parse(referTo);
        this.lexer.match('\n');
        return referTo;
    }

    public static void main(String args[]) throws ParseException {
        String to[] =
            {   "Refer-To: <sip:dave@denver.example.org?" +
                    "Replaces=12345%40192.168.118.3%3Bto-tag%3D12345%3Bfrom-tag%3D5FFE-3994>\n",
                "Refer-To: <sip:+1-650-555-2222@ss1.wcom.com;user=phone>;tag=5617\n",
                "Refer-To: T. A. Watson <sip:watson@bell-telephone.com>\n",
                "Refer-To: LittleGuy <sip:UserB@there.com>\n",
                "Refer-To: sip:mranga@120.6.55.9\n",
                "Refer-To: sip:mranga@129.6.55.9 ; tag=696928473514.129.6.55.9\n" };

        for (int i = 0; i < to.length; i++) {
            ReferToParser tp = new ReferToParser(to[i]);
            ReferTo t = (ReferTo) tp.parse();
            System.out.println("encoded = " + t.encode());

        }
    }
}
