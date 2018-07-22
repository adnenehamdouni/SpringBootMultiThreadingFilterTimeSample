package com.spring.example.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by adnenehamdouni on 19/07/2018.
 */

@Configuration
@ComponentScan(basePackages = {"com.spring.example.business"})
@PropertySource({"classpath:/environment-configuration.properties"})
@Slf4j
public class BusinessConfig {

    //@Value("${app.baseurl}")
    //private String cislBaseUrl;

    @Bean
    @Qualifier("tracking")
    public ThreadLocal<String> trackingId() {
        return new ThreadLocal<>();
    }

}
