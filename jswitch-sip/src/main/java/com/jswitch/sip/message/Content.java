package com.jswitch.sip.message;


import com.jswitch.sip.header.ContentDispositionHeader;
import com.jswitch.sip.header.ContentTypeHeader;
import com.jswitch.sip.header.Header;

import java.util.Iterator;

public interface Content {

  public abstract void setContent(Object content);

  public abstract Object getContent();

  public abstract ContentTypeHeader getContentTypeHeader();

  public abstract ContentDispositionHeader getContentDispositionHeader();

  public abstract Iterator<Header> getExtensionHeaders();
  

  public abstract String toString();


}
