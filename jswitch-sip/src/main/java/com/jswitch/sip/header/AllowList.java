
package com.jswitch.sip.header;

import java.text.ParseException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;


public class AllowList extends SIPHeaderList<Allow> {


    private static final long serialVersionUID = -4699795429662562358L;

    public Object clone() {
        AllowList retval = new AllowList();
        retval.clonehlist(this.hlist);
        return retval;
    }


    public AllowList() {
        super(Allow.class, AllowHeader.NAME);
    }


    public ListIterator<String> getMethods() {

        LinkedList<String> ll = new LinkedList<String>();

        for (Iterator<Allow> it = this.hlist.iterator(); it.hasNext(); ) {
            Allow a = (Allow) it.next();
            ll.add(a.getMethod());
        }

        return ll.listIterator();
    }

    public void setMethods(List<String> methods) throws ParseException {
        ListIterator<String> it = methods.listIterator();
        while (it.hasNext()) {
            Allow allow = new Allow();
            allow.setMethod((String) it.next());
            this.add(allow);
        }
    }
}
