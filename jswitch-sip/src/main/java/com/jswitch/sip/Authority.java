package com.jswitch.sip;

import lombok.Data;

import static com.jswitch.common.constant.Separators.AT;

/**
 * @author danmo
 * @date 2024-06-18 10:56
 **/
@Data
public class Authority extends NetObject {

    protected HostPort hostPort;

    /**
     * userInfo field
     */
    protected UserInfo userInfo;


    public String encode() {
        return encode(new StringBuilder()).toString();
    }

    public StringBuilder encode(StringBuilder buffer) {
        if (userInfo != null) {
            userInfo.encode(buffer);
            buffer.append(AT);
            hostPort.encode(buffer);
        } else {
            hostPort.encode(buffer);
        }
        return buffer;
    }


    @Override
    public boolean equals(Object other) {
        if (other == null) return false;
        if (other.getClass() != getClass()) {
            return false;
        }
        Authority otherAuth = (Authority) other;
        if (!this.hostPort.equals(otherAuth.hostPort)) {
            return false;
        }
        if (this.userInfo != null && otherAuth.userInfo != null) {
            if (!this.userInfo.equals(otherAuth.userInfo)) {
                return false;
            }
        }
        return true;
    }


    public String getPassword() {
        if (userInfo == null)
            return null;
        else
            return userInfo.password;
    }


    public String getUser() {
        return userInfo != null ? userInfo.user : null;
    }


    public Host getHost() {
        if (hostPort == null)
            return null;
        else
            return hostPort.getHost();
    }


    public int getPort() {
        if (hostPort == null)
            return -1;
        else
            return hostPort.getPort();
    }


    public void removePort() {
        if (hostPort != null)
            hostPort.removePort();
    }


    public void setPassword(String passwd) {
        if (userInfo == null)
            userInfo = new UserInfo();
        userInfo.setPassword(passwd);
    }


    public void setUser(String user) {
        if (userInfo == null)
            userInfo = new UserInfo();
        this.userInfo.setUser(user);
    }


    public void setHost(Host host) {
        if (hostPort == null)
            hostPort = new HostPort();
        hostPort.setHost(host);
    }


    public void setPort(int port) {
        if (hostPort == null)
            hostPort = new HostPort();
        hostPort.setPort(port);
    }


    public void removeUserInfo() {
        this.userInfo = null;
    }

    public Object clone() {
        Authority retrieval = (Authority) super.clone();
        if (this.hostPort != null)
            retrieval.hostPort = (HostPort) this.hostPort.clone();
        if (this.userInfo != null)
            retrieval.userInfo = (UserInfo) this.userInfo.clone();
        return retrieval;
    }

    @Override
    public int hashCode() {
        if (this.hostPort == null) throw new UnsupportedOperationException("Null hostPort cannot compute hashcode");
        return this.hostPort.encode().hashCode();
    }
}
