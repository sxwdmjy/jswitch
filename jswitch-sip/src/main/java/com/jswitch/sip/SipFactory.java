package com.jswitch.sip;

import com.jswitch.sip.adress.AddressFactory;
import com.jswitch.sip.header.HeaderFactory;
import org.apache.logging.log4j.message.MessageFactory;

/**
 * @author danmo
 * @date 2024-06-18 11:34
 **/
public class SipFactory {

    private MessageFactory messageFactory = null;

    private HeaderFactory headerFactory = null;

    private AddressFactory addressFactory = null;

    private static volatile SipFactory instance = null;

    private SipFactory() {
    }

    public static SipFactory getInstance() {
        if (instance == null) {
            synchronized (SipFactory.class) {
                if (instance == null) {
                    instance = new SipFactory();
                }
            }
        }
        return instance;
    }


}
