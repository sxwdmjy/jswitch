package com.jswitch.sip.sdp;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Attribute {

    private String name;
    private String value;

    @Override
    public String toString() {
        return "a=" + name + (value != null ? ":" + value : "") + "\r\n";
    }
}
