
package com.jswitch.sip.header;

import java.text.ParseException;

import static com.jswitch.common.constant.Separators.SEMICOLON;
import static com.jswitch.common.constant.Separators.SP;

public class PAccessNetworkInfo extends ParametersHeader implements PAccessNetworkInfoHeader, ExtensionHeader {


    private String accessType;

    private Object extendAccessInfo;


    public PAccessNetworkInfo() {
        super(PAccessNetworkInfoHeader.NAME);
        parameters.setSeparator(SEMICOLON);
    }


    public PAccessNetworkInfo(String accessTypeVal) {
        this();
        setAccessType(accessTypeVal);
    }

    public void setAccessType(String accessTypeVal) {
        if (accessTypeVal == null)
            throw new NullPointerException("SIP Exception, P-Access-Network-Info, setAccessType(), the accessType parameter is null.");
        this.accessType = accessTypeVal;
    }

    public String getAccessType() {
        return accessType;
    }


    public void setCGI3GPP(String cgi) throws ParseException {
        if (cgi == null)
            throw new NullPointerException("SIP Exception, P-Access-Network-Info, setCGI3GPP(), the cgi parameter is null.");
        setParameter(ParameterNamesIms.CGI_3GPP, cgi);

    }


    public String getCGI3GPP() {
        return getParameter(ParameterNamesIms.CGI_3GPP);
    }

    public void setUtranCellID3GPP(String utranCellID) throws ParseException {

        if (utranCellID == null)
            throw new NullPointerException("SIP Exception, P-Access-Network-Info, setUtranCellID3GPP(), the utranCellID parameter is null.");
        setParameter(ParameterNamesIms.UTRAN_CELL_ID_3GPP, utranCellID);

    }

    public String getUtranCellID3GPP() {
        return getParameter(ParameterNamesIms.UTRAN_CELL_ID_3GPP);
    }


    public void setDSLLocation(String dslLocation) throws ParseException {

        if (dslLocation == null)
            throw new NullPointerException("SIP Exception, P-Access-Network-Info, setDSLLocation(), the dslLocation parameter is null.");
        setParameter(ParameterNamesIms.DSL_LOCATION, dslLocation);

    }


    public String getDSLLocation() {
        return getParameter(ParameterNamesIms.DSL_LOCATION);
    }


    public void setCI3GPP2(String ci3Gpp2) throws ParseException {
        if (ci3Gpp2 == null)
            throw new NullPointerException("SIP Exception, P-Access-Network-Info, setCI3GPP2(), the ci3Gpp2 parameter is null.");
        setParameter(ParameterNamesIms.CI_3GPP2, ci3Gpp2);
    }


    public String getCI3GPP2() {
        return getParameter(ParameterNamesIms.CI_3GPP2);
    }


    public void setParameter(String name, Object value) {
        if (name.equalsIgnoreCase(ParameterNamesIms.CGI_3GPP)
                || name.equalsIgnoreCase(ParameterNamesIms.UTRAN_CELL_ID_3GPP)
                || name.equalsIgnoreCase(ParameterNamesIms.DSL_LOCATION)
                || name.equalsIgnoreCase(ParameterNamesIms.CI_3GPP2)) {
            try {
                super.setQuotedParameter(name, value.toString());
            } catch (ParseException e) {

            }
        } else {
            super.setParameter(name, value);

        }

    }


    public void setExtensionAccessInfo(Object extendAccessInfo)
            throws ParseException {

        if (extendAccessInfo == null)
            throw new NullPointerException("SIP Exception, P-Access-Network-Info, setExtendAccessInfo(), the extendAccessInfo parameter is null.");
        this.extendAccessInfo = extendAccessInfo;

    }

    public Object getExtensionAccessInfo() {
        return this.extendAccessInfo;
    }

    public StringBuilder encodeBody(StringBuilder encoding) {

        if (getAccessType() != null)
            encoding.append(getAccessType());

        if (!parameters.isEmpty()) {
            encoding.append(SEMICOLON).append(SP);
            encoding = this.parameters.encode(encoding);
        }
        if (getExtensionAccessInfo() != null) {
            encoding.append(SEMICOLON).append(SP).append(getExtensionAccessInfo().toString());
        }
        return encoding;

    }

    public void setValue(String value) throws ParseException {
        throw new ParseException(value, 0);

    }


    public boolean equals(Object other) {
        return (other instanceof PAccessNetworkInfoHeader) && super.equals(other);
    }


    public Object clone() {
        PAccessNetworkInfo retval = (PAccessNetworkInfo) super.clone();
        return retval;
    }

}
