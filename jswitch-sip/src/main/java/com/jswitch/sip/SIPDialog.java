package com.jswitch.sip;

import com.jswitch.common.exception.DialogDoesNotExistException;
import com.jswitch.common.exception.InvalidArgumentException;
import com.jswitch.common.exception.SipException;
import com.jswitch.common.exception.TransactionDoesNotExistException;
import com.jswitch.sip.adress.Address;
import com.jswitch.sip.header.*;

import java.util.Iterator;

/**
 * @author danmo
 * @date 2024-06-20 16:14
 **/
public class SIPDialog implements Dialog {



    @Override
    public Address getLocalParty() {
        return null;
    }

    @Override
    public Address getRemoteParty() {
        return null;
    }

    @Override
    public Address getRemoteTarget() {
        return null;
    }

    @Override
    public String getDialogId() {
        return null;
    }

    @Override
    public CallIdHeader getCallId() {
        return null;
    }

    @Override
    public int getLocalSequenceNumber() {
        return 0;
    }

    @Override
    public long getLocalSeqNumber() {
        return 0;
    }

    @Override
    public int getRemoteSequenceNumber() {
        return 0;
    }

    @Override
    public long getRemoteSeqNumber() {
        return 0;
    }

    @Override
    public Iterator getRouteSet() {
        return null;
    }

    @Override
    public boolean isSecure() {
        return false;
    }

    @Override
    public boolean isServer() {
        return false;
    }

    @Override
    public void incrementLocalSequenceNumber() {

    }

    @Override
    public Request createRequest(String method) throws SipException {
        return null;
    }

    @Override
    public Response createReliableProvisionalResponse(int statusCode) throws InvalidArgumentException, SipException {
        return null;
    }

    @Override
    public void sendRequest(ClientTransaction clientTransaction) throws TransactionDoesNotExistException, SipException {

    }

    @Override
    public void sendReliableProvisionalResponse(Response relResponse) throws SipException {

    }

    @Override
    public Request createPrack(Response relResponse) throws DialogDoesNotExistException, SipException {
        return null;
    }

    @Override
    public Request createAck(long cseq) throws InvalidArgumentException, SipException {
        return null;
    }

    @Override
    public void sendAck(Request ackRequest) throws SipException {

    }

    @Override
    public DialogState getState() {
        return null;
    }

    @Override
    public void delete() {

    }

    @Override
    public Transaction getFirstTransaction() {
        return null;
    }

    @Override
    public String getLocalTag() {
        return null;
    }

    @Override
    public String getRemoteTag() {
        return null;
    }

    @Override
    public void setApplicationData(Object applicationData) {

    }

    @Override
    public Object getApplicationData() {
        return null;
    }

    @Override
    public void terminateOnBye(boolean terminateFlag) throws SipException {

    }

    @Override
    public void setBackToBackUserAgent() {

    }

    @Override
    public void disableSequenceNumberValidation() {

    }

    @Override
    public ReleaseReferencesStrategy getReleaseReferencesStrategy() {
        return null;
    }

    @Override
    public void setReleaseReferencesStrategy(ReleaseReferencesStrategy releaseReferencesStrategy) {

    }

    @Override
    public void setEarlyDialogTimeoutSeconds(int timeoutValue) {

    }

    @Override
    public boolean isForked() {
        return false;
    }

    @Override
    public Dialog getOriginalDialog() {
        return null;
    }
}
