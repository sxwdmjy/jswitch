package com.jswitch.sip.header;

/**
 * @author danmo
 * @date 2024-06-13 18:54
 **/
public interface ContentLengthHeader extends Header {

    public final static String NAME = "Content-Length";

    public void setContentLength(int contentLength);

    public int getContentLength();
}
