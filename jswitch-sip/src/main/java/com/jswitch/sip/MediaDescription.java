package com.jswitch.sip;

import com.jswitch.sip.sdp.Attribute;
import com.jswitch.sip.sdp.Connection;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MediaDescription {

    private String media;
    private String port;
    private String proto;
    private List<String> fmt;
    private Connection connection;
    private List<Attribute> attributes;

    public MediaDescription(String media, String port, String proto, List<String> fmt) {
        this.media = media;
        this.port = port;
        this.proto = proto;
        this.fmt = fmt;
        this.attributes = new ArrayList<>();
    }

    @Override
    public String toString() {
        return media + " " + port + " " + proto + " " + String.join(" ", fmt);
    }
}
