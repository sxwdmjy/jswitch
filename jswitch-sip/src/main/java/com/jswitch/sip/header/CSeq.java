package com.jswitch.sip.header;

import com.jswitch.common.exception.InvalidArgumentException;
import com.jswitch.sip.SipRequest;
import com.jswitch.sip.header.CSeqHeader;
import com.jswitch.sip.header.SIPHeader;
import lombok.Getter;

import java.text.ParseException;

import static com.jswitch.common.constant.Separators.*;

/**
 * @author danmo
 * @date 2024-06-18 15:06
 **/
public class CSeq extends SIPHeader implements CSeqHeader {

    protected Long seqno;

    @Getter
    protected String method;


    public CSeq() {
        super(CSeqHeader.NAME);
    }


    public CSeq(long seqno, String method) {
        this();
        this.seqno = seqno;
        this.method = SipRequest.getCannonicalName(method);
    }


    public boolean equals(Object other) {
        if (other instanceof CSeqHeader o) {
            return this.getSeqNumber() == o.getSeqNumber()
                    && this.getMethod().equals(o.getMethod());
        }
        return false;
    }

    public String encode() {
        return headerName + COLON + SP + encodeBody() + NEWLINE;
    }


    public String encodeBody() {
        return encodeBody(new StringBuilder()).toString();
    }

    public StringBuilder encodeBody(StringBuilder buffer) {
        return buffer.append(seqno).append(SP).append(method.toUpperCase());
    }


    public void setSeqNumber(long sequenceNumber)
            throws InvalidArgumentException {
        if (sequenceNumber < 0)
            throw new InvalidArgumentException(
                    "SIP Exception, CSeq, setSequenceNumber(), the sequence number parameter is < 0 : " + sequenceNumber);
        else if (sequenceNumber > ((long) 1) << 32 - 1)
            throw new InvalidArgumentException("SIP Exception, CSeq, setSequenceNumber(), the sequence number parameter is too large : " + sequenceNumber);

        seqno = sequenceNumber;
    }


    public void setSequenceNumber(int sequenceNumber) throws InvalidArgumentException {
        this.setSeqNumber((long) sequenceNumber);
    }


    public void setMethod(String meth) throws ParseException {
        if (meth == null)
            throw new NullPointerException("SIP Exception, CSeq , setMethod(), the meth parameter is null");
        this.method = SipRequest.getCannonicalName(meth);
    }


    public int getSequenceNumber() {
        if (this.seqno == null)
            return 0;
        else
            return this.seqno.intValue();
    }

    public long getSeqNumber() {
        return this.seqno;
    }


}
