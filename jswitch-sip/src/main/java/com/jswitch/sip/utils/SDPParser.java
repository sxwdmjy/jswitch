package com.jswitch.sip.utils;

import com.jswitch.sip.MediaDescription;
import com.jswitch.sip.SDPMessage;
import com.jswitch.sip.sdp.*;

import java.util.Arrays;

public class SDPParser {

    public static SDPMessage parse(String sdpContent) {
        SDPMessage sdp = new SDPMessage();
        String[] lines = sdpContent.split("\r\n");

        MediaDescription currentMediaDescription = null;

        for (String line : lines) {
            if (line.startsWith("v=")) {
                sdp.setVersion(new Version(line.substring(2)));
            } else if (line.startsWith("o=")) {
                String[] parts = line.substring(2).split(" ");
                sdp.setOrigin(new Origin(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5]));
            } else if (line.startsWith("s=")) {
                sdp.setSessionName(new SessionName(line.substring(2)));
            } else if (line.startsWith("b=")) {
                sdp.setBandwidth(new Bandwidth(line.substring(2)));
            } else if (line.startsWith("t=")) {
                String[] parts = line.substring(2).split(" ");
                sdp.setTiming(new Timing(parts[0], parts[1]));
            } else if (line.startsWith("a=")) {
                String[] parts = line.substring(2).split(":", 2);
                Attribute attribute = new Attribute(parts[0], parts.length > 1 ? parts[1] : null);
                if (currentMediaDescription == null) {
                    sdp.addAttribute(attribute);
                } else {
                    currentMediaDescription.getAttributes().add(attribute);
                }
            } else if (line.startsWith("m=")) {
                String[] parts = line.substring(2).split(" ");
                currentMediaDescription = new MediaDescription(parts[0], parts[1], parts[2], Arrays.asList(parts).subList(3, parts.length));
                sdp.addMediaDescription(currentMediaDescription);
            } else if (line.startsWith("c=")) {
                if (currentMediaDescription != null) {
                    String[] parts = line.substring(2).split(" ");
                    currentMediaDescription.setConnection(new Connection(parts[0], parts[1], parts[2]));
                }
            }
        }

        return sdp;
    }
}
