package com.jswitch.sip.header;

public interface PUserDatabaseHeader extends Parameters,Header
{
    public final static String NAME = "P-User-Database";

    public String getDatabaseName();

    public void setDatabaseName(String name);


}
