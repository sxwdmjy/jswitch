package com.jswitch.sip.sdp;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Timing {

    private String startTime;
    private String stopTime;

    @Override
    public String toString() {
        return startTime + " " + stopTime;
    }
}
