package com.jswitch.sip.parse;

import com.jswitch.sip.header.SIPHeader;
import com.jswitch.sip.header.UserAgent;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;

@Slf4j
public class UserAgentParser extends HeaderParser {


    public UserAgentParser(String userAgent) {
        super(userAgent);
    }


    protected UserAgentParser(Lexer lexer) {
        super(lexer);
    }


    public SIPHeader parse() throws ParseException {
        if (log.isDebugEnabled())
            dbg_enter("UserAgentParser.parse");
        UserAgent userAgent = new UserAgent();
        try {
            headerName(TokenTypes.USER_AGENT);
            if (this.lexer.lookAhead(0) == '\n')
                throw createParseException("empty header");

            while (this.lexer.lookAhead(0) != '\n'
                    && this.lexer.lookAhead(0) != '\0') {

                if (this.lexer.lookAhead(0) == '(') {
                    String comment = this.lexer.comment();
                    userAgent.addProductToken('(' + comment + ')');
                } else {

                    this.getLexer().SPorHT();


                    String product = this.lexer.byteStringNoSlash();
                    if ( product == null ) throw createParseException("Expected product string");

                    StringBuilder productSb = new StringBuilder(product);
                    if (this.lexer.peekNextToken().getTokenType() == TokenTypes.SLASH) {
                        this.lexer.match(TokenTypes.SLASH);
                        this.getLexer().SPorHT();

                        String productVersion = this.lexer.byteStringNoSlash();

                        if ( productVersion == null ) throw createParseException("Expected product version");

                        productSb.append("/");

                        productSb.append(productVersion);
                    }

                    userAgent.addProductToken(productSb.toString());
                }
                // LWS
                this.lexer.SPorHT();
            }
        } finally {
            if (log.isDebugEnabled())
                dbg_leave("UserAgentParser.parse");
        }

        return userAgent;
    }


      public static void main(String args[]) throws ParseException { String
      userAgent[] = { "User-Agent: Softphone/Beta1.5 \n", "User-Agent:Nist/Beta1 (beta version) \n", "User-Agent: Nist UA (beta version)\n",
      "User-Agent: Nist1.0/Beta2 Ubi/vers.1.0 (very cool) \n" ,
      "User-Agent: SJphone/1.60.299a/L (SJ Labs)\n",
      "User-Agent: sipXecs/3.5.11 sipXecs/sipxbridge (Linux)\n"};

      for (int i = 0; i < userAgent.length; i++ ) { UserAgentParser parser =
      new UserAgentParser(userAgent[i]); UserAgent ua= (UserAgent)
      parser.parse(); System.out.println("encoded = " + ua.encode()); }
       }

}
