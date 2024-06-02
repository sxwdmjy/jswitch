package com.jswitch.sip;

import lombok.Data;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Data
public class ViaHeader {

    private static final Pattern VIA_PATTERN = Pattern.compile("SIP/2\\.0/([A-Z]+) ([^;]+)(;.*)?");

    private String transport;
    private String host;
    private int port;
    private String branch;
    private String rport;
    private String alias;
    private String received;
    private String ttl;
    private String maddr;

    public ViaHeader(String rawVia) {
        parse(rawVia);
    }

    private void parse(String rawVia) {
        Matcher matcher = VIA_PATTERN.matcher(rawVia.trim());
        if (matcher.matches()) {
            this.transport = matcher.group(1);
            this.host = matcher.group(2).split(":")[0];
            this.port = matcher.group(2).split(":").length > 1 ? Integer.parseInt(matcher.group(2).split(":")[1]) : 5060;
            String params = matcher.group(3);
            Map<String, Object> paramsMap = Arrays.stream(params.split(";")).filter(StringUtils::hasLength).collect(Collectors.toMap(item -> item.contains("=") ? item.split("=")[0] : item, item -> item.contains("=") ? item.split("=")[1] : ""));
            this.branch = paramsMap.get("branch") != null ? paramsMap.get("branch").toString() : null;
            this.alias = paramsMap.get("alias") != null ? paramsMap.get("alias").toString() : null;
            this.rport = paramsMap.get("rport") != null ? paramsMap.get("rport").toString() : null;
            this.maddr = paramsMap.get("maddr") != null ? paramsMap.get("maddr").toString() : null;
            this.received = paramsMap.get("received") != null ? paramsMap.get("received").toString() : null;
            this.ttl = paramsMap.get("ttl") != null ? paramsMap.get("ttl").toString() : null;
        }
    }

    @Override
    public String toString() {
        String format = String.format("SIP/2.0/%s %s:%d", this.transport, this.host, this.port);
        if (StringUtils.hasLength(this.rport)) {
            format += String.format(";rport=%s", this.rport);
        } else if (this.rport != null) {
            format += ";rport";
        }
        if (StringUtils.hasLength(this.branch)) {
            format += String.format(";branch=%s", this.branch);
        } else if (this.branch != null) {
            format += ";branch";
        }
        if (StringUtils.hasLength(this.alias)) {
            format += String.format(";alias=%s", this.alias);
        } else if (this.alias != null) {
            format += ";alias";
        }
        if (StringUtils.hasLength(this.received)) {
            format += String.format(";received=%s", this.received);
        } else if (this.received != null) {
            format += ";received";
        }
        if (StringUtils.hasLength(this.maddr)) {
            format += String.format(";maddr=%s", this.maddr);
        } else if (this.maddr != null) {
            format += ";maddr";
        }
        if (StringUtils.hasLength(this.ttl)) {
            format += String.format(";ttl=%s", this.ttl);
        } else if (this.ttl != null) {
            format += ";ttl";
        }
        return format;
    }

    public static void main(String[] args) {
        String rawVia = "SIP/2.0/TCP 127.0.0.1:64797;rport;branch=z9hG4bKPj61ada5f0c37e45c7844124bd62629700;alias";
        ViaHeader viaHeader = new ViaHeader(rawVia);
        System.out.println(viaHeader);
    }
}
