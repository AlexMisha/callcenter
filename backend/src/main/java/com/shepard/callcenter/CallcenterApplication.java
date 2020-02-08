package com.shepard.callcenter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.shepard.callcenter.config")
public class CallcenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(CallcenterApplication.class, args);
    }

}
