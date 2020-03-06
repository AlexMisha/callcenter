package com.shepard.callcenter.config;

import javax.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

/**
 * @author Alex Misha
 */
@Configuration
@ConfigurationProperties(prefix = "callcenter")
@Validated
public class CallcenterProperties {
    @NotBlank
    private String callRecordsPath;

    public String getCallRecordsPath() {
        return callRecordsPath;
    }

    public void setCallRecordsPath(String callRecordsPath) {
        this.callRecordsPath = callRecordsPath;
    }
}
