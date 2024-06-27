
package com.jswitch.sip.header;

import java.text.ParseException;

import static com.jswitch.sip.SIPHeaderNames.ALLOW_EVENTS;

public final class AllowEvents extends SIPHeader implements AllowEventsHeader {


    private static final long serialVersionUID = 617962431813193114L;

    protected String eventType;

    public AllowEvents() {
        super(ALLOW_EVENTS);
    }


    public AllowEvents(String m) {
        super(ALLOW_EVENTS);
        eventType = m;
    }


    public void setEventType(String eventType) throws ParseException {
        if (eventType == null)
            throw new NullPointerException("SIP Exception, AllowEvents, setEventType(), the eventType parameter is null");
        this.eventType = eventType;
    }


    public String getEventType() {
        return eventType;
    }


    @Override
    public StringBuilder encodeBody(StringBuilder buffer) {
        return buffer.append(eventType);
    }
}
