package com.jswitch.sip;

import com.jswitch.sip.utils.UriDecoder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.util.Iterator;
import java.util.Map;

import static com.jswitch.common.constant.Separators.*;

@Slf4j
@Data
public class SipUri extends GenericURI implements ISipURI {


    protected Authority authority;


    protected NameValueList uriParms;


    protected NameValueList qheaders;


    protected TelephoneNumber telephoneSubscriber;


    public SipUri() {
        this.scheme = SIP;
        this.uriParms = new NameValueList();
        this.qheaders = new NameValueList();
        this.qheaders.setSeparator("&");
    }


    public void setScheme(String scheme) {
        if (scheme.compareToIgnoreCase(SIP) != 0
                && scheme.compareToIgnoreCase(SIPS) != 0)
            throw new IllegalArgumentException("bad scheme " + scheme);
        this.scheme = scheme.toLowerCase();
    }

    public void clearUriParms() {
        uriParms = new NameValueList();
    }


    public void clearPassword() {
        if (this.authority != null) {
            UserInfo userInfo = authority.getUserInfo();
            if (userInfo != null)
                userInfo.clearPassword();
        }
    }

    public void clearQheaders() {
        qheaders = new NameValueList();
    }

    @Override
    public boolean equals(Object that) {

        if (that == this) return true;

        if (that instanceof ISipURI) {
            final ISipURI a = this;
            final ISipURI b = (ISipURI) that;

            if (a.isSecure() ^ b.isSecure()) return false;

            if (a.getUser() == null ^ b.getUser() == null) return false;
            if (a.getUserPassword() == null ^ b.getUserPassword() == null) return false;

            if (a.getUser() != null && !UriDecoder.decode(a.getUser()).equals(UriDecoder.decode(b.getUser())))
                return false;
            if (a.getUserPassword() != null && !UriDecoder.decode(a.getUserPassword()).equals(UriDecoder.decode(b.getUserPassword())))
                return false;
            if (a.getHost() == null ^ b.getHost() == null) return false;
            if (a.getHost() != null && !a.getHost().equalsIgnoreCase(b.getHost())) return false;
            if (a.getPort() != b.getPort()) return false;

            for (Iterator<String> i = a.getParameterNames(); i.hasNext(); ) {
                String pname = i.next();
                String p1 = a.getParameter(pname);
                String p2 = b.getParameter(pname);

                if (p1 != null && p2 != null && !UriDecoder.decode(p1).equalsIgnoreCase(UriDecoder.decode(p2)))
                    return false;
            }

            if (a.getTransportParam() == null ^ b.getTransportParam() == null) return false;
            if (a.getUserParam() == null ^ b.getUserParam() == null) return false;
            if (a.getTTLParam() == -1 ^ b.getTTLParam() == -1) return false;
            if (a.getMethodParam() == null ^ b.getMethodParam() == null) return false;
            if (a.getMAddrParam() == null ^ b.getMAddrParam() == null) return false;

            // Headers: must match according to their definition.
            if (a.getHeaderNames().hasNext() && !b.getHeaderNames().hasNext()) return false;
            if (!a.getHeaderNames().hasNext() && b.getHeaderNames().hasNext()) return false;

            return true;
        }
        return false;
    }


    public String encode() {
        return encode(new StringBuilder()).toString();
    }

    public StringBuilder encode(StringBuilder buffer) {
        buffer.append(scheme).append(COLON);
        if (authority != null)
            authority.encode(buffer);
        if (!uriParms.isEmpty()) {
            buffer.append(SEMICOLON);
            uriParms.encode(buffer);
        }
        if (!qheaders.isEmpty()) {
            buffer.append(QUESTION);
            qheaders.encode(buffer);
        }
        return buffer;
    }


    public String toString() {
        return this.encode();
    }


    public String getUserAtHost() {
        String user = "";
        if (authority.getUserInfo() != null)
            user = authority.getUserInfo().getUser();

        String host = authority.getHost().encode();
        StringBuilder s = null;
        if (user == null || user.equals("")) {
            s = new StringBuilder();
        } else {
            s = new StringBuilder(user).append(AT);
        }
        return s.append(host).toString();
    }


    public String getUserAtHostPort() {
        String user = "";
        if (authority.getUserInfo() != null)
            user = authority.getUserInfo().getUser();

        String host = authority.getHost().encode();
        int port = authority.getPort();
        StringBuilder s = null;
        if (user == null || user.equals("")) {
            s = new StringBuilder();
        } else {
            s = new StringBuilder(user).append(AT);
        }
        if (port != -1) {
            return s.append(host).append(COLON).append(port).toString();
        } else
            return s.append(host).toString();

    }


