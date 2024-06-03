package com.jswitch.sip.sdp;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Connection {

    private String netType;
    private String addrType;
    private String connectionAddress;

    @Override
    public String toString() {
        return netType + " " + addrType + " " + connectionAddress;
    }
}
