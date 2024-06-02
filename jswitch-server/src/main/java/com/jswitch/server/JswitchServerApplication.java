package com.jswitch.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
public class JswitchServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(JswitchServerApplication.class, args);
    }

}