    public Object getParm(String parmname) {
        Object obj = uriParms.getValue(parmname);
        return obj;
    }


    public String getMethod() {
        return (String) getParm(METHOD);
    }


    public NameValueList getParameters() {
        return uriParms;
    }


    public void removeParameters() {
        this.uriParms = new NameValueList();
    }


    public NameValueList getQheaders() {
        return qheaders;
    }


    public String getUserType() {
        return (String) uriParms.getValue(USER);
    }


    public String getUserPassword() {
        if (authority == null)
            return null;
        return authority.getPassword();
    }


    public void setUserPassword(String password) {
        if (this.authority == null)
            this.authority = new Authority();
        authority.setPassword(password);
    }


    public TelephoneNumber getTelephoneSubscriber() {
        if (telephoneSubscriber == null) {

            telephoneSubscriber = new TelephoneNumber();
        }
        return telephoneSubscriber;
    }


    public HostPort getHostPort() {

        if (authority == null || authority.getHost() == null)
            return null;
        else {
            return authority.getHostPort();
        }
    }


    public int getPort() {
        HostPort hp = this.getHostPort();
        if (hp == null)
            return -1;
        return hp.getPort();
    }


    public String getHost() {
        if (authority == null) return null;
        else if (authority.getHost() == null) return null;
        else return authority.getHost().encode();
    }


    public boolean isUserTelephoneSubscriber() {
        String usrtype = (String) uriParms.getValue(USER);
        if (usrtype == null)
            return false;
        return usrtype.equalsIgnoreCase(PHONE);
    }


    public void removeTTL() {
        if (uriParms != null)
            uriParms.delete(TTL);
    }


    public void removeMAddr() {
        if (uriParms != null)
            uriParms.delete(MADDR);
    }


    public void removeTransport() {
        if (uriParms != null)
            uriParms.delete(TRANSPORT);
    }


    public void removeHeader(String name) {
        if (qheaders != null)
            qheaders.delete(name);
    }

    public void removeHeaders() {
        qheaders = new NameValueList();
    }


    public void removeUserType() {
        if (uriParms != null)
            uriParms.delete(USER);
    }


    public void removePort() {
        authority.removePort();
    }


    public void removeMethod() {
        if (uriParms != null)
            uriParms.delete(METHOD);
    }


    public void setUser(String uname) {
        if (this.authority == null) {
            this.authority = new Authority();
        }

        this.authority.setUser(uname);
    }


    public void removeUser() {
        this.authority.removeUserInfo();
    }

    public void setDefaultParm(String name, Object value) {
        if (uriParms.getValue(name) == null) {
            NameValue nv = new NameValue(name, value);
            uriParms.set(nv);
        }
    }


    public void setAuthority(Authority authority) {
        this.authority = authority;
    }


    public void setHost(Host h) {
        if (this.authority == null)
            this.authority = new Authority();
        this.authority.setHost(h);
    }


    public void setUriParms(NameValueList parms) {
        uriParms = parms;
    }


    public void setUriParm(String name, Object value) {
        NameValue nv = new NameValue(name, value);
        uriParms.set(nv);
    }


    public void setQheaders(NameValueList parms) {
        qheaders = parms;
    }


    public void setMAddr(String mAddr) {
        NameValue nameValue = uriParms.getNameValue(MADDR);
        Host host = new Host();
        host.setAddress(mAddr);
        if (nameValue != null)
            nameValue.setValueAsObject(host);
        else {
            nameValue = new NameValue(MADDR, host);
            uriParms.set(nameValue);
        }
    }


    public void setUserParam(String usertype) {
        uriParms.set(USER, usertype);
    }


    public void setMethod(String method) {
        uriParms.set(METHOD, method);
    }

    public void setIsdnSubAddress(String isdnSubAddress) {
        if (telephoneSubscriber == null)
            telephoneSubscriber = new TelephoneNumber();
        telephoneSubscriber.setIsdnSubaddress(isdnSubAddress);
    }


    public void setTelephoneSubscriber(TelephoneNumber tel) {
        telephoneSubscriber = tel;
    }


    public void setPort(int p) {
        if (authority == null)
            authority = new Authority();
        authority.setPort(p);
    }


    public boolean hasParameter(String name) {

        return uriParms.getValue(name) != null;
    }


