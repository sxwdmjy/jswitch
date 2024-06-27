
package com.jswitch.sip.header;

import com.jswitch.common.exception.InvalidArgumentException;

public class RSeq extends SIPHeader implements RSeqHeader {

    private static final long serialVersionUID = 8765762413224043394L;
    protected long sequenceNumber;


    public RSeq() {
        super(NAME);
    }


    public int getSequenceNumber() {
        return (int)this.sequenceNumber;
    }


    public StringBuilder encodeBody(StringBuilder retval) {
        return retval.append(Long.toString(this.sequenceNumber));
    }

    public long getSeqNumber() {
        return this.sequenceNumber;
    }

    public void setSeqNumber(long sequenceNumber) throws InvalidArgumentException {

            if (sequenceNumber <= 0 ||sequenceNumber > ((long)1)<<32 - 1)
                throw new InvalidArgumentException(
                    "Bad seq number " + sequenceNumber);
            this.sequenceNumber = sequenceNumber;

    }

    public void setSequenceNumber(int sequenceNumber) throws InvalidArgumentException {
        this.setSeqNumber(sequenceNumber);

    }



}
