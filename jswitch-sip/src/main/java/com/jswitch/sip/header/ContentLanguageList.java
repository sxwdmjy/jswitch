
package com.jswitch.sip.header;

public final class ContentLanguageList extends SIPHeaderList<ContentLanguage> {

    private static final long serialVersionUID = -5302265987802886465L;
    public Object clone() {
        ContentLanguageList retval = new ContentLanguageList();
        retval.clonehlist(this.hlist);
        return retval;
    }

    public ContentLanguageList () {
        super(ContentLanguage.class,
            ContentLanguageHeader.NAME);
    }

}
