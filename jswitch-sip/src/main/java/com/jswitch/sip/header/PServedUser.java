package com.jswitch.sip.header;


import com.jswitch.common.exception.InvalidArgumentException;
import com.jswitch.sip.adress.AddressImpl;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;

import static com.jswitch.common.constant.Separators.*;

@Slf4j
public class PServedUser extends AddressParametersHeader implements PServedUserHeader, SIPHeaderNamesIms, ExtensionHeader{


    public PServedUser(AddressImpl address)
    {
        super(P_SERVED_USER);
        this.address = address;
    }

    public PServedUser()
    {
        super(NAME);
    }

    public String getRegistrationState() {

        return getParameter(ParameterNamesIms.REGISTRATION_STATE);
    }

    public String getSessionCase() {

        return getParameter(ParameterNamesIms.SESSION_CASE);
    }

    public void setRegistrationState(String registrationState) {

        if((registrationState!=null))
        {
            if(registrationState.equals("reg")||registrationState.equals("unreg"))
            {
                try {
                    setParameter(ParameterNamesIms.REGISTRATION_STATE, registrationState);
                } catch (ParseException e) {
                    log.error("setRegistrationState ParseException:{}",e.getMessage(),e);
                }

            }
              else
              {
                  try {
                      throw new InvalidArgumentException("Value can be either reg or unreg");
                  } catch (InvalidArgumentException e) {
                         log.error("setRegistrationState InvalidArgumentException:{}",e.getMessage(),e);
                    }
              }

        }
        else
        {
            throw new NullPointerException("regstate Parameter value is null");
        }

    }

    public void setSessionCase(String sessionCase) {

        if((sessionCase!=null))
        {
            if((sessionCase.equals("orig"))||(sessionCase.equals("term")))
            {
                try {
                    setParameter(ParameterNamesIms.SESSION_CASE, sessionCase);
                } catch (ParseException e) {
                    log.error("setSessionCase ParseException:{}",e.getMessage(),e);
                }
            }
              else
              {
                  try {
                    throw new InvalidArgumentException("Value can be either orig or term");
                } catch (InvalidArgumentException e) {
                   log.error("setSessionCase InvalidArgumentException:{}",e.getMessage(),e);
                }

              }
        }
        else
        {
            throw new NullPointerException("sess-case Parameter value is null");
        }

    }

    @Override
    public StringBuilder encodeBody(StringBuilder retval) {

        retval.append(address.encode());

        if(parameters.containsKey(ParameterNamesIms.REGISTRATION_STATE))
            retval.append(SEMICOLON).append(ParameterNamesIms.REGISTRATION_STATE).append(EQUALS)
            .append(this.getRegistrationState());

        if(parameters.containsKey(ParameterNamesIms.SESSION_CASE))
            retval.append(SEMICOLON).append(ParameterNamesIms.SESSION_CASE).append(EQUALS)
            .append(this.getSessionCase());

        return retval;
    }

    public void setValue(String value) throws ParseException {
        throw new ParseException(value,0);

    }

    public boolean equals(Object other)
    {
         if(other instanceof PServedUser)
         {
            final PServedUserHeader psu = (PServedUserHeader)other;
            return this.getAddress().equals(((PServedUser) other).getAddress());
         }
        return false;
    }


    public Object clone() {
        return (PServedUser) super.clone();
    }

}
