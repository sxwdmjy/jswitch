package com.jswitch.sip.header;

import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;


public class Server extends SIPHeader implements ServerHeader {


    private static final long serialVersionUID = -3587764149383342973L;

    protected List<String> productTokens;


    private StringBuilder encodeProduct(StringBuilder tokens) {
        ListIterator<String> it = productTokens.listIterator();

        while (it.hasNext()) {
            tokens.append(it.next());
            if (it.hasNext())
                tokens.append('/');
            else
                break;
        }
        return tokens;
    }

    public void addProductToken(String pt) {
        productTokens.add(pt);
    }


    public Server() {
        super(NAME);
        productTokens = new LinkedList<>();
    }


    public StringBuilder encodeBody(StringBuilder retval) {
        return encodeProduct(retval);
    }


    public ListIterator<String> getProduct() {
        if (productTokens == null || productTokens.isEmpty())
            return null;
        else
            return productTokens.listIterator();
    }

    public void setProduct(List<String> product) throws ParseException {
        if (product == null)
            throw new NullPointerException("SIP Exception, UserAgent, setProduct(), the product parameter is null");
        productTokens = product;
    }
}

