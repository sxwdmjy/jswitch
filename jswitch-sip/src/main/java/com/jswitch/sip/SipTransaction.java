/*
 * Conditions Of Use
 *
 * This software was developed by employees of the National Institute of
 * Standards and Technology (NIST), an agency of the Federal Government.
 * Pursuant to title 15 Untied States Code Section 105, works of NIST
 * employees are not subject to copyright protection in the United States
 * and are considered to be in the public domain.  As a result, a formal
 * license is not needed to use the software.
 *
 * This software is provided by NIST as a service and is expressly
 * provided "AS IS."  NIST MAKES NO WARRANTY OF ANY KIND, EXPRESS, IMPLIED
 * OR STATUTORY, INCLUDING, WITHOUT LIMITATION, THE IMPLIED WARRANTY OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE, NON-INFRINGEMENT
 * AND DATA ACCURACY.  NIST does not warrant or make any representations
 * regarding the use of the software or the results thereof, including but
 * not limited to the correctness, accuracy, reliability or usefulness of
 * the software.
 *
 * Permission to use this software is contingent upon your acceptance
 * of the terms of this agreement
 *
 * .
 *
 */
package com.jswitch.sip;

import com.jswitch.sip.header.Via;

import javax.net.ssl.SSLPeerUnverifiedException;
import java.io.IOException;
import java.net.InetAddress;
import java.util.List;

public interface SipTransaction extends TransactionExt {


  public static final int T1 = 1;


  public static final int TIMER_A = 1;


  public static final int TIMER_B = 64;

  public static final int TIMER_J = 64;

  public static final int TIMER_F = 64;

  public static final int TIMER_H = 64;
  

  public static final TransactionState INITIAL_STATE = null;

  public static final TransactionState TRYING_STATE = TransactionState.TRYING;

  public static final TransactionState CALLING_STATE = TransactionState.CALLING;

  public static final TransactionState PROCEEDING_STATE = TransactionState.PROCEEDING;

  public static final TransactionState COMPLETED_STATE = TransactionState.COMPLETED;

  public static final TransactionState CONFIRMED_STATE = TransactionState.CONFIRMED;

  public static final TransactionState TERMINATED_STATE = TransactionState.TERMINATED;
  
  public String getBranchId();

  public void cleanUp();


  public void setOriginalRequest(SipRequest newOriginalRequest);


  public SipRequest getOriginalRequest();


  public Request getRequest();


  public boolean isDialogCreatingTransaction();


  public boolean isInviteTransaction();


  public boolean isCancelTransaction();


  public boolean isByeTransaction();


  //public MessageChannel getMessageChannel();


  public void setBranch(String newBranch);


  public String getBranch();


  public String getMethod();


  public long getCSeq();


  public void setState(int newState);


  public int getInternalState();


  public TransactionState getState();


  public boolean isTerminated();

  public String getHost();

  public String getKey();

  public int getPort();

  //public SIPTransactionStack getSIPStack();

  public String getPeerAddress();

  public int getPeerPort();
  
  public String getPeerProtocol();


  public int getPeerPacketSourcePort();

  public InetAddress getPeerPacketSourceAddress();

  public String getTransport();

  public boolean isReliable();


  public Via getViaHeader();


  public void sendMessage(SipMessage messageToSend) throws IOException;


  public Dialog getDialog();


  public void setDialog(SIPDialog sipDialog, String dialogId);


  public int getRetransmitTimer();


  public String getViaHost();


  public SipResponse getLastResponse();


  public Response getResponse();


  public String getTransactionId();


  public int hashCode();


  public int getViaPort();


  public boolean doesCancelMatchTransaction(SipRequest requestToTest);


  public void setRetransmitTimer(int retransmitTimer);

  /**
   * Close the encapsulated channel.
   */
  public void close();

  public boolean isSecure();

  //public MessageProcessor getMessageProcessor();


  public void setApplicationData(Object applicationData);


  public Object getApplicationData();


  //public void setEncapsulatedChannel(MessageChannel messageChannel);


  //public SipProviderImpl getSipProvider();


  public void raiseIOExceptionEvent();


  public boolean acquireSem();


  public void releaseSem();

  public boolean passToListener();


  public void setPassToListener();

  public String getCipherSuite() throws UnsupportedOperationException;

  public java.security.cert.Certificate[] getLocalCertificates()
    throws UnsupportedOperationException;

  public java.security.cert.Certificate[] getPeerCertificates() throws SSLPeerUnverifiedException;


  public List<String> extractCertIdentities() throws SSLPeerUnverifiedException;

  public boolean isMessagePartOfTransaction(SipMessage messageToTest);


  public ReleaseReferencesStrategy getReleaseReferencesStrategy();


  public void setReleaseReferencesStrategy(ReleaseReferencesStrategy releaseReferenceStrategy);


  public int getTimerD();


  public int getTimerT2();


  public int getTimerT4();


  public void setTimerD(int interval);


  public void setTimerT2(int interval);


  public void setTimerT4(int interval);


  public void setForkId(String forkId);


  public String getForkId();

  public void cancelMaxTxLifeTimeTimer();


  public String getMergeId();

  public long getAuditTag();

  public void setAuditTag(long auditTag);

  public void semRelease();

  boolean isTransactionMapped();

  void setTransactionMapped(boolean transactionMapped);


  void fireTimeoutTimer();


  void raiseErrorEvent(int errorEventID);


  void fireTimer();


  boolean isServerTransaction();


  void startTransactionTimer();


  void fireRetransmissionTimer();


  boolean testAndSetTransactionTerminatedEvent();

  void scheduleMaxTxLifeTimeTimer();

  void setCollectionTime(int collectionTime);

  void disableRetransmissionTimer();


  void disableTimeoutTimer();

  int getTimerK();

  int getTimerI();

  int getT2();

  int getT4();

  int getBaseTimerInterval();

}
