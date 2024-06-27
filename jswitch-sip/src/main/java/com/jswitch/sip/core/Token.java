package com.jswitch.sip.core;

public class Token {
    protected String tokenValue;
    protected int tokenType;

    public String getTokenValue() {
        return this.tokenValue;
    }

    public int getTokenType() {
        return this.tokenType;
    }

    public String toString() {
        return "tokenValue = " + tokenValue + "/tokenType = " + tokenType;
    }
}
