package com.jswitch.sip.sdp;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class SessionName {

    private String sessionName;

    @Override
    public String toString() {
        return sessionName;
    }
}
