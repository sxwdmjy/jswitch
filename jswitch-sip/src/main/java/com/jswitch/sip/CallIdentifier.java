package com.jswitch.sip;

import lombok.Getter;

import static com.jswitch.common.constant.Separators.AT;

/**
 * @author danmo
 * @date 2024-06-18 15:15
 **/
@Getter
public final class CallIdentifier extends SIPObject {

    private static final long serialVersionUID = 1L;


    private String localId;


    private String host;


    public CallIdentifier() {
    }


    public CallIdentifier(String localId, String host) {
        this.localId = localId;
        this.host = host;
    }


    public CallIdentifier(String cid) throws IllegalArgumentException {
        setCallID(cid);
    }


    public String encode() {
        return encode(new StringBuilder()).toString();
    }

    public StringBuilder encode(StringBuilder buffer) {
        buffer.append(localId);
        if (host != null) {
            buffer.append(AT).append(host);
        }
        return buffer;
    }


    public boolean equals(Object other) {
        if (other == null) return false;
        if (!other.getClass().equals(this.getClass())) {
            return false;
        }
        CallIdentifier that = (CallIdentifier) other;
        if (this.localId.compareTo(that.localId) != 0) {
            return false;
        }
        if (this.host == that.host)
            return true;
        if ((this.host == null && that.host != null)
                || (this.host != null && that.host == null))
            return false;
        if (host.compareToIgnoreCase(that.host) != 0) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        if (this.localId == null) {
            throw new UnsupportedOperationException("Hash code called before id is set");
        }
        return this.localId.hashCode();
    }


    public void setLocalId(String localId) {
        this.localId = localId;
    }


    public void setCallID(String cid) throws IllegalArgumentException {
        if (cid == null)
            throw new IllegalArgumentException("NULL!");
        int index = cid.indexOf('@');
        if (index == -1) {
            localId = cid;
            host = null;
        } else {
            localId = cid.substring(0, index);
            host = cid.substring(index + 1);
        }
    }

    public void setHost(String host) {
        this.host = host;
    }
}
