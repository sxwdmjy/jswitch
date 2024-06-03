package com.jswitch.common.utils;

import java.net.*;
import java.util.Enumeration;

/**
 * @author danmo
 * @date 2024-05-10 11:17
 **/
public class IpUtils {

    public static InetAddress getLocalHost(){
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = networkInterfaces.nextElement();
                for (Enumeration en2 = networkInterface.getInetAddresses(); en2.hasMoreElements();) {
                    InetAddress addr = (InetAddress) en2.nextElement();
                    if (!addr.isLoopbackAddress()) {
                        if (addr instanceof Inet4Address) {
                            return addr;
                        }
                    }
                }

            }
            return InetAddress.getLocalHost();
        } catch (SocketException | UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }
}
