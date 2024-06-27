package com.jswitch.sip.header;

import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class UserAgent extends SIPHeader implements UserAgentHeader {


    private static final long serialVersionUID = 4561239179796364295L;

    protected List productTokens;

    private StringBuilder encodeProduct(StringBuilder tokens) {
        ListIterator it = productTokens.listIterator();

        while (it.hasNext()) {
            tokens.append((String) it.next());

        }
        return tokens;
    }

    public void addProductToken(String pt) {
        productTokens.add(pt);
    }


    public UserAgent() {
        super(NAME);
        productTokens = new LinkedList();
    }


    public StringBuilder encodeBody(StringBuilder buffer) {
        return encodeProduct(buffer);
    }


    public ListIterator getProduct() {
        if (productTokens == null || productTokens.isEmpty())
            return null;
        else
            return productTokens.listIterator();
    }

    public void setProduct(List product) throws ParseException {
        if (product == null)
            throw new NullPointerException("SIP Exception, UserAgent, setProduct(), the  product parameter is null");
        productTokens = product;
    }

    public Object clone() {
        UserAgent retval = (UserAgent) super.clone();
        if (productTokens != null)
            retval.productTokens = new LinkedList(productTokens);
        return retval;
    }
}

