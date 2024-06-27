package com.jswitch.sip.parse;


import com.jswitch.common.exception.InvalidArgumentException;
import com.jswitch.sip.core.Token;
import com.jswitch.sip.header.PMediaAuthorization;
import com.jswitch.sip.header.PMediaAuthorizationList;
import com.jswitch.sip.header.SIPHeader;
import com.jswitch.sip.header.SIPHeaderNamesIms;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;

@Slf4j
public class PMediaAuthorizationParser extends HeaderParser implements TokenTypes
{

    public PMediaAuthorizationParser(String mediaAuthorization)
    {
        super(mediaAuthorization);

    }

    public PMediaAuthorizationParser(Lexer lexer)
    {
        super(lexer);

    }





    public SIPHeader parse() throws ParseException
    {
        PMediaAuthorizationList mediaAuthorizationList = new PMediaAuthorizationList();

        if (log.isDebugEnabled())
            dbg_enter("MediaAuthorizationParser.parse");


        try
        {
            headerName(TokenTypes.P_MEDIA_AUTHORIZATION);

            PMediaAuthorization mediaAuthorization = new PMediaAuthorization();
            mediaAuthorization.setHeaderName(SIPHeaderNamesIms.P_MEDIA_AUTHORIZATION);

            while (lexer.lookAhead(0) != '\n')
            {
                this.lexer.match(TokenTypes.ID);
                Token token = lexer.getNextToken();
                try {
                    mediaAuthorization.setMediaAuthorizationToken(token.getTokenValue());
                } catch (InvalidArgumentException e) {
                    throw createParseException(e.getMessage());
                }
                mediaAuthorizationList.add(mediaAuthorization);

                this.lexer.SPorHT();
                if (lexer.lookAhead(0) == ',')
                {
                    this.lexer.match(',');
                    mediaAuthorization = new PMediaAuthorization();
                }
                this.lexer.SPorHT();
            }

            return mediaAuthorizationList;

        }
        finally
        {
            if (log.isDebugEnabled())
                dbg_leave("MediaAuthorizationParser.parse");
        }

    }


}
