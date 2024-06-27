package com.jswitch.sip.header;


import lombok.Getter;

import java.text.ParseException;

import static com.jswitch.common.constant.Separators.SEMICOLON;

@Getter
public class PUserDatabase extends ParametersHeader implements PUserDatabaseHeader,SIPHeaderNamesIms, ExtensionHeader{

    private String databaseName;


    public PUserDatabase(String databaseName)
    {
        super(NAME);
        this.databaseName = databaseName;
    }


    public PUserDatabase() {
        super(P_USER_DATABASE);
    }


    public void setDatabaseName(String databaseName) {
        if((databaseName==null)||(databaseName.equals(" ")))
            throw new NullPointerException("Database name is null");
        else
            if(!databaseName.contains("aaa://"))
        this.databaseName = new StringBuilder().append("aaa://").append(databaseName).toString();
            else
                this.databaseName = databaseName;

    }

    public StringBuilder encodeBody(StringBuilder retval) {

        retval.append("<");
        if(getDatabaseName()!=null)
        retval.append(getDatabaseName());

        if (!parameters.isEmpty()) {
            retval.append(SEMICOLON);
            this.parameters.encode(retval);
        }
        retval.append(">");

        return retval;
    }

    public boolean equals(Object other)
    {
        return (other instanceof PUserDatabaseHeader) && super.equals(other);

    }


    public Object clone() {
        PUserDatabase retval = (PUserDatabase) super.clone();
        return retval;
    }

    public void setValue(String value) throws ParseException {
        throw new ParseException(value,0);

    }



}
