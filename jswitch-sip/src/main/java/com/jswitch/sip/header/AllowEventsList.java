
package com.jswitch.sip.header;

import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class AllowEventsList extends SIPHeaderList<AllowEvents> {

    private static final long serialVersionUID = -684763195336212992L;

    public Object clone() {
        AllowEventsList retval = new AllowEventsList();
        retval.clonehlist(this.hlist);
        return retval;
    }


    public AllowEventsList() {
        super(AllowEvents.class, AllowEventsHeader.NAME);
    }


    public ListIterator<String> getMethods() {
        ListIterator<AllowEvents> li = super.hlist.listIterator();
        LinkedList<String> ll = new LinkedList<String>();
        while (li.hasNext()) {
            AllowEvents allowEvents = (AllowEvents) li.next();
            ll.add(allowEvents.getEventType());
        }
        return ll.listIterator();
    }


    public void setMethods(List<String> methods) throws ParseException {
        for (String method : methods) {
            AllowEvents allowEvents = new AllowEvents();
            allowEvents.setEventType(method);
            this.add(allowEvents);
        }
    }
}
