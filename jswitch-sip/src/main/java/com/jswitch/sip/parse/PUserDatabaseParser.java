package com.jswitch.sip.parse;


import com.jswitch.sip.header.PUserDatabase;
import com.jswitch.sip.header.SIPHeader;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;

@Slf4j
public class PUserDatabaseParser extends ParametersParser implements TokenTypes{

    public PUserDatabaseParser(String databaseName)
    {
        super(databaseName);
    }


    public PUserDatabaseParser(Lexer lexer)
    {
        super(lexer);
    }

    public SIPHeader parse() throws ParseException {

        if (log.isDebugEnabled())
            dbg_enter("PUserDatabase.parse");

        try{
            this.lexer.match(TokenTypes.P_USER_DATABASE);
            this.lexer.SPorHT();
            this.lexer.match(':');
            this.lexer.SPorHT();

            PUserDatabase userDatabase = new PUserDatabase();
            this.parseheader(userDatabase);

             return userDatabase;
        }
        finally{
            if(log.isDebugEnabled())
            dbg_leave("PUserDatabase.parse");
        }
    }

    private void parseheader(PUserDatabase userDatabase) throws ParseException
    {
        StringBuilder dbname = new StringBuilder();
        this.lexer.match(LESS_THAN);

        while(this.lexer.hasMoreChars())
        {
            char next = this.lexer.getNextChar();
          if (next!='>'&&next!='\n')
          {
          dbname.append(next);
          }
         }
        userDatabase.setDatabaseName(dbname.toString());
          super.parse(userDatabase);

}
}
