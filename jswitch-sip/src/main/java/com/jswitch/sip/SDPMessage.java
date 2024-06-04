package com.jswitch.sip;

import com.jswitch.sip.sdp.*;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if(Objects.nonNull(version)){
            sb.append(version.toString());
        }
        if(Objects.nonNull(origin)){
            sb.append(origin.toString());
        }
        if(Objects.nonNull(sessionName)){
            sb.append(sessionName.toString());
        }
        if(Objects.nonNull(bandwidth)){
            sb.append(bandwidth.toString());
        }
        if(Objects.nonNull(timing)){
            sb.append(timing.toString());
        }
        if(Objects.nonNull(attributes) && !attributes.isEmpty()){
            for (Attribute attribute : attributes) {
                sb.append(attribute.toString());
            }
        }
        if(Objects.nonNull(mediaDescriptions) && !mediaDescriptions.isEmpty()){
            for (MediaDescription mediaDescription : mediaDescriptions) {
                sb.append(mediaDescription.toString());
            }
        }
        return sb.toString();
    }
}
