
package com.jswitch.sip.header;

import com.jswitch.common.exception.SipException;
import com.jswitch.sip.NameValue;

import java.text.ParseException;

public class PChargingVector extends ParametersHeader implements PChargingVectorHeader, SIPHeaderNamesIms, ExtensionHeader {


    public PChargingVector() {

        super(P_CHARGING_VECTOR);
    }


    public StringBuilder encodeBody(StringBuilder encoding) {
        NameValue nv = getNameValue(ParameterNamesIms.ICID_VALUE);
        if (nv != null)
            this.parameters.encode(encoding);
        else
            try {
                throw new SipException("icid-value is mandatory");
            } catch (SipException e) {
                e.printStackTrace();

            }

        return encoding;
    }

    public String getICID() {

        return getParameter(ParameterNamesIms.ICID_VALUE);
    }

    public void setICID(String icid) throws ParseException {
        if (icid == null)
            throw new NullPointerException(
                    "SIP Exception, P-Charging-Vector, setICID(), the icid parameter is null.");
        setParameter(ParameterNamesIms.ICID_VALUE, icid);

    }

    public String getICIDGeneratedAt() {
        return getParameter(ParameterNamesIms.ICID_GENERATED_AT);

    }

    public void setICIDGeneratedAt(String host) throws ParseException {
        if (host == null)
            throw new NullPointerException("SIP Exception, P-Charging-Vector, setICIDGeneratedAt(), the host parameter is null.");
        setParameter(ParameterNamesIms.ICID_GENERATED_AT, host);

    }

    public String getOriginatingIOI() {

        return getParameter(ParameterNamesIms.ORIG_IOI);
    }

    public void setOriginatingIOI(String origIOI) throws ParseException {
        if (origIOI == null || origIOI.isEmpty()) {
            removeParameter(ParameterNamesIms.ORIG_IOI);
        } else
            this.parameters.set(ParameterNamesIms.ORIG_IOI, origIOI);

    }

    public String getTerminatingIOI() {

        return getParameter(ParameterNamesIms.TERM_IOI);
    }

    public void setTerminatingIOI(String termIOI) throws ParseException {

        if (termIOI == null || termIOI.isEmpty()) {
            removeParameter(ParameterNamesIms.TERM_IOI);
        } else
            this.parameters.set(ParameterNamesIms.TERM_IOI, termIOI);

    }

    public void setValue(String value) throws ParseException {
        throw new ParseException(value, 0);

    }

}
