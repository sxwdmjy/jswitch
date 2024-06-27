package com.jswitch.sip.message;

import com.jswitch.sip.header.ContentTypeHeader;

import java.util.Iterator;


public interface MultipartMimeContent {

    public abstract boolean add(Content content);


    public abstract ContentTypeHeader getContentTypeHeader();

    public abstract String toString();


    public abstract void addContent(Content content);
    

    public Iterator<Content> getContents();
    

    public int getContentCount();

}
