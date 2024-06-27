
package com.jswitch.sip;

import javax.net.ssl.SSLPeerUnverifiedException;
import java.security.cert.Certificate;
import java.util.List;

public interface TransactionExt extends Transaction {


    public SipProvider getSipProvider();


    public String getPeerAddress();

    public int getPeerPort();

    public String getTransport();

    public String getHost();

    public int getPort();

    public String getCipherSuite() throws UnsupportedOperationException;

   Certificate[] getLocalCertificates() throws UnsupportedOperationException;

   Certificate[]  getPeerCertificates() throws SSLPeerUnverifiedException;

   public List<String> extractCertIdentities() throws SSLPeerUnverifiedException;

   public ReleaseReferencesStrategy getReleaseReferencesStrategy();

   public void setReleaseReferencesStrategy(ReleaseReferencesStrategy releaseReferenceStrategy);     

   public int getTimerT2();

   public void setTimerT2(int interval);      

   public int getTimerT4();

   public void setTimerT4(int interval);

   public int getTimerD();

   public void setTimerD(int interval);
}
