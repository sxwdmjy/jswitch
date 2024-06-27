package com.jswitch.sip.message;

import com.jswitch.common.constant.Separators;
import com.jswitch.sip.header.ContentDispositionHeader;
import com.jswitch.sip.header.ContentTypeHeader;
import com.jswitch.sip.header.Header;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ContentImpl implements Content {


    private Object content;

    private ContentTypeHeader contentTypeHeader;

    private ContentDispositionHeader contentDispositionHeader;

    private List<Header> extensionHeaders = new ArrayList<Header>();

    public ContentImpl(String content) {
        this.content = content;
    }


    public void setContent(Object content) {
        this.content = content;
    }


    public ContentTypeHeader getContentTypeHeader() {
        return contentTypeHeader;
    }


    public Object getContent() {
        return this.content;
    }


    public String toString() {
        StringBuilder result = new StringBuilder();
        if (contentTypeHeader != null) {
            result.append(contentTypeHeader.toString());
        }

        if (contentDispositionHeader != null) {
            result.append(contentDispositionHeader.toString());
        }
        for (Header header : extensionHeaders) {
            result.append(header);
        }
        result.append(Separators.NEWLINE);
        result.append(content.toString());
        return result.toString();
    }


    public void setContentDispositionHeader(ContentDispositionHeader contentDispositionHeader) {
        this.contentDispositionHeader = contentDispositionHeader;
    }


    public ContentDispositionHeader getContentDispositionHeader() {
        return contentDispositionHeader;
    }


    public void setContentTypeHeader(ContentTypeHeader contentTypeHeader) {
        this.contentTypeHeader = contentTypeHeader;
    }

    public void addExtensionHeader(Header header) {
        this.extensionHeaders.add(header);
    }

    public Iterator<Header> getExtensionHeaders() {
        return extensionHeaders.iterator();
    }
}
