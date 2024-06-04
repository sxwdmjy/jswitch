package com.jswitch.sip.sdp;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Bandwidth {
    private String bandwidth;

    @Override
    public String toString() {
        return "b=" + bandwidth + "\r\n";
    }
}
