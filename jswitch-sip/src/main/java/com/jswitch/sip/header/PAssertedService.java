package com.jswitch.sip.header;


import java.text.ParseException;


public class PAssertedService extends SIPHeader implements PAssertedServiceHeader, SIPHeaderNamesIms, ExtensionHeader{

    private String subServiceIds;
    private String subAppIds;

    protected PAssertedService(String name) {
        super(NAME);
    }

    public PAssertedService()
    {
        super(P_ASSERTED_SERVICE);
    }

    @Override
    public StringBuilder encodeBody(StringBuilder retval) {

         retval.append(ParameterNamesIms.SERVICE_ID);

            if(this.subServiceIds!=null)
            {
                retval.append(ParameterNamesIms.SERVICE_ID_LABEL).append(".");

            retval.append(this.getSubserviceIdentifiers());
            }

            else if(this.subAppIds!=null)
            {
                retval.append(ParameterNamesIms.APPLICATION_ID_LABEL).append(".");
                retval.append(this.getApplicationIdentifiers());
            }

        return retval;
    }

    public void setValue(String value) throws ParseException {
        throw new ParseException(value,0);

    }

    public String getApplicationIdentifiers() {
        if(this.subAppIds.charAt(0)=='.')
        {
            return this.subAppIds.substring(1).trim();
        }
        return this.subAppIds;
    }

    public String getSubserviceIdentifiers() {
        if(this.subServiceIds.charAt(0)=='.')
        {
            return this.subServiceIds.substring(1).trim();
        }
        return this.subServiceIds;
    }
    public void setApplicationIdentifiers(String appids) {
        this.subAppIds = appids;

    }

    public void setSubserviceIdentifiers(String subservices) {
        this.subServiceIds = subservices;

    }

    public boolean equals(Object other)
    {
        return (other instanceof PAssertedServiceHeader) && super.equals(other);

    }


    public Object clone() {
        return (PAssertedService) super.clone();
    }


}
