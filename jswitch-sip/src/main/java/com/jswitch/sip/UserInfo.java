package com.jswitch.sip;

import lombok.Data;

import java.util.Objects;

import static com.jswitch.common.constant.Separators.*;

/**
 * @author danmo
 * @date 2024-06-18 11:01
 **/
@Data
public final class UserInfo extends NetObject {

    protected String user;

    protected String password;

    protected int userType;

    public final static int TELEPHONE_SUBSCRIBER = 1;

    public final static int USER = 2;

    public UserInfo() {
        super();
    }

    public boolean equals(Object obj) {
        if (getClass() != obj.getClass()) {
            return false;
        }
        UserInfo other = (UserInfo) obj;
        if (this.userType != other.userType) {
            return false;
        }
        if (!this.user.equalsIgnoreCase(other.user)) {
            return false;
        }
        if (this.password != null && other.password == null)
            return false;

        if (other.password != null && this.password == null)
            return false;

        return Objects.equals(this.password, other.password);
    }

    public String encode() {
        return encode(new StringBuilder()).toString();
    }

    public StringBuilder encode(StringBuilder buffer) {
        if (password != null)
            buffer.append(user).append(COLON).append(password);
        else
            buffer.append(user);

        return buffer;
    }


    public void clearPassword() {
        this.password = null;
    }


    public void setUser(String user) {
        if (user == null || user.isEmpty()) return;
        this.user = user;
        if (user.contains(POUND) || user.contains(SEMICOLON)) {
            setUserType(TELEPHONE_SUBSCRIBER);
        } else {
            setUserType(USER);
        }
    }

    public void setUserType(int type) throws IllegalArgumentException {
        if (type != TELEPHONE_SUBSCRIBER && type != USER) {
            throw new IllegalArgumentException("Parameter not in range");
        }
        userType = type;
    }
}
