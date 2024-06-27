package com.jswitch.sip.header;


import com.jswitch.common.exception.InvalidArgumentException;

import static com.jswitch.sip.SIPHeaderNames.TIMESTAMP;

public class TimeStamp extends SIPHeader implements TimeStampHeader {


    private static final long serialVersionUID = -3711322366481232720L;


    protected long timeStamp = -1;


    protected int delay = -1;

    protected float delayFloat = -1;

    private float timeStampFloat = -1;


    public TimeStamp() {
        super(TIMESTAMP);
    }

    private String getTimeStampAsString() {
        if (timeStamp == -1 && timeStampFloat == -1)
            return "";
        else if (timeStamp != -1)
            return Long.toString(timeStamp);
        else
            return Float.toString(timeStampFloat);
    }

    private String getDelayAsString() {
        if (delay == -1 && delayFloat == -1)
            return "";
        else if (delay != -1)
            return Integer.toString(delay);
        else
            return Float.toString(delayFloat);
    }


    public StringBuilder encodeBody(StringBuilder retval) {        
        String s1 = getTimeStampAsString();
        String s2 = getDelayAsString();
        if (s1.isEmpty() && s2.isEmpty())
            return retval;
        if (!s1.isEmpty())
            retval.append(s1);
        if (!s2.isEmpty())
            retval.append(" ").append(s2);
        return retval;

    }


    public boolean hasDelay() {
        return delay != -1;
    }


    public void removeDelay() {
        delay = -1;
    }



    public void setTimeStamp(float timeStamp) throws InvalidArgumentException {
        if (timeStamp < 0)
            throw new InvalidArgumentException("SIP Exception, TimeStamp, setTimeStamp(), the timeStamp parameter is <0");
        this.timeStamp = -1;
        this.timeStampFloat = timeStamp;
    }


    public float getTimeStamp() {
        return this.timeStampFloat == -1 ? Float.valueOf(timeStamp).floatValue()
                : this.timeStampFloat;
    }



    public float getDelay() {
        return delayFloat == -1 ? Float.valueOf(delay).floatValue() : delayFloat;
    }

    public void setDelay(float delay) throws InvalidArgumentException {
        if (delay < 0 && delay != -1)
            throw new InvalidArgumentException("SIP Exception, TimeStamp, setDelay(), the delay parameter is <0");
        this.delayFloat = delay;
        this.delay = -1;
    }

    public long getTime() {
        return this.timeStamp == -1 ? (long) timeStampFloat : timeStamp;
    }

    public int getTimeDelay() {
        return this.delay == -1 ? (int) delayFloat : delay;

    }

    public void setTime(long timeStamp) throws InvalidArgumentException {
        if (timeStamp < -1)
            throw new InvalidArgumentException("Illegal timestamp");
        this.timeStamp = timeStamp;
        this.timeStampFloat = -1;

    }

    public void setTimeDelay(int delay) throws InvalidArgumentException {
        if (delay < -1)
            throw new InvalidArgumentException("Value out of range " + delay);
        this.delay = delay;
        this.delayFloat = -1;

    }

}
