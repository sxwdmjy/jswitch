package com.jswitch.sip.parse;

import com.jswitch.sip.NameValue;
import com.jswitch.sip.core.Token;
import com.jswitch.sip.header.*;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;

@Slf4j
public class SecurityAgreeParser extends HeaderParser
{

    public SecurityAgreeParser(String security) {
        super(security);
    }


    protected SecurityAgreeParser(Lexer lexer) {
        super(lexer);
    }


    protected void parseParameter(SecurityAgree header) throws ParseException
    {
        if (log.isDebugEnabled())
            dbg_enter("parseParameter");
        try {
            NameValue nv = this.nameValue('=');
            header.setParameter(nv);
        } finally {
            if (log.isDebugEnabled())
                dbg_leave("parseParameter");
        }
    }


    public SIPHeaderList parse(SecurityAgree header) throws ParseException
    {

        SIPHeaderList list;

        if (header.getClass().isInstance(new SecurityClient())) {
            list = new SecurityClientList();
        } else if (header.getClass().isInstance(new SecurityServer())) {
            list = new SecurityServerList();
        } else if (header.getClass().isInstance(new SecurityVerify())) {
            list = new SecurityVerifyList();
        }
        else
            return null;


        // the security-mechanism:
        this.lexer.SPorHT();
        lexer.match(TokenTypes.ID);
        Token type = lexer.getNextToken();
        header.setSecurityMechanism(type.getTokenValue());
        this.lexer.SPorHT();

        char la = lexer.lookAhead(0);
        if (la == '\n')
        {
            list.add(header);
            return list;
        }
        else if (la == ';')
            this.lexer.match(';');

        this.lexer.SPorHT();

        // The parameters:
        try {
            while (lexer.lookAhead(0) != '\n') {

                this.parseParameter(header);
                this.lexer.SPorHT();
                char laInLoop = lexer.lookAhead(0);
                if (laInLoop == '\n' || laInLoop == '\0')
                    break;
                else if (laInLoop == ',')
                {
                    list.add(header);
                    if (header.getClass().isInstance(new SecurityClient())) {
                        header = new SecurityClient();
                    } else if (header.getClass().isInstance(new SecurityServer())) {
                        header = new SecurityServer();
                    } else if (header.getClass().isInstance(new SecurityVerify())) {
                        header = new SecurityVerify();
                    }

                    this.lexer.match(',');
                    // the security-mechanism:
                    this.lexer.SPorHT();
                    lexer.match(TokenTypes.ID);
                    type = lexer.getNextToken();
                    header.setSecurityMechanism(type.getTokenValue());

                }
                this.lexer.SPorHT();

                if (lexer.lookAhead(0) == ';')
                    this.lexer.match(';');

                this.lexer.SPorHT();

            }
            list.add(header);

            return list;

        } catch (ParseException ex) {
            throw ex;
        }


    }




}


