package com.jswitch.sip;

import java.net.InetAddress;

import static com.jswitch.common.constant.Separators.COLON;

/**
 * @author danmo
 * @date 2024-06-18 10:56
 **/
public final class HostPort extends GenericObject {

    protected Host host;


    protected int port;

    public HostPort() {
        host = null;
        port = -1;
    }

    public String encode() {
        return encode(new StringBuilder()).toString();
    }

    public StringBuilder encode(StringBuilder buffer) {
        host.encode(buffer);
        if (port != -1)
            buffer.append(COLON).append(port);
        return buffer;
    }


    public boolean equals(Object other) {
        if (other == null) return false;
        if (getClass () != other.getClass ()) {
            return false;
        }
        HostPort that = (HostPort) other;
        return port == that.port && host.equals(that.host);
    }


    public Host getHost() {
        return host;
    }


    public int getPort() {
        return port;
    }


    public boolean hasPort() {
        return port != -1;
    }


    public void removePort() {
        port = -1;
    }


    public void setHost(Host h) {
        host = h;
    }


    public void setPort(int p) {
        port = p;
    }


    public InetAddress getInetAddress() throws java.net.UnknownHostException {
        if (host == null)
            return null;
        else
            return host.getInetAddress();
    }

    public void merge(Object mergeObject) {
        super.merge (mergeObject);
        if (port == -1)
            port = ((HostPort) mergeObject).port;
    }

    public Object clone() {
        HostPort retval = (HostPort) super.clone();
        if (this.host != null)
            retval.host = (Host) this.host.clone();
        return retval;
    }

    public String toString() {
        return this.encode();
    }

    @Override
    public int hashCode() {
        return this.host.hashCode() + this.port;
    }

}
