package com.jswitch.sip;

import com.jswitch.common.exception.SipException;

/**
 * @author danmo
 * @date 2024-06-13 13:57
 **/
public interface ClientTransaction extends Transaction {

    public void sendRequest() throws SipException;

    public Request createCancel() throws SipException;

    public Request createAck() throws SipException;
}
