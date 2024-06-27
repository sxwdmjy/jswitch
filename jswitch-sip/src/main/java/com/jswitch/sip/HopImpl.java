package com.jswitch.sip;

import lombok.Getter;

import java.io.Serializable;

/**
 * @author danmo
 * @date 2024-06-18 14:42
 **/
public class HopImpl implements Hop, Serializable {
    @Getter
    protected String host;
    @Getter
    protected int port;
    @Getter
    protected String transport;

    //这是从代理添加器生成的
    protected boolean defaultRoute;
    protected boolean uriRoute;

    public String toString() {
        return host + ":" + port + "/" + transport;
    }

    public HopImpl(String hostName, int portNumber, String trans) {
        host = hostName;
        if (host.contains(":"))
            if (!host.contains("["))
                host = "[" + host + "]";
        port = portNumber;
        transport = trans;
    }


    HopImpl(String hop) throws IllegalArgumentException {

        if (hop == null)
            throw new IllegalArgumentException("Null arg!");

        int brack = hop.indexOf(']');
        int colon = hop.indexOf(':', brack);
        int slash = hop.indexOf('/', colon);

        if (colon > 0) {
            this.host = hop.substring(0, colon);
            String portstr;
            if (slash > 0) {
                portstr = hop.substring(colon + 1, slash);
                this.transport = hop.substring(slash + 1);
            } else {
                portstr = hop.substring(colon + 1);
                this.transport = "UDP";
            }
            try {
                port = Integer.parseInt(portstr);
            } catch (NumberFormatException ex) {
                throw new IllegalArgumentException("Bad port spec");
            }
        } else {
            if (slash > 0) {
                this.host = hop.substring(0, slash);
                this.transport = hop.substring(slash + 1);
                this.port = transport.equalsIgnoreCase("TLS") ? 5061 : 5060;
            } else {
                this.host = hop;
                this.transport = "UDP";
                this.port = 5060;
            }
        }

        if (host.isEmpty())
            throw new IllegalArgumentException("no host!");

        this.host = this.host.trim();
        this.transport = this.transport.trim();

        if ((brack > 0) && host.charAt(0) != '[') {
            throw new IllegalArgumentException("Bad IPv6 reference spec");
        }

    }


    public boolean isURIRoute() {
        return uriRoute;
    }


    public void setURIRouteFlag() {
        uriRoute = true;
    }
}
