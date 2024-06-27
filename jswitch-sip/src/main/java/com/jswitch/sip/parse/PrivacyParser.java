package com.jswitch.sip.parse;


import com.jswitch.sip.core.Token;
import com.jswitch.sip.header.Privacy;
import com.jswitch.sip.header.PrivacyList;
import com.jswitch.sip.header.SIPHeader;
import com.jswitch.sip.header.SIPHeaderNamesIms;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;

@Slf4j
public class PrivacyParser extends HeaderParser implements TokenTypes
{


    public PrivacyParser(String privacyType) {
        super(privacyType);
    }

    protected PrivacyParser(Lexer lexer) {
        super(lexer);
    }


    public SIPHeader parse() throws ParseException
    {
        if (log.isDebugEnabled())
            dbg_enter("PrivacyParser.parse");

        PrivacyList privacyList = new PrivacyList();

        try
        {
            this.headerName(TokenTypes.PRIVACY);

            while (lexer.lookAhead(0) != '\n') {
                this.lexer.SPorHT();

                Privacy privacy = new Privacy();
                privacy.setHeaderName(SIPHeaderNamesIms.PRIVACY);

                this.lexer.match(TokenTypes.ID);
                Token token = lexer.getNextToken();
                privacy.setPrivacy(token.getTokenValue());
                this.lexer.SPorHT();
                privacyList.add(privacy);

                // Parsing others option-tags
                while (lexer.lookAhead(0) == ';')
                {
                    this.lexer.match(';');
                    this.lexer.SPorHT();
                    privacy = new Privacy();
                    this.lexer.match(TokenTypes.ID);
                    token = lexer.getNextToken();
                    privacy.setPrivacy(token.getTokenValue());
                    this.lexer.SPorHT();

                    privacyList.add(privacy);
                }
            }

            return privacyList;

        }
        finally {
            if (log.isDebugEnabled())
                dbg_leave("PrivacyParser.parse");
        }

    }


    public static void main(String args[]) throws ParseException
    {
        String rou[] = {

                "Privacy: none\n",
                "Privacy: none;id;user\n"
            };

        for (int i = 0; i < rou.length; i++ ) {
            PrivacyParser rp =
              new PrivacyParser(rou[i]);
            PrivacyList list = (PrivacyList) rp.parse();
            System.out.println("encoded = " +list.encode());
        }
    }



}

