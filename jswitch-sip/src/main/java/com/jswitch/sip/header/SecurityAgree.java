package com.jswitch.sip.header;


import com.jswitch.common.constant.Separators;
import com.jswitch.common.exception.InvalidArgumentException;
import com.jswitch.sip.NameValue;

import java.text.ParseException;

import static com.jswitch.common.constant.Separators.SEMICOLON;
import static com.jswitch.common.constant.Separators.SP;

public abstract class SecurityAgree extends ParametersHeader
{

    private String secMechanism;



    public SecurityAgree(String name)
    {
        super(name);
        parameters.setSeparator(SEMICOLON);
    }


    public SecurityAgree()
    {
        super();
        parameters.setSeparator(SEMICOLON);
    }


    public void setParameter(String name, String value) throws ParseException
    {
        if (value == null)
            throw new NullPointerException("null value");

        NameValue nv = super.parameters.getNameValue(name.toLowerCase());
        if (nv == null)
        {
            nv = new NameValue(name, value);

            if (name.equalsIgnoreCase(ParameterNamesIms.D_VER))
            {
                nv.setQuotedValue();

                if (value.startsWith(Separators.DOUBLE_QUOTE))
                    throw new ParseException(value
                            + " : Unexpected DOUBLE_QUOTE", 0);
            }

            super.setParameter(nv);
        }
        else
        {
            nv.setValueAsObject(value);
        }

    }

    public StringBuilder encodeBody(StringBuilder retval) {
    	retval.append(this.secMechanism).append(SEMICOLON).append(SP);
    	return parameters.encode(retval);
    }


    public void setSecurityMechanism(String secMech) throws ParseException {
        if (secMech == null)
            throw new NullPointerException("SIP Exception, SecurityAgree, setSecurityMechanism(), the sec-mechanism parameter is null");
        this.secMechanism = secMech;
    }


    public void setEncryptionAlgorithm(String ealg) throws ParseException {
        if (ealg == null)
            throw new NullPointerException("SIP Exception, SecurityClient, setEncryptionAlgorithm(), the encryption-algorithm parameter is null");
        setParameter(ParameterNamesIms.EALG, ealg);
    }


    public void setAlgorithm(String alg) throws ParseException {
        if (alg == null)
            throw new NullPointerException("SIP Exception, SecurityClient, setAlgorithm(), the algorithm parameter is null");
        setParameter(ParameterNamesIms.ALG, alg);
    }

    public void setProtocol(String prot) throws ParseException {
        if (prot == null)
            throw new NullPointerException("SIP Exception, SecurityClient, setProtocol(), the protocol parameter is null");
        setParameter(ParameterNamesIms.PROT, prot);
    }


    public void setMode(String mod) throws ParseException {
        if (mod == null)
            throw new NullPointerException("SIP Exception, SecurityClient, setMode(), the mode parameter is null");
        setParameter(ParameterNamesIms.MOD, mod);
    }

    public void setSPIClient(int spic) throws InvalidArgumentException {
        if (spic < 0)
            throw new InvalidArgumentException("SIP Exception, SecurityClient, setSPIClient(), the spi-c parameter is <0");
        setParameter(ParameterNamesIms.SPI_C, spic);
    }


    public void setSPIServer(int spis) throws InvalidArgumentException {
        if (spis < 0)
            throw new InvalidArgumentException("SIP Exception, SecurityClient, setSPIServer(), the spi-s parameter is <0");
        setParameter(ParameterNamesIms.SPI_S, spis);
    }

    public void setPortClient(int portC) throws InvalidArgumentException {
        if (portC < 0)
            throw new InvalidArgumentException("SIP Exception, SecurityClient, setPortClient(), the port-c parameter is <0");
        setParameter(ParameterNamesIms.PORT_C, portC);
    }


    public void setPortServer(int portS) throws InvalidArgumentException {
        if (portS < 0)
            throw new InvalidArgumentException("SIP Exception, SecurityClient, setPortServer(), the port-s parameter is <0");
        setParameter(ParameterNamesIms.PORT_S, portS);
    }

    public void setPreference(float q) throws InvalidArgumentException {
        if (q < 0.0f)
            throw new InvalidArgumentException(
                "SIP Exception, SecurityClient, setPreference(), the preference (q) parameter is <0");
        setParameter(ParameterNamesIms.Q, q);
    }

    public String getSecurityMechanism() {
        return this.secMechanism;
    }

    public String getEncryptionAlgorithm() {
        return getParameter(ParameterNamesIms.EALG);
    }


    public String getAlgorithm() {
        return getParameter(ParameterNamesIms.ALG);
    }


    public String getProtocol() {
        return getParameter(ParameterNamesIms.PROT);
    }


    public String getMode() {
        return getParameter(ParameterNamesIms.MOD);

    }

    public int getSPIClient() {
        return (Integer.parseInt(getParameter(ParameterNamesIms.SPI_C)));
    }


    public int getSPIServer() {
        return (Integer.parseInt(getParameter(ParameterNamesIms.SPI_S)));
    }


    public int getPortClient() {
        return (Integer.parseInt(getParameter(ParameterNamesIms.PORT_C)));
    }


    public int getPortServer() {
        return (Integer.parseInt(getParameter(ParameterNamesIms.PORT_S)));
    }


    public float getPreference() {
        return (Float.parseFloat(getParameter(ParameterNamesIms.Q)));
    }


    public boolean equals(Object other)
    {
        if(other instanceof SecurityAgreeHeader)
        {
            SecurityAgreeHeader o = (SecurityAgreeHeader) other;
            return (this.getSecurityMechanism().equals( o.getSecurityMechanism() )
                && this.equalParameters( (Parameters) o ));
        }
        return false;

    }


    public Object clone() {
        SecurityAgree retval = (SecurityAgree) super.clone();
        if (this.secMechanism != null)
            retval.secMechanism = this.secMechanism;
        return retval;
    }


}


