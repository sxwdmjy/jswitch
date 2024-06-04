package com.jswitch.sip.sdp;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Version {

    private String version;

    @Override
    public String toString() {
        return "v=" + version + "\r\n";
    }
}
