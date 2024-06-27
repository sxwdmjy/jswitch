package com.jswitch.sip.header;

import com.jswitch.common.exception.InvalidArgumentException;

/**
 * @author danmo
 * @date 2024-06-18 18:41
 **/
public interface SessionExpiresHeader extends Parameters, Header, ExtensionHeader {

    public final static String NAME = "Session-Expires";

    public int getExpires();

    public void setExpires(int expires) throws InvalidArgumentException;

    public String getRefresher();

    public void setRefresher(String refresher);
}