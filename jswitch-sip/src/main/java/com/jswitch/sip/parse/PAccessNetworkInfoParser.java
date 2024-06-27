
package com.jswitch.sip.parse;

import com.jswitch.sip.NameValue;
import com.jswitch.sip.core.Token;
import com.jswitch.sip.header.PAccessNetworkInfo;
import com.jswitch.sip.header.PAccessNetworkInfoList;
import com.jswitch.sip.header.SIPHeader;
import com.jswitch.sip.header.SIPHeaderNamesIms;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;

@Slf4j
public class PAccessNetworkInfoParser extends HeaderParser implements TokenTypes
{

    public PAccessNetworkInfoParser(String accessNetwork) {

        super(accessNetwork);

    }


    protected PAccessNetworkInfoParser(Lexer lexer) {
        super(lexer);

    }


    public SIPHeader parse() throws ParseException
    {

        if (log.isDebugEnabled())
            dbg_enter("AccessNetworkInfoParser.parse");

        PAccessNetworkInfoList accessNetworkInfoList = new PAccessNetworkInfoList();

        try {
            headerName(TokenTypes.P_ACCESS_NETWORK_INFO);
            while (true) {
                PAccessNetworkInfo accessNetworkInfo = new PAccessNetworkInfo();
                accessNetworkInfo.setHeaderName(SIPHeaderNamesIms.P_ACCESS_NETWORK_INFO);

                this.lexer.SPorHT();
                lexer.match(TokenTypes.ID);
                Token token = lexer.getNextToken();
                accessNetworkInfo.setAccessType(token.getTokenValue());
                this.lexer.SPorHT();
                while (lexer.lookAhead(0) == ';') {
                    this.lexer.match(';');
                    this.lexer.SPorHT();

                    try {
                        NameValue nv = super.nameValue('=');
                        accessNetworkInfo.setParameter(nv);
                    } catch (ParseException e) {
                        this.lexer.SPorHT();
                        String ext = this.lexer.quotedString();
                        if (ext == null) {
                            ext = this.lexer.ttokenGenValue();
                        } else {
                            ext = "\"" + ext + "\"";
                        }
                        accessNetworkInfo.setExtensionAccessInfo(ext);
                    }
                    this.lexer.SPorHT();
                }
                accessNetworkInfoList.add(accessNetworkInfo);
                this.lexer.SPorHT();
                char la = lexer.lookAhead(0);
                if (la == ',') {
                    this.lexer.match(',');
                    this.lexer.SPorHT();
                } else if (la == '\n')
                    break;
                else
                    throw createParseException("unexpected char");
            }
            return accessNetworkInfoList;
        } finally {
            if (log.isDebugEnabled())
                dbg_leave("AccessNetworkInfoParser.parse");
        }

    }




}