    public void setQHeader(NameValue nameValue) {
        this.qheaders.set(nameValue);
    }


    public void setUriParameter(NameValue nameValue) {
        this.uriParms.set(nameValue);
    }


    public boolean hasTransport() {
        return hasParameter(TRANSPORT);
    }


    public void removeParameter(String name) {
        uriParms.delete(name);
    }


    public void setHostPort(HostPort hostPort) {
        if (this.authority == null) {
            this.authority = new Authority();
        }
        authority.setHostPort(hostPort);
    }

    public Object clone() {
        SipUri retval = (SipUri) super.clone();
        if (this.authority != null)
            retval.authority = (Authority) this.authority.clone();
        if (this.uriParms != null)
            retval.uriParms = (NameValueList) this.uriParms.clone();
        if (this.qheaders != null)
            retval.qheaders = (NameValueList) this.qheaders.clone();
        if (this.telephoneSubscriber != null)
            retval.telephoneSubscriber = (TelephoneNumber) this.telephoneSubscriber.clone();
        return retval;
    }


    public String getHeader(String name) {
        return this.qheaders.getValue(name) != null
                ? this.qheaders.getValue(name).toString()
                : null;

    }


    public Iterator<String> getHeaderNames() {
        return this.qheaders.getNames();

    }


    public String getLrParam() {
        boolean haslr = this.hasParameter(LR);
        return haslr ? "true" : null;
    }

    public String getMAddrParam() {
        NameValue maddr = uriParms.getNameValue(MADDR);
        if (maddr == null)
            return null;
        String host = (String) maddr.getValueAsObject();
        return host;
    }


    public String getMethodParam() {
        return this.getParameter(METHOD);
    }


    public String getParameter(String name) {
        Object val = uriParms.getValue(name);
        if (val == null)
            return null;
        if (val instanceof GenericObject)
            return ((GenericObject) val).encode();
        else
            return val.toString();
    }


    public Iterator<String> getParameterNames() {
        return this.uriParms.getNames();
    }


    public int getTTLParam() {
        Integer ttl = (Integer) uriParms.getValue("ttl");
        if (ttl != null)
            return ttl.intValue();
        else
            return -1;
    }


    public String getTransportParam() {
        if (uriParms != null) {
            return (String) uriParms.getValue(TRANSPORT);
        } else
            return null;
    }


    public String getUser() {
        return authority.getUser();
    }


    public boolean isSecure() {
        return this.getScheme().equalsIgnoreCase(SIPS);
    }


    public boolean isSipURI() {
        return true;
    }


    public void setHeader(String name, String value) {
        NameValue nv = new NameValue(name, value);
        qheaders.set(nv);

    }


    public void setHost(String host) throws ParseException {
        Host h = new Host(host);
        this.setHost(h);
    }

    public void setLrParam() {
        this.uriParms.set("lr", null);
    }


    public void setMAddrParam(String maddr) throws ParseException {
        if (maddr == null)
            throw new NullPointerException("bad maddr");
        setParameter("maddr", maddr);
    }


    public void setMethodParam(String method) throws ParseException {
        setParameter("method", method);
    }

    public void setParameter(String name, String value) throws ParseException {
        if (name.equalsIgnoreCase("ttl")) {
            try {
                Integer.parseInt(value);
            } catch (NumberFormatException ex) {
                throw new ParseException("bad parameter " + value, 0);
            }
        }
        uriParms.set(name, value);
    }


    public void setSecure(boolean secure) {
        if (secure)
            this.scheme = SIPS;
        else
            this.scheme = SIP;
    }

    public void setTTLParam(int ttl) {
        if (ttl <= 0)
            throw new IllegalArgumentException("Bad ttl value");
        if (uriParms != null) {
            NameValue nv = new NameValue("ttl", Integer.valueOf(ttl));
            uriParms.set(nv);
        }
    }


    public void setTransportParam(String transport) throws ParseException {
        if (transport == null)
            throw new NullPointerException("null arg");
        NameValue nv = new NameValue(TRANSPORT, transport.toLowerCase());
        uriParms.set(nv);
    }


    public String getUserParam() {
        return getParameter("user");

    }


    public boolean hasLrParam() {
        return uriParms.getNameValue(LR) != null;
    }


    public boolean hasGrParam() {
        return uriParms.getNameValue(GRUU) != null;
    }


    public void setGrParam(String value) {
        this.uriParms.set(GRUU, value);
    }

    public String getGrParam() {
        return (String) this.uriParms.getValue(GRUU);
    }

}
