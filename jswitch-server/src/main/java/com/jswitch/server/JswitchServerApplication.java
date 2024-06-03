package com.jswitch.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.jswitch.**")
@MapperScan("com.jswitch.**.mapper")
@SpringBootApplication
public class JswitchServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(JswitchServerApplication.class, args);
    }

}
