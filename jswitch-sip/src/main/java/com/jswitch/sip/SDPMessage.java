package com.jswitch.sip;

import com.jswitch.sip.sdp.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SDPMessage {

    private Version version;
    private Origin origin;
    private SessionName sessionName;
    private Bandwidth bandwidth;
    private Timing timing;
    private List<Attribute> attributes;
    private List<MediaDescription> mediaDescriptions;

    public void addAttribute(Attribute attribute) {
        this.attributes.add(attribute);
    }

    public void addMediaDescription(MediaDescription mediaDescription) {
        this.mediaDescriptions.add(mediaDescription);
    }

    public SDPMessage() {
        this.attributes = new ArrayList<>();
        this.mediaDescriptions = new ArrayList<>();
    }
}
