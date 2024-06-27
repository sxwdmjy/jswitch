
package com.jswitch.sip.header;


import lombok.Getter;

import java.text.ParseException;

import static com.jswitch.sip.SIPHeaderNames.SUBJECT;

@Getter
public class Subject extends SIPHeader implements SubjectHeader {

    private static final long serialVersionUID = -6479220126758862528L;

    protected String subject;


    public Subject() {
        super(SUBJECT);
    }


    public StringBuilder encodeBody(StringBuilder retval) {
        if (subject != null) {
            return retval.append(subject);
        } else {
            return retval.append("");
        }
    }


    public void setSubject(String subject) throws ParseException {
        if (subject == null)
            throw new NullPointerException("SIP Exception, Subject, setSubject(), the subject parameter is null");
        this.subject = subject;
    }

}

