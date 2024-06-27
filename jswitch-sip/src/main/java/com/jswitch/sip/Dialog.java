package com.jswitch.sip;

import com.jswitch.common.exception.DialogDoesNotExistException;
import com.jswitch.common.exception.InvalidArgumentException;
import com.jswitch.common.exception.SipException;
import com.jswitch.common.exception.TransactionDoesNotExistException;
import com.jswitch.sip.adress.Address;
import com.jswitch.sip.header.CallIdHeader;

import java.io.Serializable;
import java.util.Iterator;

/**
 * @author danmo
 * @date 2024-06-13 11:02
 **/
public interface Dialog extends Serializable {

    public Address getLocalParty();


    public Address getRemoteParty();


    public Address getRemoteTarget();

    public String getDialogId();

    public CallIdHeader getCallId();


    public int getLocalSequenceNumber();


    public long getLocalSeqNumber();

    public int getRemoteSequenceNumber();

    public long getRemoteSeqNumber();

    public Iterator getRouteSet();

    public boolean isSecure();

    public boolean isServer();


    public void incrementLocalSequenceNumber();

    public Request createRequest(String method) throws SipException;

    public Response createReliableProvisionalResponse(int statusCode) throws InvalidArgumentException, SipException;


    public void sendRequest(ClientTransaction clientTransaction) throws TransactionDoesNotExistException, SipException;

    public void sendReliableProvisionalResponse(Response relResponse) throws SipException;


    public Request createPrack(Response relResponse) throws DialogDoesNotExistException, SipException;


    public Request createAck(long cseq) throws InvalidArgumentException, SipException;


    public void sendAck(Request ackRequest) throws SipException;


    public DialogState getState();


    public void delete();


    public Transaction getFirstTransaction();


    public String getLocalTag();


    public String getRemoteTag();


    public void setApplicationData(Object applicationData);


    public Object getApplicationData();


    public void terminateOnBye(boolean terminateFlag) throws SipException;

    public void setBackToBackUserAgent();

    public void disableSequenceNumberValidation();

    public ReleaseReferencesStrategy getReleaseReferencesStrategy();

    public void setReleaseReferencesStrategy(ReleaseReferencesStrategy releaseReferencesStrategy);

    public void setEarlyDialogTimeoutSeconds(int timeoutValue);

    public boolean isForked();


    public Dialog getOriginalDialog();

}
