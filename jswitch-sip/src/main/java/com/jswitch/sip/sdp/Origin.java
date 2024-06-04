package com.jswitch.sip.sdp;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Origin {

    private String username;
    private String sessionId;
    private String sessionVersion;
    private String netType;
    private String addrType;
    private String unicastAddress;


    @Override
    public String toString() {
        return "o=" + username + " " + sessionId + " " + sessionVersion + " " + netType + " " + addrType + " " + unicastAddress + "\r\n";
    }

}
