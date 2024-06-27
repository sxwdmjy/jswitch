package com.jswitch.sip.header;

import com.jswitch.sip.core.Token;

import java.text.ParseException;

import static com.jswitch.common.constant.Separators.DOUBLE_QUOTE;
import static com.jswitch.common.constant.Separators.SEMICOLON;

/**
 * P-Visited-Network-ID SIP Private Header: RFC 3455.
 *
 */
public class PVisitedNetworkID extends ParametersHeader implements PVisitedNetworkIDHeader, SIPHeaderNamesIms, ExtensionHeader {


    private String networkID;

    private boolean isQuoted;

    public PVisitedNetworkID() {

        super(P_VISITED_NETWORK_ID);

    }

    public PVisitedNetworkID(String networkID) {

        super(P_VISITED_NETWORK_ID);
        setVisitedNetworkID(networkID);

    }

    public PVisitedNetworkID(Token tok) {
        super(P_VISITED_NETWORK_ID);
        setVisitedNetworkID(tok.getTokenValue());

    }

    public StringBuilder encodeBody(StringBuilder retval) {


        if (getVisitedNetworkID() != null)
        {
            if (isQuoted)
                retval.append(DOUBLE_QUOTE).append(getVisitedNetworkID()).append(DOUBLE_QUOTE);
            else
                retval.append(getVisitedNetworkID());
        }

        if (!parameters.isEmpty()) {
            retval.append(SEMICOLON);
            this.parameters.encode(retval);
        }

        return retval;

    }


    public void setVisitedNetworkID(String networkID) {
        if (networkID == null)
            throw new NullPointerException(" the networkID parameter is null");

        this.networkID = networkID;

        this.isQuoted = true;
    }


    public void setVisitedNetworkID(Token networkID) {
        if (networkID == null)
            throw new NullPointerException(" the networkID parameter is null");

        this.networkID = networkID.getTokenValue();

        this.isQuoted = false;
    }

    public String getVisitedNetworkID() {
        return networkID;
    }


    public void setValue(String value) throws ParseException {
        throw new ParseException (value,0);

    }


    public boolean equals(Object other)
    {
        if (other instanceof PVisitedNetworkIDHeader)
        {
            PVisitedNetworkIDHeader o = (PVisitedNetworkIDHeader) other;
            return (this.getVisitedNetworkID().equals( o.getVisitedNetworkID() )
                && this.equalParameters( (Parameters) o ));
        }
        return false;
    }


    public Object clone() {
        PVisitedNetworkID retval = (PVisitedNetworkID) super.clone();
        if (this.networkID != null)
            retval.networkID = this.networkID;
        retval.isQuoted = this.isQuoted;
        return retval;
    }


}
