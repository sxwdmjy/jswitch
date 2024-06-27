package com.jswitch.sip.message;

import com.jswitch.common.exception.SipException;
import com.jswitch.sip.header.ContentLengthHeader;
import com.jswitch.sip.header.Header;

import java.util.ListIterator;

/**
 * @author danmo
 * @date 2024-06-13 13:49
 **/
public interface Message {

    public void addHeader(Header header);

    public void addLast(Header header) throws SipException, NullPointerException ;

    public void addFirst(Header header) throws SipException, NullPointerException;

    public void removeFirst(String headerName) throws NullPointerException;

    public void removeLast(String headerName) throws NullPointerException;

    public void removeHeader(String headerName);

    public ListIterator<String> getHeaderNames();

    public ListIterator getHeaders(String headerName);

    public Header getHeader(String headerName);

    public ListIterator getUnrecognizedHeaders();

    public void setHeader(Header header);

    public void setContentLength(ContentLengthHeader contentLength);
}
