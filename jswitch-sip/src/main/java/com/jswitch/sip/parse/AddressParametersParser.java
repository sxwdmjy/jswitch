
package com.jswitch.sip.parse;

import com.jswitch.sip.adress.AddressImpl;
import com.jswitch.sip.header.AddressParametersHeader;

import java.text.ParseException;

public class AddressParametersParser extends ParametersParser {

	protected boolean allowParameters = true;
	
    protected AddressParametersParser(Lexer lexer) {
        super(lexer);
    }

    protected AddressParametersParser(String buffer) {
        super(buffer);
    }

    protected void parse(AddressParametersHeader addressParametersHeader)
        throws ParseException {
        dbg_enter("AddressParametersParser.parse");
        try {
            AddressParser addressParser = new AddressParser(this.getLexer());
            AddressImpl addr = addressParser.address(false);
            addressParametersHeader.setAddress(addr);
            lexer.SPorHT();
            char la = this.lexer.lookAhead(0);
            if ( this.lexer.hasMoreChars() &&
                 la != '\0' &&
                 la != '\n' &&
                 this.lexer.startsId()) {

                 super.parseNameValueList(addressParametersHeader);


            }  else {
            	if (this.allowParameters == false) {
            	   lexer.SPorHT();            	
            	   if (this.lexer.lookAhead(0) == ';') {            		
                		throw new ParseException(this.lexer.getBuffer() +  "is not valid. This header doesn't allow parameters" , this.lexer.getPtr());
                	}
            	}
            	super.parse(addressParametersHeader);
            }

        } catch (ParseException ex) {
            throw ex;
        } finally {
            dbg_leave("AddressParametersParser.parse");
        }
    }
}
