package com.jswitch.sip;

import com.jswitch.common.exception.InvalidArgumentException;
import com.jswitch.common.exception.SipException;

/**
 * @author danmo
 * @date 2024-06-13 13:57
 **/
public interface ServerTransaction extends Transaction{

    public void sendResponse(Response response) throws SipException, InvalidArgumentException;
}
