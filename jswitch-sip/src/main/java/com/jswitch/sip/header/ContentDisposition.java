package com.jswitch.sip.header;


import java.text.ParseException;

import static com.jswitch.common.constant.Separators.SEMICOLON;

public final class ContentDisposition extends ParametersHeader implements ContentDispositionHeader {


    private static final long serialVersionUID = 835596496276127003L;

    protected String dispositionType;


    public ContentDisposition() {
        super(NAME);
    }


    public StringBuilder encodeBody(StringBuilder encoding) {
        encoding.append(dispositionType);
        if (!this.parameters.isEmpty()) {
            encoding.append(SEMICOLON).append(parameters.encode());
        }
        return encoding;
    }


    public void setDispositionType(String dispositionType) throws ParseException {
        if (dispositionType == null)
            throw new NullPointerException("SIP Exception, ContentDisposition, setDispositionType(), the dispositionType parameter is null");
        this.dispositionType = dispositionType;
    }


    public String getDispositionType() {
        return this.dispositionType;
    }


    public String getHandling() {
        return this.getParameter("handling");
    }


    public void setHandling(String handling) throws ParseException {
        if (handling == null)
            throw new NullPointerException("SIP Exception , ContentDisposition, setHandling(), the handling parameter is null");
        this.setParameter("handling", handling);
    }


    public String getContentDisposition() {
        return this.encodeBody(new StringBuilder()).toString();
    }
}

