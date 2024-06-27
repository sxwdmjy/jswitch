
package com.jswitch.sip.core;

import com.jswitch.sip.NameValue;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;


@Slf4j
public abstract class ParserCore {

    static int nesting_level;

    protected LexerCore lexer;


    protected NameValue nameValue(char separator) throws ParseException  {
        if (log.isDebugEnabled()) dbg_enter("nameValue");
        try {

        lexer.match(LexerCore.ID);
        Token name = lexer.getNextToken();
        // eat white space.
        lexer.SPorHT();
        try {


                boolean quoted = false;

            char la = lexer.lookAhead(0);

            if (la == separator ) {
                lexer.consume(1);
                lexer.SPorHT();
                String str = null;
                boolean isFlag = false;
                char c = lexer.lookAhead(0);
                if (c == '\"')  {
                     str = lexer.quotedString();
                     quoted = true;
                } else if (c == '['){
                    lexer.match(LexerCore.IPV6);
                    Token value = lexer.getNextToken();
                    str = value.tokenValue;

                    if (str==null) {
                        str = "";
                        isFlag = true;
                    }
                }else {
                   lexer.match(LexerCore.ID);
                   Token value = lexer.getNextToken();
                   str = value.tokenValue;

                   if (str==null) {
                       str = "";
                       isFlag = true;
                   }
                }
                NameValue nv = new NameValue(name.tokenValue,str,isFlag);
                if (quoted) nv.setQuotedValue();
                return nv;
            }  else {
                return new NameValue(name.tokenValue,"",true);
            }
        } catch (ParseException ex) {
            return new NameValue(name.tokenValue,null,false);
        }

        } finally {
            if (log.isDebugEnabled()) dbg_leave("nameValue");
        }


    }

    protected  void dbg_enter(String rule) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < nesting_level ; i++)
            stringBuilder.append(">");

        if (log.isDebugEnabled())  {
            log.debug(stringBuilder + rule + "\nlexer buffer = \n" + lexer.getRest());
        }
        nesting_level++;
    }

    protected void dbg_leave(String rule) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < nesting_level ; i++)
            stringBuilder.append("<");

        if (log.isDebugEnabled())  {
            log.debug(stringBuilder + rule + "\nlexer buffer = \n" + lexer.getRest());
        }
        nesting_level --;
    }

    protected NameValue nameValue() throws ParseException  {
        return nameValue('=');
    }



    protected void peekLine(String rule) {
        if (log.isDebugEnabled()) {
            log.debug(rule +" " + lexer.peekLine());
        }
    }
}


