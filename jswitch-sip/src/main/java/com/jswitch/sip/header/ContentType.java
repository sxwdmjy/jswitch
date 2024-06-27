package com.jswitch.sip.header;

import lombok.Getter;

import java.text.ParseException;

import static com.jswitch.common.constant.Separators.SEMICOLON;

/**
 * @author danmo
 * @date 2024-06-18 15:21
 **/
@Getter
public class ContentType extends ParametersHeader implements ContentTypeHeader {

    private static final long serialVersionUID = 8475682204373446610L;
    /**
     * mediaRange field.
     */
    protected MediaRange mediaRange;


    public ContentType() {
        super(ContentTypeHeader.NAME);
    }


    public ContentType(String contentType, String contentSubtype) {
        this();
        this.setContentType(contentType, contentSubtype);
    }


    public int compareMediaRange(String media) {
        return (mediaRange.type + "/" + mediaRange.subtype).compareToIgnoreCase(media);
    }


    public String encodeBody() {
        return encodeBody(new StringBuilder()).toString();
    }

    public StringBuilder encodeBody(StringBuilder buffer) {
        mediaRange.encode(buffer);
        if (hasParameters()) {
            buffer.append(SEMICOLON);
            parameters.encode(buffer);
        }
        return buffer;
    }


    public String getMediaType() {
        return mediaRange.type;
    }


    public String getMediaSubType() {
        return mediaRange.subtype;
    }


    public String getContentSubType() {
        return mediaRange == null ? null : mediaRange.getSubtype();
    }


    public String getContentType() {
        return mediaRange == null ? null : mediaRange.getType();
    }


    public String getCharset() {
        return this.getParameter("charset");
    }


    public void setMediaRange(MediaRange m) {
        mediaRange = m;
    }


    public void setContentType(String contentType, String contentSubType) {
        if (mediaRange == null)
            mediaRange = new MediaRange();
        mediaRange.setType(contentType);
        mediaRange.setSubtype(contentSubType);
    }


    public void setContentType(String contentType) throws ParseException {
        if (contentType == null)
            throw new NullPointerException("null arg");
        if (mediaRange == null)
            mediaRange = new MediaRange();
        mediaRange.setType(contentType);

    }


    public void setContentSubType(String contentType) throws ParseException {
        if (contentType == null)
            throw new NullPointerException("null arg");
        if (mediaRange == null)
            mediaRange = new MediaRange();
        mediaRange.setSubtype(contentType);
    }

    public Object clone() {
        ContentType retval = (ContentType) super.clone();
        if (this.mediaRange != null)
            retval.mediaRange = (MediaRange) this.mediaRange.clone();
        return retval;
    }

    public boolean equals(Object other) {
        if (other instanceof ContentTypeHeader o) {
            return this.getContentType().equalsIgnoreCase(o.getContentType())
                    && this.getContentSubType().equalsIgnoreCase(o.getContentSubType())
                    && equalParameters(o);
        }
        return false;
    }
}
