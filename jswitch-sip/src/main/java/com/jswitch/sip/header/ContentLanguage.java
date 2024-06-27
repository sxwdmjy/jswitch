
package com.jswitch.sip.header;

import java.util.Locale;

import static com.jswitch.sip.SIPHeaderNames.CONTENT_LANGUAGE;

public class ContentLanguage extends SIPHeader implements ContentLanguageHeader {


    private static final long serialVersionUID = -5195728427134181070L;

    protected Locale locale;

    public ContentLanguage() {
        super(CONTENT_LANGUAGE);
    }


    public ContentLanguage(String languageTag) {
        super(CONTENT_LANGUAGE);
        this.setLanguageTag(languageTag);
    }


    public StringBuilder encodeBody(StringBuilder buffer) {
        return buffer.append(getLanguageTag());
    }


    public String getLanguageTag() {
        if ("".equals(locale.getCountry())) {
            return locale.getLanguage();
        } else {
            return locale.getLanguage() + '-' + locale.getCountry();
        }
    }

    public void setLanguageTag(String languageTag) {

        final int slash = languageTag.indexOf('-');
        if (slash >= 0) {
            this.locale = new Locale(languageTag.substring(0, slash), languageTag.substring(slash + 1));
        } else {
            this.locale = new Locale(languageTag);
        }
    }

    public Locale getContentLanguage() {
        return locale;
    }

    public void setContentLanguage(Locale language) {
        this.locale = language;
    }

    public Object clone() {
        ContentLanguage retval = (ContentLanguage) super.clone();
        if (this.locale != null)
            retval.locale = (Locale) this.locale.clone();
        return retval;
    }
}
