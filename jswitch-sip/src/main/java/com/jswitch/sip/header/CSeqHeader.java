package com.jswitch.sip.header;

import com.jswitch.common.exception.InvalidArgumentException;

import java.text.ParseException;

/**
 * @author danmo
 * @date 2024-06-18 15:07
 **/
public interface CSeqHeader extends Header {

    public void setMethod(String method) throws ParseException;


    public String getMethod();


    public void setSequenceNumber(int sequenceNumber)
            throws InvalidArgumentException;


    public int getSequenceNumber();


    public void setSeqNumber(long sequenceNumber)
            throws InvalidArgumentException;


    public long getSeqNumber();


    public boolean equals(Object obj);


    public final static String NAME = "CSeq";
}
