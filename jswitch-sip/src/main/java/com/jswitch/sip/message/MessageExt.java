package com.jswitch.sip.message;

import com.jswitch.sip.header.ToHeader;
import com.jswitch.sip.header.ViaHeader;
import com.jswitch.sip.header.*;

import java.text.ParseException;

/**
 * @author danmo
 * @date 2024-06-18 13:51
 **/
public interface MessageExt extends Message {

    public void setApplicationData (Object applicationData);


    public Object getApplicationData();


    public MultipartMimeContent getMultipartMimeContent() throws ParseException;


    public ViaHeader getTopmostViaHeader();

    public FromHeader getFromHeader();


    public ToHeader getToHeader();



    public CallIdHeader getCallIdHeader();


    public CSeqHeader getCSeqHeader();


    public ContentTypeHeader getContentTypeHeader();


    public ContentLengthHeader getContentLengthHeader();


    public String getFirstLine();
}
