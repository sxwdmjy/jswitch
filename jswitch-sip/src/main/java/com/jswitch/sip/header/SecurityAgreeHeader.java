
package com.jswitch.sip.header;


import com.jswitch.common.exception.InvalidArgumentException;

import java.text.ParseException;

public interface SecurityAgreeHeader extends Parameters, Header
{

    public void setSecurityMechanism(String secMech) throws ParseException;


    public void setEncryptionAlgorithm(String ealg) throws ParseException;


    public void setAlgorithm(String alg) throws ParseException;


    public void setProtocol(String prot) throws ParseException;


    public void setMode(String mod) throws ParseException;


    public void setSPIClient(int spic) throws InvalidArgumentException;


    public void setSPIServer(int spis) throws InvalidArgumentException;


    public void setPortClient(int portC) throws InvalidArgumentException;



    public void setPortServer(int portS) throws InvalidArgumentException;


    public void setPreference(float q) throws InvalidArgumentException;


    public String getSecurityMechanism();


    public String getEncryptionAlgorithm();


    public String getAlgorithm();


    public String getProtocol();


    public String getMode();


    public int getSPIClient();


    public int getSPIServer();


    public int getPortClient();


    public int getPortServer();


    public float getPreference();

}
