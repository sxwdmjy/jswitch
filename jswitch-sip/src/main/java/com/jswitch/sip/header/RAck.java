
package com.jswitch.sip.header;

import com.jswitch.common.exception.InvalidArgumentException;

import java.text.ParseException;

import static com.jswitch.common.constant.Separators.SP;

public class RAck extends SIPHeader implements RAckHeader {


    private static final long serialVersionUID = 743999286077404118L;

    protected long cSeqNumber;

    protected long rSeqNumber;

    protected String method;

    public RAck() {
        super(NAME);
    }


    public StringBuilder encodeBody(StringBuilder buffer) {

        return buffer.append(rSeqNumber).append(SP).append(
                cSeqNumber).append(SP).append(method);

    }


    public int getCSeqNumber() {
        return (int) cSeqNumber;
    }

    public long getCSeqNumberLong() {
        return cSeqNumber;
    }


    public String getMethod() {
        return this.method;
    }


    public int getRSeqNumber() {
        return (int) rSeqNumber;
    }


    public void setCSeqNumber(int cSeqNumber) throws InvalidArgumentException {
        this.setCSequenceNumber(cSeqNumber);
    }

    public void setMethod(String method) throws ParseException {
        this.method = method;
    }

    public long getCSequenceNumber() {
        return this.cSeqNumber;
    }

    public long getRSequenceNumber() {
        return this.rSeqNumber;
    }

    public void setCSequenceNumber(long cSeqNumber)
            throws InvalidArgumentException {
        if (cSeqNumber <= 0 || cSeqNumber > ((long) 1) << 32 - 1)
            throw new InvalidArgumentException("Bad CSeq # " + cSeqNumber);
        this.cSeqNumber = cSeqNumber;

    }


    public void setRSeqNumber(int rSeqNumber) throws InvalidArgumentException {
        this.setRSequenceNumber(rSeqNumber);
    }

    public void setRSequenceNumber(long rSeqNumber)
            throws InvalidArgumentException {
        if (rSeqNumber <= 0 || cSeqNumber > ((long) 1) << 32 - 1)
            throw new InvalidArgumentException("Bad rSeq # " + rSeqNumber);
        this.rSeqNumber = rSeqNumber;
    }
}
