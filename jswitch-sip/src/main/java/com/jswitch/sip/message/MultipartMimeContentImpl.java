package com.jswitch.sip.message;

import com.jswitch.common.constant.Separators;
import com.jswitch.sip.header.*;

import java.text.ParseException;
import java.util.*;

public class MultipartMimeContentImpl implements MultipartMimeContent {
  public static final String BOUNDARY = "boundary";
  private List<Content> contentList = new LinkedList<Content>();
  private HeaderFactoryImpl headerFactory = new HeaderFactoryImpl();
  private ContentTypeHeader multipartMimeContentTypeHeader;
  private String boundary;


  public MultipartMimeContentImpl(ContentTypeHeader contentTypeHeader) {
    this.multipartMimeContentTypeHeader = contentTypeHeader;
    this.boundary = contentTypeHeader.getParameter(BOUNDARY);

  }


  public boolean add(Content content) {
    return contentList.add((ContentImpl) content);
  }


  public ContentTypeHeader getContentTypeHeader() {
    return multipartMimeContentTypeHeader;
  }


  @Override
  public String toString() {
    StringBuilder result = new StringBuilder();

    for (Content content : this.contentList) {
      result.append("--" + boundary + Separators.NEWLINE);
      result.append(content.toString());
      result.append(Separators.NEWLINE);
    }

    if (!contentList.isEmpty()) {
      result.append("--" + boundary + "--");
    }

    return result.toString();

  }


  public void createContentList(String body) throws ParseException {
    if (boundary != null) {
      Scanner scanner = new Scanner(body);
      scanner.useDelimiter("\r?\n?--" + boundary + "(--)?\r?\n?");
      while (scanner.hasNext()) {
    	  try {
    		  String bodyPart = scanner.next();
    		  Content partContent = parseBodyPart(bodyPart);
    		  contentList.add(partContent);
    	  } catch (NoSuchElementException e) {

    	  }
      }
    } else {

      ContentImpl content = parseBodyPart(body);
      content.setContentTypeHeader(this.getContentTypeHeader());
      this.contentList.add(content);
    }
  }

  private ContentImpl parseBodyPart(String bodyPart) throws ParseException {
    String headers[] = null;
    String bodyContent;
    
    if (bodyPart.startsWith("\n") || bodyPart.startsWith("\r\n")) {
      bodyContent = bodyPart;
    } else {

      String[] nextPartSplit = bodyPart.split("\r\n\r\n", 2);

      bodyContent = bodyPart;
      
      if (nextPartSplit.length == 2) {
        String[] potentialHeaders = nextPartSplit[0].split("\r\n");
        if (potentialHeaders[0].indexOf(":") > 0) {
          headers = potentialHeaders;
          bodyContent = nextPartSplit[1];
        }
      }
    }
    
    ContentImpl content = new ContentImpl(bodyContent);
    if (headers != null) {
      for (String partHeader : headers) {
        Header header = headerFactory.createHeader(partHeader);
        if (header instanceof ContentTypeHeader) {
          content.setContentTypeHeader((ContentTypeHeader) header);
        } else if (header instanceof ContentDispositionHeader) {
          content.setContentDispositionHeader((ContentDispositionHeader) header);
        } else {
          content.addExtensionHeader(header);
        }
      }
    }
    return content;
  }

  /*
   * (non-Javadoc)
   * 
   * @see gov.nist.javax.sip.message.MultipartMimeContentExt#setContent(java.lang.String,
   * java.lang.String, gov.nist.javax.sip.message.Content)
   */
  public void addContent(Content content) {
    this.add(content);
  }

  public Iterator<Content> getContents() {
    return this.contentList.iterator();
  }

  public int getContentCount() {
    return this.contentList.size();
  }

}
