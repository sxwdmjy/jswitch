
package com.jswitch.sip.header;

import com.jswitch.sip.ParameterNames;

import java.text.ParseException;
import java.util.Objects;

import static com.jswitch.common.constant.Separators.SEMICOLON;

public class Event extends ParametersHeader implements EventHeader {


    private static final long serialVersionUID = -6458387810431874841L;

    protected String eventType;


    public Event() {
        super(EVENT);
    }


    public void setEventType(String eventType) throws ParseException {
        if (eventType == null)
            throw new NullPointerException(" the eventType is null");
        this.eventType = eventType;
    }


    public String getEventType() {
        return eventType;
    }


    public void setEventId(String eventId) throws ParseException {
        if (eventId == null)
            throw new NullPointerException(" the eventId parameter is null");
        setParameter(ParameterNames.ID, eventId);
    }


    public String getEventId() {
        return getParameter(ParameterNames.ID);
    }


    public String encodeBody() {
        return encodeBody(new StringBuilder()).toString();
    }

    public StringBuilder encodeBody(StringBuilder buffer) {
        if (eventType != null)
            buffer.append(eventType);

        if (!parameters.isEmpty()) {
            buffer.append(SEMICOLON);
            this.parameters.encode(buffer);
        }
        return buffer;
    }


    public boolean match(Event matchTarget) {
        if (matchTarget.eventType == null && this.eventType != null)
            return false;
        else if (matchTarget.eventType != null && this.eventType == null)
            return false;
        else if (this.eventType == null && matchTarget.eventType == null)
            return false;
        else if (getEventId() == null && matchTarget.getEventId() != null)
            return false;
        else if (getEventId() != null && matchTarget.getEventId() == null)
            return false;
        return matchTarget.eventType.equalsIgnoreCase(this.eventType)
                && ((Objects.equals(this.getEventId(), matchTarget.getEventId()))
                || this.getEventId().equalsIgnoreCase(matchTarget.getEventId()));
    }
}
