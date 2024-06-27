package com.jswitch.sip.header;

import com.jswitch.common.exception.InvalidArgumentException;
import com.jswitch.sip.*;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;

import static com.jswitch.common.constant.Separators.*;
import static com.jswitch.sip.ParameterNames.RPORT;

/**
 * @author danmo
 * @date 2024-06-18 13:55
 **/
@Slf4j
public class Via extends ParametersHeader implements ViaHeader {

    protected Protocol sentProtocol;

    protected HostPort sentBy;

    protected String comment;

    public Via() {
        super(NAME);
        sentProtocol = new Protocol();
    }

    public boolean equals(Object other) {

        if (other == this) return true;

        if (other instanceof ViaHeader viaHeader) {
            return getProtocol().equalsIgnoreCase(viaHeader.getProtocol())
                    && getTransport().equalsIgnoreCase(viaHeader.getTransport())
                    && getHost().equalsIgnoreCase(viaHeader.getHost())
                    && getPort() == viaHeader.getPort()
                    && equalParameters(viaHeader);
        }
        return false;
    }

    public String getProtocolVersion() {
        if (sentProtocol == null)
            return null;
        else
            return sentProtocol.getProtocolVersion();
    }


    public Protocol getSentProtocol() {
        return sentProtocol;
    }


    public HostPort getSentBy() {
        return sentBy;
    }


    public Hop getHop() {
        return new HopImpl(sentBy.getHost().getHostname(),
                sentBy.getPort(), sentProtocol.getTransport());
    }

    public NameValueList getViaParms() {
        return parameters;
    }


    public String getComment() {
        return comment;
    }


    public boolean hasPort() {
        return (getSentBy()).hasPort();
    }


    public boolean hasComment() {
        return comment != null;
    }


    public void removePort() {
        sentBy.removePort();
    }


    public void removeComment() {
        comment = null;
    }


    public void setProtocolVersion(String protocolVersion) {
        if (sentProtocol == null)
            sentProtocol = new Protocol();
        sentProtocol.setProtocolVersion(protocolVersion);
    }


    public void setHost(Host host) {
        if (sentBy == null) {
            sentBy = new HostPort();
        }
        sentBy.setHost(host);
    }


    public void setSentProtocol(Protocol s) {
        sentProtocol = s;
    }


    public void setSentBy(HostPort s) {
        sentBy = s;
    }

    public void setComment(String c) {
        comment = c;
    }


    protected String encodeBody() {
        return encodeBody(new StringBuilder()).toString();
    }

    public StringBuilder encodeBody(StringBuilder buffer) {
        sentProtocol.encode(buffer);
        buffer.append(SP);
        sentBy.encode(buffer);
        if (!parameters.isEmpty()) {
            buffer.append(SEMICOLON);
            parameters.encode(buffer);
        }
        if (comment != null) {
            buffer.append(SP).append(LPAREN).append(comment).append(RPAREN);
        }
        return buffer;
    }

    public void setHost(String host) throws ParseException {
        if (sentBy == null)
            sentBy = new HostPort();
        try {
            Host h = new Host(host);
            sentBy.setHost(h);
        } catch (Exception e) {
            throw new NullPointerException(" host parameter is null");
        }
    }


    public String getHost() {
        if (sentBy == null)
            return null;
        else {
            Host host = sentBy.getHost();
            if (host == null)
                return null;
            else
                return host.getHostname();
        }
    }


    public void setPort(int port) throws InvalidArgumentException {

        if (port != -1 && (port < 1 || port > 65535)) {
            throw new InvalidArgumentException("Port value out of range -1, [1..65535]");
        }

        if (sentBy == null)
            sentBy = new HostPort();
        sentBy.setPort(port);
    }


    public void setRPort(String rport) {
        try {
            this.setParameter(RPORT, rport);
        } catch (ParseException e) {
            log.error("Failed to set rport parameter");
        }
    }


    public int getPort() {
        if (sentBy == null)
            return -1;
        return sentBy.getPort();
    }


    public int getRPort() {
        String strRport = getParameter(RPORT);
        if (strRport != null && !strRport.equals(""))
            return Integer.valueOf(strRport).intValue();
        else
            return -1;
    }


    public String getTransport() {
        if (sentProtocol == null)
            return null;
        return sentProtocol.getTransport();
    }


    public void setTransport(String transport) throws ParseException {
        if (transport == null)
            throw new NullPointerException("SIP Exception, Via, setTransport(), the transport parameter is null.");
        if (sentProtocol == null)
            sentProtocol = new Protocol();
        sentProtocol.setTransport(transport);
    }


    public String getProtocol() {
        if (sentProtocol == null)
            return null;
        return sentProtocol.getProtocol();
    }


    public void setProtocol(String protocol) throws ParseException {
        if (protocol == null)
            throw new NullPointerException("SIP Exception, Via, setProtocol(), the protocol parameter is null");

        if (sentProtocol == null)
            sentProtocol = new Protocol();

        sentProtocol.setProtocol(protocol);
    }


    public int getTTL() {
        int ttl = getParameterAsInt(ParameterNames.TTL);
        return ttl;
    }


    public void setTTL(int ttl) throws InvalidArgumentException {
        if (ttl < 0 && ttl != -1)
            throw new InvalidArgumentException("SIP Exception, Via, setTTL(), the ttl parameter is < 0");
        setParameter(new NameValue(ParameterNames.TTL, Integer.valueOf(ttl)));
    }


    public String getMAddr() {
        return getParameter(ParameterNames.MADDR);
    }


    public void setMAddr(String mAddr) throws ParseException {
        if (mAddr == null)
            throw new NullPointerException("SIP Exception, Via, setMAddr(), the mAddr parameter is null.");

        Host host = new Host();
        host.setAddress(mAddr);
        NameValue nameValue = new NameValue(ParameterNames.MADDR, host);
        setParameter(nameValue);

    }


    public String getReceived() {
        return getParameter(ParameterNames.RECEIVED);
    }


    public void setReceived(String received) throws ParseException {
        if (received == null)
            throw new NullPointerException("SIP Exception, Via, setReceived(), the received parameter is null.");

        setParameter(ParameterNames.RECEIVED, received);

    }


    public String getBranch() {
        return getParameter(ParameterNames.BRANCH);
    }


    public void setBranch(String branch) throws ParseException {
        if (branch == null || branch.length() == 0)
            throw new NullPointerException("SIP Exception,Via, setBranch(), the branch parameter is null or length 0.");

        setParameter(ParameterNames.BRANCH, branch);
    }

    public Object clone() {
        Via retval = (Via) super.clone();
        if (this.sentProtocol != null)
            retval.sentProtocol = (Protocol) this.sentProtocol.clone();
        if (this.sentBy != null)
            retval.sentBy = (HostPort) this.sentBy.clone();
        if (this.getRPort() != -1)
            retval.setParameter(RPORT, this.getRPort());
        return retval;
    }


    public String getSentByField() {
        if (sentBy != null)
            return sentBy.encode();
        return null;
    }

    public String getSentProtocolField() {
        if (sentProtocol != null)
            return sentProtocol.encode();
        return null;
    }
}
