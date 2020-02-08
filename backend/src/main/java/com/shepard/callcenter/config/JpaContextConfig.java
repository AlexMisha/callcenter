package com.shepard.callcenter.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author Alex Misha
 */
@Configuration
@EnableJpaRepositories(basePackages = "com.shepard.callcenter.dao")
@EnableTransactionManagement
@EntityScan("com.shepard.callcenter.entity")
public class JpaContextConfig {
}
