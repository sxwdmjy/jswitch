package com.jswitch.sip;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author danmo
 * @date 2024-06-18 10:57
 **/
public class Host extends GenericObject {

    //确定我们是否应该容忍和剥离 IPv6 地址中的地址作用域区域。
    // 地址作用域区域有时会在 InetAddress.getHostAddress（） 生成的 IPv6 地址的末尾返回
    // 然而，它们不是 SIP 语义的一部分，因此基本上这种方法决定了解析器是否应该剥离它们（而不是简单地直截了当并抛出异常）
    private static boolean stripAddressScopeZones = false;

    protected static final int HOSTNAME = 1;
    protected static final int IPV4ADDRESS = 2;
    protected static final int IPV6ADDRESS = 3;

    protected String hostname;

    protected int addressType;

    private InetAddress inetAddress;


    public Host() {
        addressType = HOSTNAME;
    }


    public Host(String hostName) throws IllegalArgumentException {
        if (hostName == null)
            throw new IllegalArgumentException("null host name");
        setHost(hostName, IPV4ADDRESS);
    }


    public Host(String name, int addrType) {
        setHost(name, addrType);
    }

    public String encode() {
        return encode(new StringBuilder()).toString();
    }

    public StringBuilder encode(StringBuilder buffer) {
        if (addressType == IPV6ADDRESS && !isIPv6Reference(hostname)) {
            buffer.append('[').append(hostname).append(']');
        } else {
            buffer.append(hostname);
        }
        return buffer;
    }


    public boolean equals(Object obj) {
        if ( obj == null ) return false;
        if (!this.getClass().equals(obj.getClass())) {
            return false;
        }
        Host otherHost = (Host) obj;
        return otherHost.hostname.equals(hostname);

    }


    public String getHostname() {
        return hostname;
    }


    public String getAddress() {
        return hostname;
    }


    public String getIpAddress() {
        String rawIpAddress = null;
        if (hostname == null)
            return null;
        if (addressType == HOSTNAME) {
            try {
                if (inetAddress == null)
                    inetAddress = InetAddress.getByName(hostname);
                rawIpAddress = inetAddress.getHostAddress();
            } catch (UnknownHostException ex) {
                dbgPrint("Could not resolve hostname " + ex);
            }
        } else {
            if (addressType == IPV6ADDRESS){
                try {
                    String ipv6FullForm = getInetAddress().toString();
                    int slashIndex = ipv6FullForm.indexOf("/");
                    if (slashIndex != -1) {
                        ipv6FullForm = ipv6FullForm.substring(++slashIndex, ipv6FullForm.length());
                    }
                    if (hostname.startsWith("[")) {
                        rawIpAddress = '[' + ipv6FullForm + ']';
                    } else {
                        rawIpAddress = ipv6FullForm;
                    }
                } catch (UnknownHostException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } else {
                rawIpAddress = hostname;
            }
        }
        return rawIpAddress;
    }


    public void setHostname(String h) {
        setHost(h, HOSTNAME);
    }


    public void setHostAddress(String address) {
        setHost(address, IPV4ADDRESS);
    }


    private void setHost(String host, int type){
        inetAddress = null;

        if (isIPv6Address(host))
            addressType = IPV6ADDRESS;
        else
            addressType = type;

        if (host != null){
            hostname = host.trim();

            if(addressType == HOSTNAME)
                hostname = hostname.toLowerCase();

            int zoneStart = -1;
            if(addressType == IPV6ADDRESS
                    && stripAddressScopeZones
                    && (zoneStart = hostname.indexOf('%'))!= -1){

                hostname = hostname.substring(0, zoneStart);

                if( hostname.startsWith("[") && !hostname.endsWith("]"))
                    hostname += ']';
            }
        }
    }


    public void setAddress(String address) {
        this.setHostAddress(address);
    }


    public boolean isHostname() {
        return addressType == HOSTNAME;
    }


    public boolean isIPAddress() {
        return addressType != HOSTNAME;
    }


    public InetAddress getInetAddress() throws java.net.UnknownHostException {
        if (hostname == null)
            return null;
        if (inetAddress != null)
            return inetAddress;
        inetAddress = InetAddress.getByName(hostname);
        return inetAddress;

    }

    //----- IPv6
    private boolean isIPv6Address(String address) {
        return (address != null && address.indexOf(':') != -1);
    }

    public static boolean isIPv6Reference(String address) {
        return address.charAt(0) == '['
                && address.charAt(address.length() - 1) == ']';
    }

    @Override
    public int hashCode() {
        return this.getHostname().hashCode();

    }
}
