package com.jswitch.sip.header;

import com.jswitch.common.exception.InvalidArgumentException;
import com.jswitch.sip.ParameterNames;

import java.text.ParseException;

import static com.jswitch.common.constant.Separators.*;

public class RetryAfter extends ParametersHeader implements RetryAfterHeader {


    private static final long serialVersionUID = -1029458515616146140L;


    public static final String DURATION = ParameterNames.DURATION;


    protected Integer retryAfter = 0;


    protected String comment;


    public RetryAfter() {
        super(NAME);
    }


    public StringBuilder encodeBody(StringBuilder retval) {

        if (retryAfter != null)
            retval.append(retryAfter);

        if (comment != null)
            retval.append(SP + LPAREN).append(comment).append(RPAREN);
        if (!parameters.isEmpty()) {
            retval.append(SEMICOLON);
            parameters.encode(retval);
        }

        return retval;
    }


    public boolean hasComment() {
        return comment != null;
    }


    public void removeComment() {
        comment = null;
    }


    public void removeDuration() {
        super.removeParameter(DURATION);
    }

    public void setRetryAfter(int retryAfter) throws InvalidArgumentException {
        if (retryAfter < 0)
            throw new InvalidArgumentException(
                    "invalid parameter " + retryAfter);
        this.retryAfter = Integer.valueOf(retryAfter);
    }

    public int getRetryAfter() {
        return retryAfter;
    }

    public String getComment() {
        return comment;
    }


    public void setComment(String comment) throws ParseException {
        if (comment == null)
            throw new NullPointerException("the comment parameter is null");
        this.comment = comment;
    }

    public void setDuration(int duration) throws InvalidArgumentException {
        if (duration < 0)
            throw new InvalidArgumentException("the duration parameter is <0");
        this.setParameter(DURATION, duration);
    }

    public int getDuration() {
        if (this.getParameter(DURATION) == null) return -1;
        else return super.getParameterAsInt(DURATION);
    }
}
